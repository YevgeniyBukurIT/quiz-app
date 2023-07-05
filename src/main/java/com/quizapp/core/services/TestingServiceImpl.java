package com.quizapp.core.services;

import com.quizapp.core.interfaces.mappers.TestMapper;
import com.quizapp.core.interfaces.repository.*;
import com.quizapp.core.interfaces.services.domain.TestingService;
import com.quizapp.core.models.*;
import com.quizapp.web.dto.answer.options.AnswerOptionResultDto;
import com.quizapp.web.dto.test.TestDto;
import com.quizapp.web.dto.test.TestOverviewDto;
import com.quizapp.web.dto.test.TestResultViewDto;
import com.quizapp.web.dto.test.TestViewDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;


@AllArgsConstructor
@Service
public class TestingServiceImpl implements TestingService {

    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final AnswerRepository answerRepository;
    private final TestMapper testMapper;



    private final TestingSessionRepository testingSessionRepository;

    public List<AnswerOptionResultDto> getAnswerComparison(int userId, int testId){
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found"));

        var session = testingSessionRepository
                .getByAppUser_IdAndTest_Id(userId,testId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));

        if (!session.isTerminated())
            throw new IllegalStateException("Test session is ongoing.");

        var userTestAnswers = answerRepository
                .getAnswersByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found."))
                .stream().filter(a -> a.getAnswerOption().getQuestion().getTest().getId() == testId)
                .toList();

        var ansDto = new LinkedList<AnswerOptionResultDto>();


        test.getQuestions().forEach(q -> {
            q.getAnswerOptions().forEach(ao -> {
                var dto = new AnswerOptionResultDto();
                dto.setId(ao.getId());
                dto.setText(ao.getText());
                dto.setRight(ao.getPointsIfCorrect() > 0.0);
                dto.setUserSelected(userTestAnswers.stream().anyMatch(ans -> ans.getAnswerOption().getId()==ao.getId()));
                ansDto.add(dto);
            });
        });
        return ansDto;
    }

    @Override
    public TestDto enterTest(int userId, int testId) {
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found"));
        var user =  userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found."));
        var existingSession = testingSessionRepository.getByAppUser_IdAndTest_Id(userId,testId);

        if (existingSession.isPresent())
            testMapper.ToSessionDto(test);

        if (test.getState() == TestState.FINISHED)
            throw new IllegalArgumentException("Cannot add user to ongoing test.");

        var session = TestingSession.builder()
                .initDate(Date.from(Instant.now()))
                .timeAvailable(200000)
                .appUser(user)
                .test(test)
                .build();

        testingSessionRepository.saveAndFlush(session);
        return testMapper.ToSessionDto(test);
    }

    @Override
    public TestOverviewDto getOverview(int testId) {
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found"));

        var sessions = testingSessionRepository.getByTest_Id(testId).get();

        TestOverviewDto dto = new TestOverviewDto();
        dto.setTheme(test.getTheme());
        dto.setId(testId);
        dto.setAuthor(test.getCreator().getEmail());
        dto.setCompleted((int) sessions.stream().filter(TestingSession::isTerminated).count());
        dto.setRegistered(sessions.size());
        dto.setAuthCode(test.getAuthCode());
        return dto;
    }

    public void answerQuestion(int userId,int questionId,int answerOptionId){
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var answerOption = answerOptionRepository.findById(answerOptionId).orElseThrow(() -> new IllegalArgumentException("Option not found."));

        var answer = Answer.builder()
                .user(user)
                .answerOption(answerOption)
                .build();
        answerOption.getAnswers().add(answer);
        answerOption.getQuestion().getAnswers().add(answer);
        answerOption.getQuestion().getTest().getAnswers().add(answer);

        answerRepository.saveAndFlush(answer);
    }

    public TestDto getUserResults(int userId, int testId){
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found"));

        var session = testingSessionRepository
                .getByAppUser_IdAndTest_Id(userId,testId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));

        if (!session.isTerminated())
            throw new IllegalArgumentException("Session ongoing.");

        var testDto = testMapper.ToSessionDto(test);

        var answers = getAnswerComparison(userId,testId);

        testDto.setAnswers(answers);
        testDto.setGrade(session.getGrade());
        testDto.setUserId(userId);
        return testDto;
    }

    public TestViewDto getUsersResults(int testId){
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found"));

        var sessions = testingSessionRepository
                .getByTest_Id(testId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));

        var dto = TestViewDto.builder()
                .id(testId)
                .maxGrade(test.getMaxPoints())
                .theme(test.getTheme())
                .name(test.getName())
                .form(test.isForm())
                .results(sessions.stream().map(sess -> TestResultViewDto.builder()
                                .username(sess.getAppUser().getUsername())
                                .userId(sess.getAppUser().getId())
                                .grade(sess.getGrade())
                                .username(sess.getAppUser().getEmail()).build())
                        .toList()
                ).build();
        ;

        return dto;
    }

    public void endTest(int userId,int testId){
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found"));

        var session = testingSessionRepository
                .getByAppUser_IdAndTest_Id(userId,testId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));

        var userTestAnswers = answerRepository
                .getAnswersByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found."))
                .stream().filter(a -> a.getAnswerOption().getQuestion().getTest().getId() == testId)
                .toList();

        var wrapper = new Object() {
            int grade = 0;
        };

        userTestAnswers.forEach(ans -> wrapper.grade += ans.getAnswerOption().getPointsIfCorrect());

        session.setTerminated(true);
        session.setGrade(wrapper.grade);
        session.setEndDate(Date.from(Instant.now()));

        testingSessionRepository.saveAndFlush(session);
    }

    @Override
    public void ensureUserParticipation(int userId, int testId) {

    }
}

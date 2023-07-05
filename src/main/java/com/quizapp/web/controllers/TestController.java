package com.quizapp.web.controllers;

import com.quizapp.core.interfaces.mappers.TestMapper;
import com.quizapp.core.interfaces.repository.TestRepository;
import com.quizapp.core.interfaces.repository.TestingSessionRepository;
import com.quizapp.core.interfaces.repository.UserRepository;
import com.quizapp.core.interfaces.services.domain.StatisticService;
import com.quizapp.core.interfaces.services.domain.TestBuilderService;
import com.quizapp.core.interfaces.services.domain.TestingService;
import com.quizapp.core.models.TestingSession;
import com.quizapp.core.models.user.AppUser;
import com.quizapp.web.dto.test.TestDto;
import com.quizapp.web.dto.test.TestCreationDto;
import com.quizapp.web.dto.test.TestViewDto;
import com.quizapp.web.dto.auth.TestAuthCodeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/test")
@AllArgsConstructor
public class TestController {
    private final TestRepository testRepository;
    private final TestMapper testMapper;


    private final UserRepository userRepository;
    private final TestingSessionRepository sessionRepository;
    private final TestBuilderService testBuilderService;
    private final TestingService testingService;
    private final StatisticService statisticService;

    @GetMapping("/")
    public ResponseEntity<List<TestCreationDto>> getTests() {
        return new ResponseEntity<>(
                testRepository.findAll()
                .stream()
                .map(testMapper::ToDefaultDto)
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/user/created/")
    public ResponseEntity<List<TestViewDto>> getUserCreatedTests() {
        var user = retrieveUser();
        var ue = userRepository.findById(user.getId()).get();
        return ResponseEntity.ok(ue.getCreatedTests().stream()
                .map(t ->
                    TestViewDto.builder()
                            .id(t.getId())
                            .theme(t.getTheme())
                            .name(t.getName()).build()
                ).toList());
    }

    @GetMapping("/user/taken/")
    public ResponseEntity<List<TestViewDto>> getUserTests() {
        var user = retrieveUser();

        var sessions = sessionRepository.getByAppUser_Id(user.getId()).get();

        return ResponseEntity.ok(sessions.stream()
                .map(t ->
                        TestViewDto.builder()
                                .id(t.getTest().getId())
                                .theme(t.getTest().getTheme())
                                .name(t.getTest().getName())
                                .end(t.getEndDate().getTime()).build()
                ).toList());
    }

    private AppUser retrieveUser(){
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/start/")
    public ResponseEntity<TestDto> startTest(@RequestBody TestAuthCodeDto authenticator) {
        try {
            var user = retrieveUser();
            var test = testRepository.getTestByAuthCode(authenticator.getAuthenticator())
                    .orElseThrow(() ->new IllegalArgumentException("Test nof found."));
            testingService.enterTest(user.getId(),test.getId());
            return ResponseEntity.ok(testMapper.ToSessionDto(test));
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/answer/{questionId}/with/{optionId}")
    public void answer(@PathVariable int questionId,
                       @PathVariable int optionId
                       ){
        try {
            var user = retrieveUser();
            testingService.answerQuestion(user.getId(), questionId, optionId);
        }
        catch (Exception e){

        }
    }

    @PostMapping("/create/")
    public ResponseEntity create(@RequestBody TestCreationDto testDto){
            var user = retrieveUser();
            var userEntity = userRepository.findByEmail(user.getEmail()).get();

            var test = testMapper.FromCreationDto(testDto);
            var connectionString = testBuilderService.commitTest(userEntity,test);
            return ResponseEntity.ok(test.getId());
    }

    @PostMapping("/end/{testId}")
    public ResponseEntity end(@PathVariable int testId){
        try {
            var user = retrieveUser();
            testingService.endTest(user.getId(), testId);

            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/overview/{testId}")
    public ResponseEntity overview(@PathVariable int testId){
        try {
            var user = retrieveUser();

            return ResponseEntity.ok(testingService.getOverview(testId));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/result/{testId}/users")
    public ResponseEntity userResults(@PathVariable int testId){
        try {
            var user = retrieveUser();
            var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("user not found"));

            if (user.getId() != test.getCreator().getId())
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok(testingService.getUsersResults(testId));

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/result/{testId}")
    public ResponseEntity result(@PathVariable int testId){
        try {
            var user = retrieveUser();
            var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("user not found"));

            if (user.getId() == test.getCreator().getId())
                return ResponseEntity.ok(statisticService.getTestWithStatistic(testId));
            else
                return ResponseEntity.ok(testingService.getUserResults(user.getId(),testId));

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/result/{testId}/{userId}")
    public ResponseEntity resultForUser(@PathVariable int testId,@PathVariable int userId){
        try {
            var user = retrieveUser();
            var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("user not found"));

            return ResponseEntity.ok(testingService.getUserResults(userId,testId));

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

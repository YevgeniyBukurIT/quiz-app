package com.quizapp.web.controllers;

import com.quizapp.core.interfaces.mappers.TestMapper;
import com.quizapp.core.interfaces.repository.TestRepository;
import com.quizapp.core.interfaces.services.domain.TestBuilderService;
import com.quizapp.core.interfaces.services.domain.TestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/statistic/")
public class StatisticController {
    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final TestBuilderService testBuilderService;
    private final TestingService testingService;

    @Autowired
    public StatisticController(TestRepository testRepository, TestMapper testMapper, TestBuilderService testBuilderService, TestingService testingService) {
        this.testRepository = testRepository;
        this.testMapper = testMapper;
        this.testBuilderService = testBuilderService;
        this.testingService = testingService;
    }
}

//    @GetMapping("/test/{testId}")
//    public ResponseEntity<List<TestCreationDto>> getStatistic(@PathVariable int testId) {
////        try {
////            var user = retrieveUser();
////            testingService.answerQuestion(user.getId(), questionId, optionId);
////        }catch (Exception e){
////
////        }
//    }
//}

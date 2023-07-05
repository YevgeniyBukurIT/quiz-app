package com.quizapp.core.interfaces.mappers;

import com.quizapp.core.models.Test;
import com.quizapp.web.dto.test.TestDto;
import com.quizapp.web.dto.test.TestCreationDto;


public interface TestMapper extends Mapper<Test>{
    Test FromCreationDto(TestCreationDto creationDto);


    TestCreationDto ToDefaultDto(Test test);

    TestDto ToSessionDto(Test test);
}

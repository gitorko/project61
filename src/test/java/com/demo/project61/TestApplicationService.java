/**
 * Copyright (c) Company, Inc. All Rights Reserved.
 */

package com.demo.project61;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.project61.pojo.PersonAge;
import com.demo.project61.service.AppServiceImpl;

/**
 * TestApplicationService
 */
@SpringBootTest
public class TestApplicationService {

    @Autowired
    AppServiceImpl svc;

    @Test
    public void test01_CalculateAge_Pass() {

        String str = "01-01-1980";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(str, formatter);
        PersonAge pa = svc.calculateAge(dob);
        Assertions.assertThat(pa.getYears()).isGreaterThan(40);
    }

}

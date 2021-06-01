/**
 * Copyright (c) 2021 Company. All Rights Reserved.
 */

package com.demo.project61;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.project61.pojo.PersonAge;
import com.demo.project61.service.AppServiceImpl;

/**
 * TestApplicationService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestApplicationService {

    @Autowired
    AppServiceImpl svc;

    @Test
    public void test01_CalculateAge_Pass() {

        String str = "01-01-1980";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(str, formatter);
        PersonAge pa = svc.calculateAge(dob);
        Assertions.assertThat(pa.getYears()).isEqualTo(41);
    }

}

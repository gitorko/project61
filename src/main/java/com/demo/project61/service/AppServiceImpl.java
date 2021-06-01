/**
 * Copyright (c) 2021 Company. All Rights Reserved.
 */

package com.demo.project61.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import com.demo.project61.pojo.PersonAge;

/**
 * ApplicationService
 */
@Component
public class AppServiceImpl implements IAppServer {

    public PersonAge calculateAge(LocalDate dob) {
        final LocalDate today = LocalDate.now();
        final Period period = Period.between(dob, today);
        final PersonAge pa =
                PersonAge.builder().days(period.getDays()).months(period.getMonths()).years(period.getYears()).build();
        return pa;
    }
}

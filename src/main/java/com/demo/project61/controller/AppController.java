/**
 * Copyright (c) Company, Inc. All Rights Reserved.
 */

package com.demo.project61.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.project61.pojo.PersonAge;
import com.demo.project61.service.AppServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * ApplicationController
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class AppController {

    @Autowired
    AppServiceImpl svc;

    @GetMapping("/age/{dob}")
    public PersonAge calculateAge(@PathVariable("dob") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dob) {
        log.info("Date of Birth: {}", dob);
        if (dob.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DOB cant be in future");
        }
        final PersonAge pa = svc.calculateAge(dob);
        log.info("Person Age: {}", pa);
        return pa;
    }

    @GetMapping("/time")
    public String getServerTime() {
        log.info("Getting server time!");
        String podName = System.getenv("HOSTNAME");
        return "Pod: " + podName + " : " + LocalDateTime.now();
    }
}

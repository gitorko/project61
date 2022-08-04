/**
 * Copyright (c) Company, Inc. All Rights Reserved.
 */

package com.demo.project61.service;

import java.time.LocalDate;

import com.demo.project61.pojo.PersonAge;

/**
 * AppService
 */
interface AppService {
    PersonAge calculateAge(LocalDate dob);
}

/**
 * Copyright (c) 2021 Company. All Rights Reserved.
 */

package com.demo.project61.service;

import java.time.LocalDate;

import com.demo.project61.pojo.PersonAge;

/**
 * IAppServer
 */
interface IAppServer {
    PersonAge calculateAge(LocalDate dob);
}

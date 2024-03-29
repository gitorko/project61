/**
 * Copyright (c) Company, Inc. All Rights Reserved.
 */

package com.demo.project61.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * PersonAge
 */
@Data
@Builder
public class PersonAge {

    Integer days;
    Integer months;
    Integer years;
}

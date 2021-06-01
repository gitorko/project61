/**
 * Copyright (c) 2021 Company. All Rights Reserved.
 */

package com.demo.project61;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriTemplate;

import com.demo.project61.controller.AppController;
import com.demo.project61.pojo.PersonAge;
import com.demo.project61.service.AppServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestApplicationController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppServiceImpl svc;

    @Test
    public void test01_CalculateAge_Must_Pass() throws Exception {
        String dobDt = "01-01-1980";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(dobDt, formatter);
        PersonAge pa = PersonAge.builder().days(0).months(0).years(0).build();
        Mockito.when(svc.calculateAge(dob)).thenReturn(pa);
        UriTemplate template = new UriTemplate("/api/age/{dob}");
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("dob", dobDt);
        URI uri = template.expand(variables);
        System.out.println(uri.toString());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        Assertions.assertThat(response).isNotEmpty();
    }

    @Test
    public void test02_CalculateAge_FutureDate_Pass() throws Exception {
        String futureDt = "01-01-2028";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(futureDt, formatter);
        PersonAge pa = PersonAge.builder().days(0).months(0).years(0).build();
        Mockito.when(svc.calculateAge(dob)).thenReturn(pa);
        UriTemplate template = new UriTemplate("/api/age/{dob}");
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("dob", futureDt);
        URI uri = template.expand(variables);
        System.out.println(uri.toString());
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
    }

}

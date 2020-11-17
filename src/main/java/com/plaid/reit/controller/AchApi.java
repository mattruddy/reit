package com.plaid.reit.controller;

import com.plaid.reit.service.AchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/payment")
public class AchApi {

    @Autowired private AchService achService;

    @PostMapping(value = "/connect")
    public void connect(HttpServletResponse response) {
        achService.connect(response);
    }

}

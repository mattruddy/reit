package com.plaid.reit.controller;

import com.plaid.reit.model.dto.AuthTokenResponse;
import com.plaid.reit.model.dto.LoginRequest;
import com.plaid.reit.model.dto.SignUpRequest;
import com.plaid.reit.model.paymentDto.ExternalAccountReq;
import com.plaid.reit.service.AchService;
import com.plaid.reit.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PublicApi {

    @Autowired private AuthService authService;

    // POST
    @PostMapping(value = "/signup")
    public AuthTokenResponse signup(@RequestBody SignUpRequest signUpRequest) {
        return authService.signup(signUpRequest);
    }

    @PostMapping(value = "/login")
    public AuthTokenResponse login(@RequestBody LoginRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @Autowired private AchService achService;

    @PostMapping(value = "/connect")
    public void connect(HttpServletResponse httpServletResponse) {
        achService.connect(httpServletResponse);
    }

    @PostMapping(value = "/profile")
    public void createProfile(HttpServletRequest request) {
        achService.createPaymentProfile(request);
    }

    @PostMapping(value = "/account")
    public void createAccount(@RequestBody ExternalAccountReq req, HttpServletRequest request) {
        achService.createExternalAccount(req, request);
    }

    @PostMapping(value = "/payment")
    public void createPayment(HttpServletRequest request) {
        achService.createPayment(request);
    }

}

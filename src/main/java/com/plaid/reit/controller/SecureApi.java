package com.plaid.reit.controller;

import com.plaid.reit.model.paymentDto.ExternalAccountReq;
import com.plaid.reit.service.AchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/secure", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecureApi {

    @Autowired private AchService achService;

    @PostMapping(value = "/account")
    public void createAccount(@RequestBody ExternalAccountReq req, HttpServletRequest request) {
        achService.createExternalAccount(req, request);
    }

    @PostMapping(value = "/payment")
    public void createPayment() {
        achService.createPayment();
    }

//    @GetMapping("/profile")
//    public ProfileResp getProfile() throws Exception {
//        return plaidService.getAccount();
//    }

//    @GetMapping("/linked-token")
//    public LinkTokenResp getAccount() {
//        return new LinkTokenResp(plaidService.linkToken());
//    }

//    @PostMapping("/token-access/{token}")
//    public ItemPublicTokenExchangeResponse createAccess(@PathVariable("token") String token) {
//        return plaidService.createAccess(token);
//    }
//
//    @PostMapping("/transfer")
//    public void transferFunds(@RequestBody TransferRequest request) throws Exception {
//        plaidService.transferFunds(request);
//    }
}

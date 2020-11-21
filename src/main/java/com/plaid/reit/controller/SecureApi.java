package com.plaid.reit.controller;

import com.plaid.reit.model.dto.InvestorResp;
import com.plaid.reit.model.dto.ProfileResp;
import com.plaid.reit.model.dto.TransactionResp;
import com.plaid.reit.model.dto.TransferRequest;
import com.plaid.reit.model.paymentDto.ExternalAccountReq;
import com.plaid.reit.service.AchService;
import com.plaid.reit.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/secure", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecureApi {

    @Autowired private AchService achService;
    @Autowired private InvestorService investorService;

    @GetMapping("/profile")
    public ProfileResp getProfile() {
        return investorService.getProfile();
    }

    @PutMapping(value = "/account")
    public InvestorResp createAccount(@RequestBody ExternalAccountReq req) {
        return achService.createExternalAccount(req);
    }

    @DeleteMapping(value = "account")
    public InvestorResp deleteExternalAccount() {
        return achService.deleteExternalAccount();
    }

    @PostMapping(value = "/transfer")
    public TransactionResp createPayment(@RequestBody TransferRequest request) {
        return achService.createPayment(request);
    }
}

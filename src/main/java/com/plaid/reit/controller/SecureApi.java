package com.plaid.reit.controller;

import com.plaid.client.response.AccountsGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.reit.model.dto.InvestorRequest;
import com.plaid.reit.model.dto.LinkTokenResp;
import com.plaid.reit.model.dto.ProfileResp;
import com.plaid.reit.model.dto.TransferRequest;
import com.plaid.reit.service.PlaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/secure", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecureApi {

    @Autowired private PlaidService plaidService;

    @GetMapping("/profile")
    public ProfileResp getProfile() throws Exception {
        return plaidService.getAccount();
    }

    @GetMapping("/linked-token")
    public LinkTokenResp getAccount() {
        return new LinkTokenResp(plaidService.linkToken());
    }

    @PostMapping("/token-access/{token}")
    public ItemPublicTokenExchangeResponse createAccess(@PathVariable("token") String token) {
        return plaidService.createAccess(token);
    }

    @PostMapping("/transfer")
    public void transferFunds(@RequestBody TransferRequest request) throws Exception {
        plaidService.transferFunds(request);
    }
}

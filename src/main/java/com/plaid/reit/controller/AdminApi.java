package com.plaid.reit.controller;

import com.plaid.reit.model.dto.TransactionResp;
import com.plaid.reit.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminApi {

    private String apiToken = "b5b81edf-a361-47ed-9e24-7fa13e524a2a";

    @Autowired private TransactionService transactionService;

    @GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResp> getPendingTransactions() {
        return transactionService.getPendingTransactions();
    }

    @PutMapping("/transaction/{id}")
    public void updateTransaction(@PathVariable("id") long id) {
        transactionService.updateTransaction(id);
    }

}

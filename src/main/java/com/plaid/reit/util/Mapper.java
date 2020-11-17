package com.plaid.reit.util;

import com.plaid.reit.model.Dividend;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.Transaction;
import com.plaid.reit.model.dto.DividendResp;
import com.plaid.reit.model.dto.InvestorRequest;
import com.plaid.reit.model.dto.InvestorResp;
import com.plaid.reit.model.dto.TransactionResp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static Investor dtoToEntity(InvestorRequest request) {
        Investor investor = new Investor();
        investor.setAmount(BigDecimal.valueOf(request.getAmount()));
        investor.setMemberDate(Timestamp.from(Instant.now()));
        return investor;
    }

    public static InvestorResp entityToDto(Investor investor, List<Dividend> dividends,
                                           List<Transaction> transactions) {
        InvestorResp resp = new InvestorResp();
        resp.setAmount(investor.getAmount());
        resp.setLastFourAccountNumber(investor.getLastFourAccountNumber());
        resp.setBankName(investor.getBankName());
        resp.setBankType(investor.getBankType());
        resp.setTrossAccount(investor.getTrossAccountNumber());
        resp.setDividends(dividends.stream()
                .map(Mapper::entityToDto).collect(Collectors.toList()));
        resp.setTransactions(transactions.stream()
                .map(Mapper::entityToDto).collect(Collectors.toList()));
        resp.setMemberDate(investor.getMemberDate());
        return resp;
    }

    public static DividendResp entityToDto(Dividend dividend) {
        DividendResp resp = new DividendResp();
        resp.setAmount(dividend.getAmount());
        resp.setCreatedAt(dividend.getCreatedAt());
        resp.setId(dividend.getId());
        return resp;
    }

    public static TransactionResp entityToDto(Transaction transaction) {
        TransactionResp resp = new TransactionResp();
        resp.setAmount(transaction.getAmount());
        resp.setCreatedAt(transaction.getCreatedAt());
        resp.setId(transaction.getId());
        resp.setTransferStatus(transaction.getTransferStatus());
        resp.setTransactionType(transaction.getTransactionType());
        return resp;
    }

}

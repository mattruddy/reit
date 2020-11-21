package com.plaid.reit.util;

import com.plaid.reit.model.Dividend;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.Transaction;
import com.plaid.reit.model.dto.DividendResp;
import com.plaid.reit.model.dto.InvestorResp;
import com.plaid.reit.model.dto.TransactionResp;
import com.plaid.reit.model.paymentDto.ExternalAccountReq;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private Mapper() {}

    public static Investor dtoToEntity(Investor investor, ExternalAccountReq req, String externalAccountId, String profileId) {
        investor.setProfileId(profileId);
        investor.setLastFourAccountNumber(req.getAccountNumber().substring(req.getAccountNumber().length() - 4));
        investor.setLinked(Boolean.TRUE);
        investor.setBankName(req.getBankName());
        investor.setExternalAccountId(externalAccountId);
        investor.setBankType(req.getBankType());
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
        resp.setLinked(investor.getLinked());
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

package com.plaid.reit.util;

import com.plaid.reit.model.Dividend;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.dto.DividendResp;
import com.plaid.reit.model.dto.InvestorRequest;
import com.plaid.reit.model.dto.InvestorResp;

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
        investor.setAccountNumber(AccountNumberUtil.generateRandom());
        return investor;
    }

    public static InvestorResp entityToDto(Investor investor, List<Dividend> dividends) {
        InvestorResp resp = new InvestorResp();

        resp.setAccountNumber(investor.getAccountNumber());
        resp.setAmount(investor.getAmount());
        resp.setDividends(dividends.stream()
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

}

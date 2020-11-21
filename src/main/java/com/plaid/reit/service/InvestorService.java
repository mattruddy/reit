package com.plaid.reit.service;

import com.plaid.reit.model.Dividend;
import com.plaid.reit.model.EndUser;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.Transaction;
import com.plaid.reit.model.dto.InvestorRequest;
import com.plaid.reit.model.dto.ProfileResp;
import com.plaid.reit.repository.DividendRepo;
import com.plaid.reit.repository.EndUserRepo;
import com.plaid.reit.repository.TransactionRepo;
import com.plaid.reit.security.UserIdentity;
import com.plaid.reit.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InvestorService {

    @Autowired private EndUserRepo endUserRepo;
    @Autowired private UserIdentity userIdentity;
    @Autowired private DividendRepo dividendRepo;
    @Autowired private TransactionRepo transactionRepo;

    @Transactional(readOnly = true)
    public ProfileResp getProfile() {
        EndUser endUser = userIdentity.getEndUser();

        ProfileResp profileResp = new ProfileResp();
        profileResp.setEmail(endUser.getEmail());

        Investor investor = endUser.getInvestor();
        if (investor != null) {
            List<Dividend> dividends = dividendRepo.findAllByInvestorId(investor.getId());
            List<Transaction> transactions = transactionRepo.findAllByInvestorId(investor.getId());
            profileResp.setInvestor(Mapper.entityToDto(investor, dividends, transactions));
        }
        return profileResp;
    }

}

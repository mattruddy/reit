package com.plaid.reit.service;

import com.plaid.reit.model.EndUser;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.dto.InvestorRequest;
import com.plaid.reit.repository.EndUserRepo;
import com.plaid.reit.security.UserIdentity;
import com.plaid.reit.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InvestorService {

    @Autowired private EndUserRepo endUserRepo;
    @Autowired private UserIdentity userIdentity;

    @Transactional
    public void createInvestor(InvestorRequest request) {
        EndUser endUser = userIdentity.getEndUser();
        Investor investor = Mapper.dtoToEntity(request);
        investor.setEndUser(endUser);
        endUser.setInvestor(investor);
        endUserRepo.save(endUser);
    }

}

package com.plaid.reit.security;

import com.plaid.reit.exception.ServiceException;
import com.plaid.reit.model.EndUser;
import com.plaid.reit.repository.EndUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserIdentity {

    private EndUser endUser;
    @Autowired
    private EndUserRepo endUserRepo;

    @Transactional(readOnly = true)
    public void create(String email) {
        EndUser user = endUserRepo.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new ServiceException("Error");
        }
        this.endUser = user;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUser endUser) {
        this.endUser = endUser;
    }
}

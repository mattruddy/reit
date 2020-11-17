package com.plaid.reit.service;

import com.plaid.reit.model.EndUser;
import com.plaid.reit.repository.EndUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@Transactional
public class MyUserDetailService implements UserDetailsService {

    @Autowired private EndUserRepo endUserRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        EndUser endUser = endUserRepo.findByEmailIgnoreCase(s);

        if (endUser == null) {
            return new User(
                    "", "",
                    true, true, true, true,
                    Collections.emptyList()
            );
        }

        return new User(
                endUser.getEmail(), endUser.getPassDigest(),
                true, true, true, true,
                Collections.emptyList()
        );
    }
}

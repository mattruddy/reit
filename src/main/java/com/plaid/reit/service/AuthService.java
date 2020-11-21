package com.plaid.reit.service;

import com.plaid.reit.model.Investor;
import com.plaid.reit.model.dto.AuthTokenResponse;
import com.plaid.reit.model.dto.SignUpRequest;
import com.plaid.reit.exception.ServiceException;
import com.plaid.reit.model.EndUser;
import com.plaid.reit.repository.EndUserRepo;
import com.plaid.reit.security.JwtTokenProvider;
import com.plaid.reit.util.AccountNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private EndUserRepo endUserRepo;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private PasswordEncoder passwordEncoder;

    public AuthTokenResponse signup(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail().toLowerCase();
        if (endUserRepo.findByEmailIgnoreCase(email) != null) {
            throw new ServiceException("Invalid email");
        }
        EndUser endUser = new EndUser();
        endUser.setEmail(email);
        endUser.setPassDigest(passwordEncoder.encode(signUpRequest.getPassword()));

        Investor investor = new Investor();
        investor.setLinked(Boolean.FALSE);
        investor.setTrossAccountNumber(AccountNumberUtil.generateRandom());
        investor.setMemberDate(Timestamp.from(Instant.now()));
        investor.setAmount(BigDecimal.ZERO);
        investor.setEndUser(endUser);

        endUser.setInvestor(investor);
        endUserRepo.save(endUser);
        return login(email, signUpRequest.getPassword());
    }

    public AuthTokenResponse login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email.toLowerCase(), password));
            String token = jwtTokenProvider.createToken(email.toLowerCase());
            AuthTokenResponse response = new AuthTokenResponse();
            response.setToken(token);
            return response;
        } catch (Exception e) {
            throw new ServiceException("Invalid Credentials");
        }
    }

}

package com.plaid.reit.service;

import com.google.gson.Gson;
import com.plaid.reit.exception.ServiceException;
import com.plaid.reit.model.EndUser;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.paymentDto.*;
import com.plaid.reit.repository.EndUserRepo;
import com.plaid.reit.repository.TransactionService;
import com.plaid.reit.security.UserIdentity;
import com.plaid.reit.util.AccountNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

@Component
public class AchService {

    @Autowired private UserIdentity userIdentity;
    @Autowired private EndUserRepo endUserRepo;
    @Autowired private TransactionService transactionService;

    private static final Gson gson = new Gson().newBuilder()
            .setLenient()
            .create();
    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String PAYMENT_URL = "http://payment:80/api";
    private static final String API_TOKEN = "2dmBxtqEKcf2PTVgHQmRP8hDLeu41sZY2pLlDH3frBb";
    private static final String API_KEY = "mtiZ5yW9OjrIjaoY7ZldI7jZGM9NM5Aj21upVwsb9ME";
    private static final String PAYMENT_TYPE_DEBIT = "92486311-be4d-4c86-8798-4a47ba80533b";

    @Transactional
    public void createExternalAccount(ExternalAccountReq req, HttpServletRequest servletRequest) {
        String sessionId = connect();

        EndUser endUser = userIdentity.getEndUser();
        if (endUser.getInvestor() != null) {
            throw new ServiceException("Account Created Already");
        }
//        HttpHeaders headers = getHeaders(sessionId);
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("external_account_payment_profile_id", createPaymentProfile(sessionId));
//        map.add("external_account_type", req.getBankType().getValue());
//        map.add("external_account_holder", req.getAccountHolder());
//        map.add("external_account_dfi_id", req.getRoutingNumber());
//        map.add("external_account_number", req.getAccountNumber());
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/saveExternalAccount",
//                request , String.class);
//
//        ExternalAccountResp resp = gson.fromJson(responseEntity.getBody(), ExternalAccountResp.class);

        Investor investor = new Investor();
        investor.setAmount(BigDecimal.ZERO);
        investor.setEndUser(endUser);
        investor.setMemberDate(Timestamp.from(Instant.now()));
        investor.setLastFourAccountNumber(req.getAccountNumber().substring(req.getAccountNumber().length() - 5));
        investor.setBankName(req.getBankName());
//        investor.setAccountId(resp.getData().getExternal_account_id());
        investor.setAccountId(UUID.randomUUID().toString());
        investor.setBankType(req.getBankType());

        endUser.setInvestor(investor);
        endUserRepo.save(endUser);
        disconnect(sessionId);
    }

    public void createPayment() {
        EndUser endUser = userIdentity.getEndUser();

        if (endUser.getInvestor() == null
                || endUser.getInvestor().getAccountId() == null) {
            throw new ServiceException("Account not created");
        }

        String sessionId = connect();

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        HttpHeaders headers = getHeaders(sessionId);
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("payment_schedule_external_account_id", endUser.getInvestor().getAccountId());
//        map.add("payment_schedule_payment_type_id",PAYMENT_TYPE_DEBIT);
//        map.add("payment_schedule_next_date", LocalDate.now().format(formatter));
//        map.add("payment_schedule_frequency", "once");
//        map.add("payment_schedule_end_date", LocalDate.now().format(formatter));
//        map.add("payment_schedule_amount", Double.toString(.01));
//        map.add("payment_schedule_currency_code", "USD");
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/savePaymentSchedule", request , String.class);
//
//        PaymentResp resp = gson.fromJson(responseEntity.getBody(), PaymentResp.class);

        transactionService.createTransaction(BigDecimal.valueOf(.01), UUID.randomUUID().toString());

        disconnect(sessionId);
    }

    private String createPaymentProfile(String sessionId) {
        return UUID.randomUUID().toString();
//        HttpHeaders headers = getHeaders(sessionId);
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("payment_profile_external_id", Long.toString(userIdentity.getEndUser().getId()));
//        map.add("payment_profile_email_address", userIdentity.getEndUser().getEmail());
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        String json = restTemplate.postForEntity(PAYMENT_URL + "/savePaymentProfile", request , String.class)
//                .getBody();
//        ProfileResp resp = gson.fromJson(json, ProfileResp.class);
//        return resp.getData().getPayment_profile_id();
    }

    private String connect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user_api_token", API_TOKEN);
        map.add("user_api_key", API_KEY);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/connect", request , String.class);
        ConnectResp connectResp = gson.fromJson(responseEntity.getBody(), ConnectResp.class);
        return connectResp.getSession_id();
    }

    private void disconnect(String sessionId) {
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user_api_token", API_TOKEN);
        map.add("user_api_key", API_KEY);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/disconnect", request , String.class);
    }

    private HttpHeaders getHeaders(String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        if (sessionId != null) {
            headers.add("Cookie", "PHPSESSID="+sessionId);
        }
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}

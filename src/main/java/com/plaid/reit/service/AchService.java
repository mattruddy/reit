package com.plaid.reit.service;

import com.google.gson.Gson;
import com.plaid.reit.exception.ServiceException;
import com.plaid.reit.model.EndUser;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.Transaction;
import com.plaid.reit.model.dto.InvestorResp;
import com.plaid.reit.model.dto.TransactionResp;
import com.plaid.reit.model.dto.TransferRequest;
import com.plaid.reit.model.paymentDto.*;
import com.plaid.reit.repository.InvestorRepo;
import com.plaid.reit.security.UserIdentity;
import com.plaid.reit.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

@Component
public class AchService {

    @Autowired private UserIdentity userIdentity;
    @Autowired private TransactionService transactionService;
    @Autowired private InvestorRepo investorRepo;

    @Value("${openach.gateway.url}")
    private String paymentUrl;
    @Value("${openach.api.token}")
    private String apiToken;
    @Value("${openach.api.key}")
    private String apiKey;
    @Value("${openach.payment.debit}")
    private String paymentDebit;
    @Value("${openach.payment.credit}")
    private String paymentCredit;

    private static final Gson gson = new Gson();
    private static final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public InvestorResp createExternalAccount(ExternalAccountReq req) {
        String sessionId = connect();

        Investor investor = userIdentity.getEndUser().getInvestor();
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String profileId = createPaymentProfile(sessionId);
        map.add("external_account_payment_profile_id", profileId);
        map.add("external_account_type", req.getBankType().getValue());
        map.add("external_account_holder", "matt ruddy");
        map.add("external_account_dfi_id", req.getRoutingNumber());
        map.add("external_account_number", req.getAccountNumber());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(paymentUrl + "/saveExternalAccount",
                request , String.class);

        ExternalAccountResp resp = gson.fromJson(responseEntity.getBody(), ExternalAccountResp.class);
        Mapper.dtoToEntity(investor, req, resp.getData().getExternal_account_id(), profileId);
        investorRepo.save(investor);

        disconnect(sessionId);
        return Mapper.entityToDto(investor, Collections.emptyList(), Collections.emptyList());
    }

    @Transactional
    public InvestorResp deleteExternalAccount() {
        Investor investor = userIdentity.getEndUser().getInvestor();

        String sessionId = connect();

        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("payment_profile_id", investor.getProfileId());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(paymentUrl + "/deletePaymentProfile",
                request, String.class);

        DeleteProfileResp resp = gson.fromJson(responseEntity.getBody(), DeleteProfileResp.class);
        if (resp.isSuccess()) {
            investor.setProfileId(null);
            investor.setExternalAccountId(null);
            investor.setLastFourAccountNumber(null);
            investor.setBankName(null);
            investor.setLinked(Boolean.FALSE);
            investorRepo.save(investor);
        }
        disconnect(sessionId);
        return Mapper.entityToDto(investor, Collections.emptyList(), Collections.emptyList());
    }

    public TransactionResp createPayment(TransferRequest transferRequest) {
        EndUser endUser = userIdentity.getEndUser();

        if (endUser.getInvestor() == null) {
            throw new ServiceException("Account not created");
        }

        String sessionId = connect();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transferRequest.getTransferDate());
        calendar.add(Calendar.HOUR, 3 * 24);
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("payment_schedule_external_account_id", endUser.getInvestor().getExternalAccountId());
        map.add("payment_schedule_payment_type_id", paymentDebit);
        map.add("payment_schedule_next_date", formatter.format(calendar.getTime()));
        map.add("payment_schedule_frequency", "once");
        map.add("payment_schedule_end_date", formatter.format(calendar.getTime()));
        map.add("payment_schedule_amount", Double.toString(transferRequest.getAmount()));
        map.add("payment_schedule_currency_code", "USD");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(paymentUrl + "/savePaymentSchedule", request , String.class);
        PaymentResp resp = gson.fromJson(responseEntity.getBody(), PaymentResp.class);

        Transaction transaction = transactionService.createTransaction(transferRequest, resp.getData().getPayment_schedule_id());
        disconnect(sessionId);
        return Mapper.entityToDto(transaction);
    }

    private String createPaymentProfile(String sessionId) {
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("payment_profile_external_id", Long.toString(userIdentity.getEndUser().getId()));
        map.add("payment_profile_email_address", userIdentity.getEndUser().getEmail());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String json = restTemplate.postForEntity(paymentUrl + "/savePaymentProfile", request , String.class)
                .getBody();
        ProfileResp resp = gson.fromJson(json, ProfileResp.class);
        return resp.getData().getPayment_profile_id();
    }

    private String connect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user_api_token", apiToken);
        map.add("user_api_key", apiKey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(paymentUrl + "/connect", request , String.class);
        ConnectResp connectResp = gson.fromJson(responseEntity.getBody(), ConnectResp.class);
        return connectResp.getSession_id();
    }

    private void disconnect(String sessionId) {
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user_api_token", apiToken);
        map.add("user_api_key", apiKey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        restTemplate.postForEntity(paymentUrl + "/disconnect", request , String.class);
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

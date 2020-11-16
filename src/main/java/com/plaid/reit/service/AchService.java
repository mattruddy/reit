package com.plaid.reit.service;

import com.google.gson.Gson;
import com.plaid.reit.model.paymentDto.ConnectResp;
import com.plaid.reit.model.paymentDto.ExternalAccountReq;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Component
public class AchService {

    private static final Gson gson = new Gson();
    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String PAYMENT_URL = "http://payment:80/api";
    private static final String API_TOKEN = "2dmBxtqEKcf2PTVgHQmRP8hDLeu41sZY2pLlDH3frBb";
    private static final String API_KEY = "mtiZ5yW9OjrIjaoY7ZldI7jZGM9NM5Aj21upVwsb9ME";

    public void connect(HttpServletResponse response) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("user_api_token", API_TOKEN);
            map.add("user_api_key", API_KEY);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/connect", request , String.class);
            ConnectResp connectResp = gson.fromJson(responseEntity.getBody(), ConnectResp.class);
            Cookie cookie = new Cookie("PHPSESSID", connectResp.getSession_id());
            cookie.setPath("/");
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPaymentProfile(HttpServletRequest servletRequest) {
        String sessionId = null;
        for (Cookie cookie : servletRequest.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("PHPSESSID")) {
                sessionId = cookie.getValue();
                break;
            }
        }
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("payment_profile_external_id", "me");
        map.add("payment_profile_email_address", "yo@aol.com");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/savePaymentProfile", request , String.class);
        System.out.println(responseEntity.getBody());
    }

    public void createExternalAccount(ExternalAccountReq req, HttpServletRequest servletRequest) {
        String sessionId = null;
        for (Cookie cookie : servletRequest.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("PHPSESSID")) {
                sessionId = cookie.getValue();
                break;
            }
        }
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("external_account_payment_profile_id", "9499ac48-6d65-4a53-849d-85934cbf710e");
        map.add("external_account_type", req.getBankType().getValue());
        map.add("external_account_holder", req.getAccountHolder());
        map.add("external_account_dfi_id", req.getRoutingNumber());
        map.add("external_account_number", req.getAccountNumber());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/saveExternalAccount", request , String.class);
        System.out.println(responseEntity.getBody());

    }

    public void createPayment(HttpServletRequest servletRequest) {
        String sessionId = null;
        for (Cookie cookie : servletRequest.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("PHPSESSID")) {
                sessionId = cookie.getValue();
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        HttpHeaders headers = getHeaders(sessionId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("payment_schedule_external_account_id", "a9e06fc1-ef01-4f4b-8d30-15363f4a8138");
        map.add("payment_schedule_payment_type_id","92486311-be4d-4c86-8798-4a47ba80533b");
        map.add("payment_schedule_next_date", LocalDate.now().format(formatter));
        map.add("payment_schedule_frequency", "once");
        map.add("payment_schedule_end_date", LocalDate.now().format(formatter));
        map.add("payment_schedule_amount", Double.toString(.01));
        map.add("payment_schedule_currency_code", "USD");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(PAYMENT_URL + "/savePaymentSchedule", request , String.class);
        System.out.println(responseEntity.getBody());
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

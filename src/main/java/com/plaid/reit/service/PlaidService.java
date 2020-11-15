package com.plaid.reit.service;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.AccountsGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.LinkTokenCreateRequest;
import com.plaid.client.response.AccountsGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.reit.exception.ServiceException;
import com.plaid.reit.model.Account;
import com.plaid.reit.model.Dividend;
import com.plaid.reit.model.EndUser;
import com.plaid.reit.model.Investor;
import com.plaid.reit.model.dto.ProfileResp;
import com.plaid.reit.repository.DividendRepo;
import com.plaid.reit.repository.EndUserRepo;
import com.plaid.reit.security.UserIdentity;
import com.plaid.reit.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
public class PlaidService {

    @Autowired private PlaidClient plaidClient;
    @Autowired private UserIdentity userIdentity;
    @Autowired private EndUserRepo endUserRepo;
    @Autowired private DividendRepo dividendRepo;

    public String linkToken() {
        LinkTokenCreateRequest.User user = new LinkTokenCreateRequest.User(userIdentity.getEndUser().getEmail());
        try {
            return plaidClient.service()
                    .linkTokenCreate(
                            new LinkTokenCreateRequest(
                                    user,
                                    "Plaid",
                                    Collections.singletonList("transactions"),
                                    Collections.singletonList("US"),
                                    "en"
                            )
                    ).execute().body().getLinkToken();
        } catch (Exception e) {
            throw new ServiceException("error");
        }
    }

    @Transactional
    public ItemPublicTokenExchangeResponse createAccess(String token) {
        try {
            EndUser endUser = userIdentity.getEndUser();
            ItemPublicTokenExchangeResponse response = plaidClient.service()
                    .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(
                            token
                    )).execute().body();

            Account account = new Account();
            account.setEndUser(endUser);
            account.setAccountId(response.getAccessToken());

            endUser.setAccount(account);

            endUserRepo.save(endUser);
            return response;
        }  catch (Exception e) {
            throw new ServiceException("error");
        }
    }

    @Transactional(readOnly =  true)
    public ProfileResp getAccount() throws Exception {
        Account account = userIdentity.getEndUser().getAccount();
        if (account != null) {
            AccountsGetResponse accountsGetResponse = plaidClient
                    .service().accountsGet(
                            new AccountsGetRequest(userIdentity.getEndUser().getAccount().getAccountId())
                    ).execute().body();
            ProfileResp resp = new ProfileResp();
            resp.setEmail(userIdentity.getEndUser().getEmail());
            if (accountsGetResponse != null) {
                resp.setAccount(accountsGetResponse.getAccounts());
            }
            Investor investor = userIdentity.getEndUser().getInvestor();
            if (investor != null) {
                List<Dividend> dividends = dividendRepo.findAllByInvestorId(investor.getId());
                resp.setInvestor(Mapper.entityToDto(investor, dividends));
            }
            return resp;
        }
        throw new ServiceException("No Account");
    }

}

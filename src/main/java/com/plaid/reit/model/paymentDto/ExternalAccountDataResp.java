package com.plaid.reit.model.paymentDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalAccountDataResp {

    @JsonProperty("external_account_id")
    private String external_account_id;

    public String getExternal_account_id() {
        return external_account_id;
    }

    public void setExternal_account_id(String external_account_id) {
        this.external_account_id = external_account_id;
    }
}

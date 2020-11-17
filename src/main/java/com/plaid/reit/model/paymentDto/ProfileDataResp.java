package com.plaid.reit.model.paymentDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDataResp {

    @JsonProperty("payment_profile_id")
    private String payment_profile_id;

    public String getPayment_profile_id() {
        return payment_profile_id;
    }

    public void setPayment_profile_id(String payment_profile_id) {
        this.payment_profile_id = payment_profile_id;
    }
}

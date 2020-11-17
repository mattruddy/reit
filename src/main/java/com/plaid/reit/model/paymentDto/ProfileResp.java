package com.plaid.reit.model.paymentDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResp {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private ProfileDataResp data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ProfileDataResp getData() {
        return data;
    }

    public void setData(ProfileDataResp data) {
        this.data = data;
    }
}

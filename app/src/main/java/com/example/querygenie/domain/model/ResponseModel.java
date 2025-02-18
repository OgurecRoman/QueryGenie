package com.example.querygenie.domain.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}

package com.example.querygenie.data.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}

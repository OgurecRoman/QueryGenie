package com.example.querygenie.data.model;

import com.google.gson.annotations.SerializedName;

public class RequestModel {
    @SerializedName("prompt")
    private String prompt;

    public RequestModel(String prompt) {
        this.prompt = prompt;
    }
}

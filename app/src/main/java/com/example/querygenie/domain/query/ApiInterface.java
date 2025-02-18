package com.example.querygenie.domain.query;

import com.example.querygenie.domain.model.RequestModel;
import com.example.querygenie.domain.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/generate")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> response(@Body RequestModel request);
}

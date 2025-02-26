package com.example.querygenie.domain.queryService;

import com.example.querygenie.data.model.RequestModel;
import com.example.querygenie.data.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/generate")
    @Headers("Content-Type: application/json")
    Call<ResponseModel> response(@Body RequestModel request);
}

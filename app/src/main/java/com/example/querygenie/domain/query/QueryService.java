package com.example.querygenie.domain.query;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.querygenie.domain.model.RequestModel;
import com.example.querygenie.domain.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryService extends Service {

    private void sendResponse(String response) {
        Intent broadcastIntent = new Intent("RESPONSE");
        broadcastIntent.putExtra("response", response);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://94.126.205.209:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ResponseModel> call = apiInterface.response(new RequestModel(intent.getStringExtra("query")));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    String responseText = response.body().getResponse();
                    sendResponse(responseText);
                } else {
                    sendResponse("Произошла какая-то ошибка.");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                sendResponse("Произошла какая-то ошибка. Попробуйте позже.");
            }
        });
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

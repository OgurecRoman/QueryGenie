package com.example.querygenie.domain.query;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.querygenie.domain.model.RequestModel;
import com.example.querygenie.domain.model.ResponseModel;
import com.example.querygenie.presentation.viewModel.SharedViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryService extends Service {

    private void sendResponse(String response){
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
                    Log.d("aaa", "Успешный запрос " + response);
                    String responseText = response.body().getResponse();
                    sendResponse(responseText);
                } else {
                    Log.d("aaa", "НЕуспешный запрос " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("aaa", "ошипка " + t.getMessage());
            }
        });
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

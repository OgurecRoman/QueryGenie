package com.example.querygenie.presentation.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> responseLiveData = new MutableLiveData<>();

    public void setResponse(String response) {
        responseLiveData.postValue(response);
    }

    public LiveData<String> getResponse() {
        return responseLiveData;
    }
}

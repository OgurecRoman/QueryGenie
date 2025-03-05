package com.example.querygenie.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private boolean isHistory;

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public boolean isHistory() {
        return isHistory;
    }
}

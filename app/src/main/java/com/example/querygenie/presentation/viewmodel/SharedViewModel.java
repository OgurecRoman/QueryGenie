package com.example.querygenie.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private final MutableLiveData<String> sort = new MutableLiveData<>();
    private String searchText;
    private boolean isHistory;
    private int selectedSort = 0;

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void setSort(String query) {
        sort.setValue(query);
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public void setSelectedSort(String sort) {
        if (Objects.equals(sort, "Сбросить"))
            this.selectedSort = 0;
        else if (Objects.equals(sort, "Дата"))
            this.selectedSort = 1;
        else if (Objects.equals(sort, "Популярность"))
            this.selectedSort = 2;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public LiveData<String> getSort() {
        return sort;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public int getSelectedSort() {
        return selectedSort;
    }

    public String getSearchText() {
        return searchText;
    }
}

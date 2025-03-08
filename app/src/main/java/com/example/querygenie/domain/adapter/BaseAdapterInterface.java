package com.example.querygenie.domain.adapter;

import com.example.querygenie.presentation.viewmodel.SharedViewModel;

public interface BaseAdapterInterface {
    void reloadData(boolean isFav, SharedViewModel sharedViewModel);
}

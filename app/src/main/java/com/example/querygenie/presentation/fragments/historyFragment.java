package com.example.querygenie.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.querygenie.R;
import com.example.querygenie.presentation.viewmodel.SharedViewModel;
import com.example.querygenie.domain.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private ViewPagerAdapter pagerAdapter;

    public HistoryFragment() {
    }

    private void showMenu(ImageButton view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            String selectedSort = "Сбросить";

            if (item.getItemId() == R.id.sort_date) {
                selectedSort = "Дата";
            } else if (item.getItemId() == R.id.sort_popularity) {
                selectedSort = "Популярность";
            } else if (item.getItemId() == R.id.sort_clean) {
                selectedSort = "Сбросить";
            } else {
                return false;
            }

            if (!selectedSort.equals("Сбросить"))
                view.setImageResource(R.drawable.filteractive);
            else
                view.setImageResource(R.drawable.filter);

            sharedViewModel.setSort(selectedSort);

            return true;
        });
        popupMenu.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.setHistory(true);

        EditText searchText = view.findViewById(R.id.search);
        ImageButton filterBut = view.findViewById(R.id.filter);
        if (sharedViewModel.getSelectedSort() == 0)
            filterBut.setImageResource(R.drawable.filter);
        else
            filterBut.setImageResource(R.drawable.filteractive);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        List<Integer> tabTitles = Arrays.asList(R.string.all, R.string.favourite);
        pagerAdapter = new ViewPagerAdapter(
                getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(tabTitles.get(position))
        ).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                searchText.setText("");

                ListFragment fragment = pagerAdapter.getFragment(position);
                if (fragment != null) {
                    fragment.refreshAdapter();
                }
            }
        });

        filterBut.setOnClickListener(view1 -> {
            showMenu(filterBut);
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sharedViewModel.setSearchQuery(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }
}
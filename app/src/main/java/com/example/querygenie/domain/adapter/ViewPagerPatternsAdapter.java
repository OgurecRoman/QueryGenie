package com.example.querygenie.domain.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.querygenie.presentation.fragments.ListFragment;

import java.util.HashMap;
import java.util.Map;

public class ViewPagerPatternsAdapter extends FragmentStateAdapter {
    private final Map<Integer, ListFragment> fragmentMap = new HashMap<>();
    private final FragmentManager fragmentManager;

    public ViewPagerPatternsAdapter(FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
        this.fragmentManager = fm;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ListFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public ListFragment getFragment(int position) {
        return (ListFragment) fragmentManager.findFragmentByTag("f" + position);
    }
}

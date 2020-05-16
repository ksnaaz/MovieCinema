package com.nz.movie_cinema.view.activity;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.nz.movie_cinema.R;
import com.nz.movie_cinema.databinding.ActivityDashboardBinding;
import com.nz.movie_cinema.view.adapter.TabLayoutAdapter;
import com.nz.movie_cinema.view_model.AllMoviesViewModel;

public class DashboardActivity extends AppCompatActivity {
    private AllMoviesViewModel allMoviesViewModel;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        setSupportActionBar(binding.toolbar);
        allMoviesViewModel = ViewModelProviders.of(this).get(AllMoviesViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setHandler(this);
        binding.setManager(getSupportFragmentManager());

    }

    @BindingAdapter({"bind:handler"})
    public static void bindViewPagerAdapter(final ViewPager view, final DashboardActivity activity) {
        final TabLayoutAdapter adapter = new TabLayoutAdapter(view.getContext(), activity.getSupportFragmentManager());
        view.setAdapter(adapter);
    }

    @BindingAdapter({"bind:pager"})
    public static void bindViewPagerTabs(final TabLayout view, final ViewPager pagerView) {
        view.setupWithViewPager(pagerView, true);
    }


}
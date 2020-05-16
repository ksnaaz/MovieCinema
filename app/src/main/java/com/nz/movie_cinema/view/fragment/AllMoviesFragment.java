package com.nz.movie_cinema.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.nz.movie_cinema.R;
import com.nz.movie_cinema.databinding.FragmentAllmoviesBinding;
import com.nz.movie_cinema.model.MovieDetails;
import com.nz.movie_cinema.model.MoviePageResult;
import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.utils.AppUtil;
import com.nz.movie_cinema.utils.FavouriteClickListener;
import com.nz.movie_cinema.utils.ItemClickListener;
import com.nz.movie_cinema.utils.UpdateSearchedItemListener;
import com.nz.movie_cinema.view.activity.MovieDetailsActivity;
import com.nz.movie_cinema.view.adapter.AllLatestMoviesAdapter;
import com.nz.movie_cinema.view_model.AllMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 5/12/2020.
 */

public class AllMoviesFragment extends Fragment implements FavouriteClickListener, ItemClickListener, UpdateSearchedItemListener {
    public static final String TAG = AllMoviesFragment.class.getSimpleName();
    private FragmentAllmoviesBinding fragmentAllmoviesBinding;
    private AllLatestMoviesAdapter allMoviesAdapter;
    private AllMoviesViewModel allMoviesViewModel;
    List<Movies> tempList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentAllmoviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_allmovies, container, false);
        return fragmentAllmoviesBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        allMoviesViewModel = ViewModelProviders.of(getActivity()).get(AllMoviesViewModel.class);

        fragmentAllmoviesBinding.rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        allMoviesAdapter = new AllLatestMoviesAdapter(getActivity(), this, this, this);
        fragmentAllmoviesBinding.rvMovies.setAdapter(allMoviesAdapter);

        loadData();

        fragmentAllmoviesBinding.searchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAllmoviesBinding.searchEdit.setFocusableInTouchMode(true);
                fragmentAllmoviesBinding.searchEdit.requestFocus();
                AppUtil.showKeyboard(getActivity(), fragmentAllmoviesBinding.searchEdit);
                if(tempList.size() == 0) {
                    tempList.addAll(allMoviesAdapter.getAdapterList());
                }
                allMoviesAdapter.addDataIntoList(getLastSearchedMoviesFromDB());
            }
        });


        fragmentAllmoviesBinding.searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (v.getText().length() > 0) {
                        allMoviesAdapter.updateList(tempList);
                        allMoviesAdapter.getFilter().filter(v.getText());
                        return true;
                    }
                }
                return false;
            }
        });

        fragmentAllmoviesBinding.searchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allMoviesAdapter.addDataIntoList(tempList);
                fragmentAllmoviesBinding.searchEdit.setText("");
                fragmentAllmoviesBinding.searchCancel.setFocusableInTouchMode(false);
                AppUtil.hideKeyboard(getActivity(), v);
            }
        });

    }

    @Override
    public void onFavClick(Movies movie, boolean isFavourite) {
        if (isFavourite) {
            allMoviesViewModel.addFavouriteMovie(movie);
        } else {
            allMoviesViewModel.updateFavouriteMovie(movie, false);
        }
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent i = new Intent(getActivity(), MovieDetailsActivity.class);
        i.putExtra("movieId", movieId);
        startActivity(i);
    }

    private void loadData() {
        allMoviesViewModel.getLatestMovies().removeObservers(this);
        allMoviesViewModel.getLatestMovies().observe(this, new Observer<MoviePageResult>() {
            @Override
            public void onChanged(@Nullable MoviePageResult moviePageResult) {
                allMoviesAdapter.addDataIntoList(moviePageResult.getMovieResult());
            }
        });
    }

    @Override
    public void onUpdateSearchedItem(List<Movies> searchedMoviesList) {
        for (Movies movies : searchedMoviesList) {
            allMoviesViewModel.addSearchedMovie(movies);
        }
    }

    private List<Movies> getLastSearchedMoviesFromDB() {
        allMoviesViewModel.getLastSearchedDBMovies().removeObservers(this);
        final List<Movies> searchedMovies = new ArrayList<>();
        allMoviesViewModel.getLastSearchedDBMovies().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> SearchedMoviesList) {
                searchedMovies.addAll(SearchedMoviesList);
            }
        });
        return searchedMovies;
    }

}

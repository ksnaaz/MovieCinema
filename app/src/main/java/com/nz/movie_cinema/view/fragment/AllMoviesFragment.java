package com.nz.movie_cinema.view.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.nz.movie_cinema.model.RecentSearchedMovies;
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
    private boolean isLoading = false;
    private int pageCount = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentAllmoviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_allmovies, container, false);
        return fragmentAllmoviesBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        allMoviesViewModel = ViewModelProviders.of(this).get(AllMoviesViewModel.class);

        fragmentAllmoviesBinding.rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        allMoviesAdapter = new AllLatestMoviesAdapter(getActivity(), this, this, this);
        fragmentAllmoviesBinding.rvMovies.setAdapter(allMoviesAdapter);

        loadData();
        initScrollListener();

        allMoviesViewModel.getFilter().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s.isEmpty()) {
                    loadData();
                } else {
                    allMoviesAdapter.getFilter().filter(s);
                }
            }
        });
    }

    @Override
    public void onFavClick(Movies movie, boolean isFavourite) {
        if (isFavourite) {
            allMoviesViewModel.addFavouriteMovie(movie);
        } else {
            allMoviesViewModel.deleteFavouriteMovie(movie);
        }
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent i = new Intent(getActivity(), MovieDetailsActivity.class);
        i.putExtra("movieId", movieId);
        startActivity(i);
    }

    private void loadData() {
        allMoviesViewModel.getMovieList(pageCount).observe(this, new Observer<MoviePageResult>() {
            @Override
            public void onChanged(@Nullable MoviePageResult moviePageResult) {
                Log.e(TAG, "(onchanged Called) : moviePageResult page number = "+moviePageResult.getPage());
                isLoading = false;
                allMoviesAdapter.addDataIntoList(moviePageResult.getMovieResult());

            }
        });
    }

    @Override
    public void onUpdateSearchedItem(List<Movies> searchedMoviesList) {
        for (Movies movies : searchedMoviesList) {
            RecentSearchedMovies recentSearchedMovies = new RecentSearchedMovies();
            recentSearchedMovies.setId(movies.getId());
            recentSearchedMovies.setOriginalTitle(movies.getOriginalTitle());
            allMoviesViewModel.addSearchedMovie(recentSearchedMovies);
        }
    }

    private void initScrollListener() {
        fragmentAllmoviesBinding.rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == allMoviesAdapter.getAdapterList().size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        removeObservers();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageCount++;
                loadData();
            }
        },2000);
    }

    private void removeObservers(){
        final MutableLiveData<MoviePageResult> observable = allMoviesViewModel.getMovieList(pageCount);
        if(observable!=null
                && observable.hasObservers()) {
            observable.removeObservers(this);
        }
    }

}

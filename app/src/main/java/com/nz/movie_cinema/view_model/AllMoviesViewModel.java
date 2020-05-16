package com.nz.movie_cinema.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.nz.movie_cinema.database.MoviesDbFactory;
import com.nz.movie_cinema.model.MoviePageResult;
import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.rest.RestApiFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nz.movie_cinema.BaseConstants.API_KEY;

/**
 * Created by hp on 4/26/2020.
 */

public class AllMoviesViewModel extends AndroidViewModel {

    private MoviesDbFactory moviesDbFactory;
    private MutableLiveData<MoviePageResult> mutableLatestMoviesLiveData;
    private LiveData<List<Movies>> mSearchedMovies;

    public AllMoviesViewModel(Application application) {
        super(application);
        moviesDbFactory = new MoviesDbFactory(application);
        this.mutableLatestMoviesLiveData = new MutableLiveData<>();
        mSearchedMovies = moviesDbFactory.getRecentSearchMovies();
    }

    public void addFavouriteMovie(Movies movie) {
        if(moviesDbFactory.getItemById(movie.getId()).isEmpty()) {
            moviesDbFactory.addItem(movie);
        } else {
            updateFavouriteMovie(movie, true);
        }
    }

    public void updateFavouriteMovie(Movies movie, boolean update) {
        moviesDbFactory.updateFavoritsItem(movie, update);
    }

    public LiveData<MoviePageResult> getLatestMovies() {
        return getMutableLiveDataOfLatestMovies();
    }

    public MutableLiveData<MoviePageResult> getMutableLiveDataOfLatestMovies() {

        Call<MoviePageResult> call = RestApiFactory.create().getLatestMovies(API_KEY);
        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                MoviePageResult movie = response.body();
                if (movie != null) {
                    mutableLatestMoviesLiveData.setValue(movie);
                }
            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) { }
        });
        return mutableLatestMoviesLiveData;
    }

    public LiveData<List<Movies>> getLastSearchedDBMovies() { return mSearchedMovies; }

    public void addSearchedMovie(Movies movie) {
        if(moviesDbFactory.getItemById(movie.getId()).isEmpty()) {
            movie.setRecentSearch(true);
            moviesDbFactory.addItem(movie);
        } else {
            updateSearchedMovie(movie, true);
        }
    }

    public void updateSearchedMovie(Movies movie, boolean update) {
        moviesDbFactory.updateSearchItem(movie, update);
    }


}

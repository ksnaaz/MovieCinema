package com.nz.movie_cinema.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.nz.movie_cinema.database.MoviesDbFactory;
import com.nz.movie_cinema.model.Movies;

import java.util.List;

/**
 * Created by hp on 4/26/2020.
 */


public class FavoriteMoviesViewModel extends AndroidViewModel {

    private MoviesDbFactory moviesDbFactory;
    private LiveData<List<Movies>> mFavMovies;

    public FavoriteMoviesViewModel (Application application) {
        super(application);
        moviesDbFactory = new MoviesDbFactory(application);
        mFavMovies = moviesDbFactory.getAllFavMovies();
    }

    public LiveData<List<Movies>> getFavMovies() { return mFavMovies; }

}
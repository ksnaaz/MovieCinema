package com.nz.movie_cinema.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.model.RecentSearchedMovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 4/27/2020.
 */

public class MoviesDbFactory {
    private FavMoviesDao favMoviesDao;
    private RecentSearchedMoviesDao recentSearchedMoviesDao;
    private LiveData<List<Movies>> mAllFavItems;
    private LiveData<List<RecentSearchedMovies>> mRecentSearchedItems;

    public MoviesDbFactory(Application appController){
        MoviesDatabase database = MoviesDatabase.getDatabaseInstance(appController.getApplicationContext());
        favMoviesDao = database.favMoviesDao();
        recentSearchedMoviesDao = database.recentSearchedMoviesDao();
        mAllFavItems = favMoviesDao.getAllFavMovies();
        mRecentSearchedItems = recentSearchedMoviesDao.getRecentSearchedMovies();
    }

    public void addFavItem(final Movies movie){
        insertDeleteMovies(movie, true);
    }

    public void deleteFavItem(final Movies movie){
        insertDeleteMovies(movie, false);
    }

    public LiveData<List<Movies>> getAllFavMovies() {
        return mAllFavItems;
    }

    public void insertDeleteMovies(final Movies movies, final boolean add) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if(add) {
                    favMoviesDao.insert(movies);
                } else {
                    favMoviesDao.delete(movies);
                }
                return null;
            }
        }.execute();
    }

    public void addRecentSearchedItem(final RecentSearchedMovies movie){
        insertDeleteSearchedMovies(movie, true);
    }

    public void deleteSearchedItem(final RecentSearchedMovies movie){
        insertDeleteSearchedMovies(movie, false);
    }

    public LiveData<List<RecentSearchedMovies>> getmRecentSearchedMovies() {
        return mRecentSearchedItems;
    }

    public void insertDeleteSearchedMovies(final RecentSearchedMovies movies, final boolean add) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if(add) {
                    recentSearchedMoviesDao.insert(movies);
                } else {
                    recentSearchedMoviesDao.delete(movies);
                }
                return null;
            }
        }.execute();
    }

}

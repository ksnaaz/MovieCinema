package com.nz.movie_cinema.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Switch;

import com.nz.movie_cinema.model.Movies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 4/27/2020.
 */

public class MoviesDbFactory {
    private MoviesDao moviesDao;
    private LiveData<List<Movies>> mAllFavItems;
    private LiveData<List<Movies>> mLastSearchedItems;

    public MoviesDbFactory(Application appController){
        MoviesDatabase database = MoviesDatabase.getDatabaseInstance(appController.getApplicationContext());
        moviesDao = database.moviesDao();
        mAllFavItems = moviesDao.getAllFavMovies();
        mLastSearchedItems = moviesDao.getRecentSearchMovies();
    }

    public void addItem(final Movies movie){
        insertDeleteMovies(movie, 0, false);
    }

    public void updateFavoritsItem(final Movies movie, final boolean update){
        insertDeleteMovies(movie, 1, update);
    }

    public void updateSearchItem(final Movies movie, final boolean update){
        insertDeleteMovies(movie, 2, update);
    }

    public void deleteItem(final Movies movie){
        insertDeleteMovies(movie, 3, false);
    }

    public List<Movies> getItemById(final int movieId){
        final List<Movies>itemList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                itemList.addAll(moviesDao.getItemById(movieId));
            }
        }).start();
        return itemList;
    }

    public LiveData<List<Movies>> getAllFavMovies() {
        return mAllFavItems;
    }

    public LiveData<List<Movies>> getRecentSearchMovies() {
        return mLastSearchedItems;
    }

    public void insertDeleteMovies(final Movies movies, final int action, final boolean update) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                switch (action){
                    case 0:
                        moviesDao.addMovieItems(movies);
                        break;
                    case 1:
                        moviesDao.updateFavorite(movies.getId(), update);
                        break;
                    case 2:
                        moviesDao.updateSearch(movies.getId(), update);
                        break;
                    case 3:
                        moviesDao.deleteMovieItems(movies);
                        break;
                }
                return null;
            }
        }.execute();
    }

}

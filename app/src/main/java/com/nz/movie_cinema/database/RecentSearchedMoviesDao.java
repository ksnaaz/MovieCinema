package com.nz.movie_cinema.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.model.RecentSearchedMovies;

import java.util.List;

@Dao
public interface RecentSearchedMoviesDao extends BaseDao<RecentSearchedMovies> {

    @Query("SELECT * FROM " + "recent_searched_movies_table")
    LiveData<List<RecentSearchedMovies>> getRecentSearchedMovies();

}
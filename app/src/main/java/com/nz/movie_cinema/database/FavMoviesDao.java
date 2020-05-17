package com.nz.movie_cinema.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nz.movie_cinema.model.Movies;

import java.util.List;

@Dao
public interface FavMoviesDao extends BaseDao<Movies> {

    @Query("SELECT * FROM " + "fav_movies_table")
    LiveData<List<Movies>> getAllFavMovies();

}
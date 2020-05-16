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
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovieItems(Movies movies);

    @Query("UPDATE " + "movies_table" + " SET isFavorite = :isFav WHERE id = :tid")
    int updateFavorite(long tid, boolean isFav);

    @Query("UPDATE " + "movies_table" + " SET isRecentSearch = :isSearched WHERE id = :tid")
    int updateSearch(long tid, boolean isSearched);

    @Query("SELECT * from movies_table WHERE id= :id")
    List<Movies> getItemById(int id);

    @Delete
    void deleteMovieItems(Movies movies);

    @Query("SELECT * FROM " + "movies_table" + " WHERE isFavorite = 1")
    LiveData<List<Movies>> getAllFavMovies();

    @Query("SELECT * FROM " + "movies_table" + " WHERE isRecentSearch = 1")
    LiveData<List<Movies>> getRecentSearchMovies();

}
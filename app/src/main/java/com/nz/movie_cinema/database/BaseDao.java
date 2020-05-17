package com.nz.movie_cinema.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nz.movie_cinema.model.Movies;

import java.util.List;

@Dao
public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... entity);

    @Update
    void update(T entity);

    @Delete
    void delete(T entity);

}
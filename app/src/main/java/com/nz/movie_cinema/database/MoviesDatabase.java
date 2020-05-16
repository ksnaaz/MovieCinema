package com.nz.movie_cinema.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nz.movie_cinema.model.Movies;

@Database(entities = {Movies.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract MoviesDao moviesDao();

    public static MoviesDatabase sInstance;

    // Get a database instance
    public static synchronized MoviesDatabase getDatabaseInstance(Context context) {
        if (sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    // Create the database
    static MoviesDatabase create(Context context) {
        RoomDatabase.Builder<MoviesDatabase> builder = Room.databaseBuilder(context.getApplicationContext(),
                MoviesDatabase.class,
                "moviedb");
        return builder.build();
    }
}
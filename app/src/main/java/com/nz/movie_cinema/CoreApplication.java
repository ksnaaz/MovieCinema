package com.nz.movie_cinema;

import android.app.Application;


public class CoreApplication extends Application {
    private static CoreApplication instance;

    public static CoreApplication getInstance() {
        if (instance == null) {
            instance = new CoreApplication();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}


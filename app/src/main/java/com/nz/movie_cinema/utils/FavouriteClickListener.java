package com.nz.movie_cinema.utils;


import com.nz.movie_cinema.model.Movies;

@SuppressWarnings("ALL")
public interface FavouriteClickListener {
    void onFavClick(Movies movie, boolean isFavourite);
}
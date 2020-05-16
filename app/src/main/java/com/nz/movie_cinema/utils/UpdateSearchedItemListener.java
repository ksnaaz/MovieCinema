package com.nz.movie_cinema.utils;


import com.nz.movie_cinema.model.Movies;

import java.util.List;

@SuppressWarnings("ALL")
public interface UpdateSearchedItemListener {
    void onUpdateSearchedItem(List<Movies> searchedMovies);
}
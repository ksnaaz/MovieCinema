package com.nz.movie_cinema.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.nz.movie_cinema.database.MoviesDbFactory;
import com.nz.movie_cinema.model.MoviePageResult;
import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.model.RecentSearchedMovies;
import com.nz.movie_cinema.rest.RestApiFactory;
import com.nz.movie_cinema.rest.RestApiRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nz.movie_cinema.BaseConstants.API_KEY;

/**
 * Created by hp on 4/26/2020.
 */

public class AllMoviesViewModel extends AndroidViewModel {

    private MoviesDbFactory moviesDbFactory;
    private RestApiRepository restApiRepository;
    private LiveData<List<RecentSearchedMovies>> mSearchedMovies;
    private MutableLiveData<String> getFilter;

    public AllMoviesViewModel(Application application) {
        super(application);
        moviesDbFactory = new MoviesDbFactory(application);
        restApiRepository = new RestApiRepository();
        mSearchedMovies = moviesDbFactory.getmRecentSearchedMovies();
        getFilter = new MutableLiveData<>();
    }

    //adding favorite movie in db
    public void addFavouriteMovie(Movies movie) {
        moviesDbFactory.addFavItem(movie);
    }

    //deleting favorite movie in db
    public void deleteFavouriteMovie(Movies movie) {
        moviesDbFactory.deleteFavItem(movie);
    }

    //calling api to get popular movies
    public MutableLiveData<MoviePageResult> getMovieList(int page) {
        return restApiRepository.loadMovies(page);
    }

    //get recent searched movie from db
    public LiveData<List<RecentSearchedMovies>> getLastSearchedDBMovies() {
        return mSearchedMovies;
    }

    //adding recent searched movie in db
    public void addSearchedMovie(RecentSearchedMovies recentSearchedMovies) {
        moviesDbFactory.addRecentSearchedItem(recentSearchedMovies);
    }

    //get filter mutable livedata on search query submit
    public LiveData<String> getFilter() {
        return getFilter;
    }

    //search query apply to get gilter
    public void setSearchQuery(String searchQuery) {
        getFilter.setValue(searchQuery);
    }

}

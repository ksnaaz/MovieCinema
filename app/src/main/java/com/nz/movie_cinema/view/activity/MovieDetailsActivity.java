package com.nz.movie_cinema.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.nz.movie_cinema.R;
import com.nz.movie_cinema.databinding.ActivityMovieDetailsBinding;
import com.nz.movie_cinema.model.MovieCastCrew;
import com.nz.movie_cinema.model.MovieDetails;
import com.nz.movie_cinema.model.MovieVideos;
import com.nz.movie_cinema.model.SimilarMovies;
import com.nz.movie_cinema.utils.AppUtil;
import com.nz.movie_cinema.view.adapter.MovieCastAdapter;
import com.nz.movie_cinema.view.adapter.MovieCrewAdapter;
import com.nz.movie_cinema.view.adapter.MovieVideosAdapter;
import com.nz.movie_cinema.view.adapter.SimilarMoviesAdapter;
import com.nz.movie_cinema.view_model.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding movieDetailsBinding;
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieCastAdapter movieCastAdapter;
    private MovieCrewAdapter movieCrewAdapter;
    private SimilarMoviesAdapter similarMoviesAdapter;
    private MovieVideosAdapter movieVideosAdapter;
    private int movieId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieDetailsBinding = DataBindingUtil.setContentView(MovieDetailsActivity.this, R.layout.activity_movie_details);
        movieDetailsBinding.setLifecycleOwner(this);
        movieDetailsBinding.setMovieDetailsViewModel(movieDetailsViewModel);
        movieId = getIntent().getIntExtra("movieId", 0);
        movieDetailsViewModel.getMovieDetails(movieId).observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(@Nullable MovieDetails moviesResult) {
                Log.e("Movie Title ", moviesResult.getTitle());
                setDataInView(moviesResult);
            }
        });

        //fetching movie cast and crew
        movieDetailsBinding.rvMovieCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false ));
        movieCastAdapter = new MovieCastAdapter(this);
        movieDetailsBinding.rvMovieCast.setAdapter(movieCastAdapter);

        movieDetailsBinding.rvMovieCrew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false ));
        movieCrewAdapter = new MovieCrewAdapter(this);
        movieDetailsBinding.rvMovieCrew.setAdapter(movieCrewAdapter);

        movieDetailsViewModel.getMovieCastCrew(movieId).observe(this, new Observer<MovieCastCrew>() {
            @Override
            public void onChanged(@Nullable MovieCastCrew movieCastCrew) {
                movieCastAdapter.addDataIntoList(movieCastCrew.getCast());
                movieCrewAdapter.addDataIntoList(movieCastCrew.getCrew());
            }
        });

        //fetching similar movies
        movieDetailsBinding.rvMovieSimilar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false ));
        similarMoviesAdapter = new SimilarMoviesAdapter(this);
        movieDetailsBinding.rvMovieSimilar.setAdapter(similarMoviesAdapter);
        movieDetailsViewModel.getSimilarMovies(movieId).observe(this, new Observer<SimilarMovies>() {
            @Override
            public void onChanged(@Nullable SimilarMovies similarMovies) {
                similarMoviesAdapter.addDataIntoList(similarMovies.getResults());
            }
        });

        //fetching movie videos
        movieDetailsBinding.rvMovieVideos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false ));
        movieVideosAdapter = new MovieVideosAdapter(this);
        movieDetailsBinding.rvMovieVideos.setAdapter(movieVideosAdapter);
        movieDetailsViewModel.getMovieVideos(movieId).observe(this, new Observer<MovieVideos>() {
            @Override
            public void onChanged(@Nullable MovieVideos movieVideos) {
                movieVideosAdapter.addDataIntoList(movieVideos.getResults());
            }
        });


        setSupportActionBar(movieDetailsBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movieDetailsBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //set summary of movie
    private void setDataInView(MovieDetails movieDetails) {
        Picasso.with(movieDetailsBinding.bannerImage.getContext()).load(AppUtil.movieImagePathBuilder(movieDetails.getPoster_path())).placeholder(R.drawable.movie_detail_placeholder).fit().centerCrop().into(movieDetailsBinding.bannerImage);
        movieDetailsBinding.circularProgressbar.setProgress(movieDetails.getVote_average()*10, 100);
        movieDetailsBinding.movieTitle.setText(movieDetails.getTitle());
        movieDetailsBinding.releaseDate.setText("Released on : "+AppUtil.changeDateFormat(movieDetails.getRelease_date()));
        String laguageAvailable = "";
        for (MovieDetails.SpokenLanguagesBean bean : movieDetails.getSpoken_languages()) {
            laguageAvailable = laguageAvailable.isEmpty() ? bean.getName() : laguageAvailable + " , " + bean.getName();
        }
        movieDetailsBinding.languages.setText(laguageAvailable);
        movieDetailsBinding.movieTagline.setVisibility(movieDetails.getTagline() != null && !movieDetails.getTagline().isEmpty() ? View.VISIBLE : View.GONE);
        movieDetailsBinding.movieTagline.setText(movieDetails.getTagline());
        movieDetailsBinding.movieDescription.setText(movieDetails.getOverview());
    }

}

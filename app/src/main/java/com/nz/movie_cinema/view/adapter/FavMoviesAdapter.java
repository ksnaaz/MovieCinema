package com.nz.movie_cinema.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nz.movie_cinema.R;
import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.utils.AppUtil;
import com.nz.movie_cinema.utils.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.MoviesItemViewHolder> {
    private Context context;
    private List<Movies> moviesArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;

    public FavMoviesAdapter(Context mContext, ItemClickListener clickListener) {
        this.context = mContext;
        moviesArrayList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
        this.itemClickListener = clickListener;
    }

    @NonNull
    @Override
    public MoviesItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_favmovie_card, viewGroup, false);
        return new MoviesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesItemViewHolder viewHolder, final int i) {
        Movies movie = moviesArrayList.get(i);
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.releasedDate.setText("Released on : "+AppUtil.changeDateFormat(movie.getReleaseDate()));
        viewHolder.languages.setText("Original Language : "+movie.getOriginalLanguage());
        Picasso.with(viewHolder.itemImage.getContext()).load(AppUtil.movieImagePathBuilder(movie.getPosterPath())).placeholder(R.drawable.movie_placeholder).fit().centerCrop().into(viewHolder.itemImage);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onMovieClick(moviesArrayList.get(i).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }


    public class MoviesItemViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView itemImage;
        private TextView title;
        private TextView releasedDate;
        private TextView languages;
        private CardView cardView;

        public MoviesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemImage = itemView.findViewById(R.id.item_image);
            cardView = itemView.findViewById(R.id.root_view);
            title = itemView.findViewById(R.id.title);
            releasedDate = itemView.findViewById(R.id.released_date);
            languages = itemView.findViewById(R.id.languages);
        }
    }

    public void addDataIntoList(List<Movies> favouriteMovies) {
        moviesArrayList.clear();
        moviesArrayList.addAll(favouriteMovies);
        notifyDataSetChanged();
        Log.e("Fav Adapter", "(addDataIntoList) size = " + moviesArrayList.size());
    }
}
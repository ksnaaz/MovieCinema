package com.nz.movie_cinema.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nz.movie_cinema.R;
import com.nz.movie_cinema.model.MovieCastCrew;
import com.nz.movie_cinema.model.SimilarMovies;
import com.nz.movie_cinema.utils.AppUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.MoviesItemViewHolder> {
    private Context context;
    private List<SimilarMovies.ResultsBean> similarMoviesArrayList;
    private LayoutInflater mInflater;

    public SimilarMoviesAdapter(Context mContext) {
        this.context = mContext;
        similarMoviesArrayList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MoviesItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_movie_detail_card, viewGroup, false);
        return new MoviesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesItemViewHolder viewHolder, final int i) {
        SimilarMovies.ResultsBean similarMovies = similarMoviesArrayList.get(i);
        viewHolder.title.setText(similarMovies.getOriginal_title());
        viewHolder.subtitle.setText(AppUtil.changeDateFormat(similarMovies.getRelease_date()));
        Picasso.with(viewHolder.itemImage.getContext()).load(AppUtil.movieImagePathBuilder(similarMovies.getPoster_path())).placeholder(R.drawable.movie_placeholder).fit().centerCrop().into(viewHolder.itemImage);
    }

    @Override
    public int getItemCount() {
        return similarMoviesArrayList.size();
    }


    public class MoviesItemViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView itemImage;
        private TextView title;
        private TextView subtitle;
        private CardView cardView;

        public MoviesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemImage = itemView.findViewById(R.id.item_image);
            cardView = itemView.findViewById(R.id.root_view);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
        }
    }

    public void addDataIntoList(List<SimilarMovies.ResultsBean> resultsBeanList) {
        similarMoviesArrayList.clear();
        similarMoviesArrayList.addAll(resultsBeanList);
        notifyDataSetChanged();
    }
}
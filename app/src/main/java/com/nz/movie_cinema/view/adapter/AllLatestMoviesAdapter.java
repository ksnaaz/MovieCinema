package com.nz.movie_cinema.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nz.movie_cinema.R;
import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.utils.AppUtil;
import com.nz.movie_cinema.utils.FavouriteClickListener;
import com.nz.movie_cinema.utils.ItemClickListener;
import com.nz.movie_cinema.utils.UpdateSearchedItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllLatestMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private Context context;
    private List<Movies> moviesArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;
    private FavouriteClickListener favouriteClickListener;
    private UpdateSearchedItemListener searchedItemListener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public AllLatestMoviesAdapter(Context mContext, ItemClickListener clickListener,
                                  FavouriteClickListener favouriteClickListener, UpdateSearchedItemListener updateSearchedItemListener) {
        this.context = mContext;
        moviesArrayList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
        this.itemClickListener = clickListener;
        this.favouriteClickListener = favouriteClickListener;
        this.searchedItemListener = updateSearchedItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_movie_card, viewGroup, false);
            return new ItemViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.loading_layout, viewGroup, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(final ItemViewHolder viewHolder, final int i) {
        Movies movie = moviesArrayList.get(i);
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.releasedDate.setText("Released on : "+AppUtil.changeDateFormat(movie.getReleaseDate()));
        viewHolder.languages.setText("Original Language : "+movie.getOriginalLanguage());
        viewHolder.favIcon.setImageDrawable(context.getResources().getDrawable(movie.isFavorite() ? R.drawable.fav_selected : R.drawable.fav_unselected_black));
        Picasso.with(viewHolder.itemImage.getContext()).load(AppUtil.movieImagePathBuilder(movie.getPosterPath())).placeholder(R.drawable.movie_placeholder).fit().centerCrop().into(viewHolder.itemImage);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onMovieClick(moviesArrayList.get(i).getId());
            }
        });
        viewHolder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAlreadyFavorite = moviesArrayList.get(i).isFavorite();
                moviesArrayList.get(i).setFavorite(!isAlreadyFavorite);
                viewHolder.favIcon.setImageDrawable(context.getResources().getDrawable(isAlreadyFavorite ? R.drawable.fav_unselected_black : R.drawable.fav_selected));
                favouriteClickListener.onFavClick(moviesArrayList.get(i), !isAlreadyFavorite);
            }
        });

    }


    @Override
    public int getItemCount() {
        return moviesArrayList == null ? 0 : moviesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return moviesArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView itemImage;
        private TextView title;
        private TextView releasedDate;
        private TextView languages;
        private CardView cardView;
        private ImageView favIcon;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemImage = itemView.findViewById(R.id.item_image);
            cardView = itemView.findViewById(R.id.root_view);
            title = itemView.findViewById(R.id.title);
            releasedDate = itemView.findViewById(R.id.released_date);
            languages = itemView.findViewById(R.id.languages);
            favIcon = itemView.findViewById(R.id.fav_icon);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                List<Movies>mFilteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    mFilteredList = moviesArrayList;
                } else {
                    for (Movies movie : moviesArrayList) {
                        if(charString.length() == 1) {
                            Pattern p = Pattern.compile("\\b[a-zA-Z]");
                            Matcher m = p.matcher(movie.getOriginalTitle());
                            while (m.find()) {
                                if(m.group().toLowerCase().equals(charString.toLowerCase())) {
                                    mFilteredList.add(movie);
                                    break;
                                }
                            }
                        } else if ( movie.getOriginalTitle().toLowerCase().indexOf(charString.toLowerCase()) != -1 ) {
                            mFilteredList.add(movie);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                moviesArrayList.clear();
                addDataIntoList((ArrayList<Movies>) filterResults.values);
                searchedItemListener.onUpdateSearchedItem((ArrayList<Movies>) filterResults.values);
            }
        };
    }

    public void addDataIntoList(List<Movies> moviesList) {
        moviesArrayList.addAll(moviesList);
        notifyDataSetChanged();
    }

    public List<Movies> getAdapterList(){
        return moviesArrayList;
    }
}
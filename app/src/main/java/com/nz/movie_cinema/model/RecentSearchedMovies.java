package com.nz.movie_cinema.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
@Entity(tableName = "recent_searched_movies_table")
public class RecentSearchedMovies implements Parcelable {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String originalTitle;

    public RecentSearchedMovies(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    protected RecentSearchedMovies(Parcel in) {
        id = in.readInt();
        originalTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(originalTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecentSearchedMovies> CREATOR = new Creator<RecentSearchedMovies>() {
        @Override
        public RecentSearchedMovies createFromParcel(Parcel in) {
            return new RecentSearchedMovies(in);
        }

        @Override
        public RecentSearchedMovies[] newArray(int size) {
            return new RecentSearchedMovies[size];
        }
    };

    public static DiffUtil.ItemCallback<RecentSearchedMovies> DIFF_CALLBACK = new DiffUtil.ItemCallback<RecentSearchedMovies>() {
        @Override
        public boolean areItemsTheSame(@NonNull RecentSearchedMovies oldItem, @NonNull RecentSearchedMovies newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RecentSearchedMovies oldItem, @NonNull RecentSearchedMovies newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        RecentSearchedMovies movie = (RecentSearchedMovies) obj;
        return movie.id == this.id;
    }


}
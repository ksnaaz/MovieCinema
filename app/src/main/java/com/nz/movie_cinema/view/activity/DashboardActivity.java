package com.nz.movie_cinema.view.activity;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.nz.movie_cinema.R;
import com.nz.movie_cinema.databinding.ActivityDashboardBinding;
import com.nz.movie_cinema.model.Movies;
import com.nz.movie_cinema.model.RecentSearchedMovies;
import com.nz.movie_cinema.view.adapter.TabLayoutAdapter;
import com.nz.movie_cinema.view_model.AllMoviesViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private AllMoviesViewModel allMoviesViewModel;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        setSupportActionBar(binding.toolbar);
        allMoviesViewModel = ViewModelProviders.of(this).get(AllMoviesViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setHandler(this);
        binding.setManager(getSupportFragmentManager());

        initSearchAdapter();
    }

    @BindingAdapter({"bind:handler"})
    public static void bindViewPagerAdapter(final ViewPager view, final DashboardActivity activity) {
        final TabLayoutAdapter adapter = new TabLayoutAdapter(view.getContext(), activity.getSupportFragmentManager());
        view.setAdapter(adapter);
    }

    @BindingAdapter({"bind:pager"})
    public static void bindViewPagerTabs(final TabLayout view, final ViewPager pagerView) {
        view.setupWithViewPager(pagerView, true);
    }

    private void initSearchAdapter(){
        final String[] from = new String[] {"recent_search"};
        final int[] to = new int[] {R.id.text1};
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.search_layout_adapter,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        //add suggestion adapter
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                allMoviesViewModel.setSearchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchView.getQuery().length() == 0) {
                    allMoviesViewModel.setSearchQuery("");
                } else if(searchView.getQuery().length() == 1) {
                    getLastSearchedMoviesFromDB();
                }
                return false;
            }
        });

        // Getting selected (clicked) item suggestion
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                String txt = cursor.getString(cursor.getColumnIndex("recent_search"));
                searchView.setQuery(txt, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
        return true;
    }

    //fetching recent search from db
    private void getLastSearchedMoviesFromDB() {
        allMoviesViewModel.getLastSearchedDBMovies().removeObservers(this);
        final ArrayList<String> searchedMovies = new ArrayList<>();
        allMoviesViewModel.getLastSearchedDBMovies().observe(this, new Observer<List<RecentSearchedMovies>>() {
            @Override
            public void onChanged(@Nullable List<RecentSearchedMovies> SearchedMoviesList) {
                searchedMovies.clear();
                for(RecentSearchedMovies movies : SearchedMoviesList) {
                    searchedMovies.add(movies.getOriginalTitle());
                }

                String[] suggestionArray = new String[searchedMovies.size()];
                searchedMovies.toArray(suggestionArray);

                Log.e(DashboardActivity.class.getCanonicalName(), "suggestion array : "+ Arrays.toString(suggestionArray));

                final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "recent_search" });
                for (int i=0; i<suggestionArray.length; i++) {
                    c.addRow(new Object[] {i, suggestionArray[i]});
                }
                mAdapter.changeCursor(c);
            }
        });
    }
}
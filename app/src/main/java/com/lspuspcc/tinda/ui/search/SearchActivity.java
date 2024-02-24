package com.lspuspcc.tinda.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.ui.searchbar.SearchBarViewModel;
import com.lspuspcc.tinda.ui.searchbar.SearchResultViewModel;

public class SearchActivity extends AppCompatActivity {
    private SearchResultViewModel mSearchResultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(searchBinding.getRoot());

        SearchBarViewModel searchBarViewModel = new SearchBarViewModel(searchBinding.includeLSearchBar, "search");
        mSearchResultViewModel = new SearchResultViewModel(this, searchBarViewModel,
                searchBinding.includeLSearchResult, searchBinding.nestedSVSearchResults, "search");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchResultViewModel = null;
    }

    public void subCategoryOnClick(View view) {
        mSearchResultViewModel.updateResults(view.getTag().toString());
    }
}

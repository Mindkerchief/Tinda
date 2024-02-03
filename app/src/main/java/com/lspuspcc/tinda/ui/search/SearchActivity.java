package com.lspuspcc.tinda.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.ui.searchbar.SearchBarViewModel;
import com.lspuspcc.tinda.ui.searchbar.SearchResultViewModel;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mSearchBinding;
    private SearchResultViewModel mSearchResultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mSearchBinding.getRoot());

        SearchBarViewModel searchBarViewModel = new SearchBarViewModel(this,
                mSearchBinding.includeLSearchBar, "search");
        mSearchResultViewModel = new SearchResultViewModel(this, searchBarViewModel,
                mSearchBinding.includeLSearchResult, mSearchBinding.nestedSVSearchResults, "search");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchResultViewModel = null;
        mSearchBinding = null;
    }

    public void subCategoryOnClick(View view) {
        mSearchResultViewModel.updateResults();
    }
}

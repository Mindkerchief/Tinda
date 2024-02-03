package com.lspuspcc.tinda.ui.deal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lspuspcc.tinda.databinding.ActivityDealBinding;
import com.lspuspcc.tinda.ui.searchbar.SearchBarViewModel;
import com.lspuspcc.tinda.ui.searchbar.SearchResultViewModel;

public class DealActivity extends AppCompatActivity {
    private ActivityDealBinding mDealBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDealBinding = ActivityDealBinding.inflate(getLayoutInflater());
        setContentView(mDealBinding.getRoot());

        SearchBarViewModel searchBarViewModel = new SearchBarViewModel(this,
                mDealBinding.includeLSearchBar, "deal");
        new SearchResultViewModel(this, searchBarViewModel,
                mDealBinding.includeLSearchResult, mDealBinding.nestedSVSearchResults, "deal");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDealBinding = null;
    }
}
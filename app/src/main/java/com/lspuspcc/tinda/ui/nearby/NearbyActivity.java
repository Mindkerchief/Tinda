package com.lspuspcc.tinda.ui.nearby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lspuspcc.tinda.databinding.ActivityNearbyBinding;
import com.lspuspcc.tinda.ui.searchbar.SearchBarViewModel;
import com.lspuspcc.tinda.ui.searchbar.SearchResultViewModel;

public class NearbyActivity extends AppCompatActivity {
    private ActivityNearbyBinding mNearbyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNearbyBinding = ActivityNearbyBinding.inflate(getLayoutInflater());
        setContentView(mNearbyBinding.getRoot());

        SearchBarViewModel searchBarViewModel = new SearchBarViewModel(mNearbyBinding.includeLSearchBar, "nearby");
        new SearchResultViewModel(this, searchBarViewModel,
                mNearbyBinding.includeLSearchResult, mNearbyBinding.nestedSVSearchResults, "nearby");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNearbyBinding = null;
    }
}

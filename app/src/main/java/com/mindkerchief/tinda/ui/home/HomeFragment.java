package com.mindkerchief.tinda.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mindkerchief.tinda.ui.deal.DealActivity;
import com.mindkerchief.tinda.ui.favorite.FavoriteActivity;
import com.mindkerchief.tinda.ui.maps.MapsActivity;
import com.mindkerchief.tinda.ui.nearby.NearbyActivity;
import com.mindkerchief.tinda.ui.search.SearchActivity;
import com.mindkerchief.tinda.databinding.FragmentHomeBinding;
import com.mindkerchief.tinda.ui.searchbar.SearchBarViewModel;
import com.mindkerchief.tinda.ui.searchbar.SearchResultViewModel;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mHomeBinding;
    private Intent mIntentSearch, mIntentNearby, mIntentDeal, mIntentMap, mIntentFavorite;
    private SearchView mSearchVSearchField;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Search Bar & Buttons Views
        mIntentSearch = new Intent(getActivity(), SearchActivity.class);
        mIntentNearby = new Intent(getActivity(), NearbyActivity.class);
        mIntentDeal = new Intent(getActivity(), DealActivity.class);
        mIntentMap = new Intent(getActivity(), MapsActivity.class);
        mIntentFavorite = new Intent(getActivity(), FavoriteActivity.class);
        mSearchVSearchField = mHomeBinding.includeLSearchBar.searchVSearchField;

        mSearchVSearchField.setIconifiedByDefault(true);
        SearchBarViewModel searchBarViewModel = new SearchBarViewModel();
        new SearchResultViewModel(getContext(), searchBarViewModel, mHomeBinding.includeLSearchResult,
                mHomeBinding.nestedSVRecommendations, "home");

        // Handle Search Bar & Buttons Event
        mHomeBinding.includeLSearchBar.btnSearchCategory.setOnClickListener(v -> homeSearchBarOnClick(false));
        mSearchVSearchField.setOnClickListener(v -> homeSearchBarOnClick(true));
        mSearchVSearchField.setOnSearchClickListener(v -> homeSearchBarOnClick(true));

        mHomeBinding.btnNearby.setOnClickListener(v -> startActivity(mIntentNearby));
        mHomeBinding.btnDeal.setOnClickListener(v -> startActivity(mIntentDeal));
        mHomeBinding.btnMap.setOnClickListener(v -> startActivity(mIntentMap));
        mHomeBinding.btnFavorite.setOnClickListener(v -> startActivity(mIntentFavorite));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeBinding = null;
    }

    private void homeSearchBarOnClick(boolean showCategory) {
        SearchBarViewModel.sShowCategory = showCategory;
        startActivity(mIntentSearch);
        mSearchVSearchField.setIconified(true);
    }
}

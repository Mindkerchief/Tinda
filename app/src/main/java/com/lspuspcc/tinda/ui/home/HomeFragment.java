package com.lspuspcc.tinda.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.ui.deal.DealActivity;
import com.lspuspcc.tinda.ui.favorite.FavoriteActivity;
import com.lspuspcc.tinda.ui.maps.MapsActivity;
import com.lspuspcc.tinda.ui.nearby.NearbyActivity;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.ui.search.SearchActivity;
import com.lspuspcc.tinda.domain.StoreModel;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;
import com.lspuspcc.tinda.databinding.FragmentHomeBinding;
import com.lspuspcc.tinda.ui.searchbar.SearchBarViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mHomeBinding;
    private Intent mIntentSearch, mIntentNearby, mIntentDeal, mIntentMap, mIntentFavorite;
    private SearchView mSearchVSearchField;
    private ArrayList<ProductModel> mProductModels;
    private ArrayList<StoreModel> mStoreModels;

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

        RecyclerView recyclerVRecommendedProducts = mHomeBinding.recyclerVRecommendedProducts;
        RecyclerView recyclerVNearbyStore = mHomeBinding.recyclerVNearbyStores;
        SetupModel setupModel = new SetupModel();

        mSearchVSearchField.setIconifiedByDefault(true);

        // Setup Home Stores and Products Recycler View
        mProductModels = setupModel.setupProductModel();
        ProductRecyclerViewAdapter productRVAdapter = new ProductRecyclerViewAdapter(requireContext(), mProductModels);
        recyclerVRecommendedProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerVRecommendedProducts.setAdapter(productRVAdapter);

        mStoreModels = setupModel.setupStoreModel(false);
        StoreRecyclerViewAdapter storeRVAdapter = new StoreRecyclerViewAdapter(requireContext(),
                mStoreModels, R.layout.card_store_vertical);
        recyclerVNearbyStore.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerVNearbyStore.setAdapter(storeRVAdapter);

        // Handle Search Bar & Buttons Event
        mHomeBinding.includeLSearchBar.btnSearchCategoryFilter.setOnClickListener(v -> homeSearchBarOnClick(false));
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

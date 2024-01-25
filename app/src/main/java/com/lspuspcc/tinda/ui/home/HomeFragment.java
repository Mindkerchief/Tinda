package com.lspuspcc.tinda.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private Intent mIntentSearch, mIntentNearby, mIntentDeal, mIntentMap, mIntentFavorite;
    private SearchView mSearchVSearchField;
    private ArrayList<ProductModel> mProductModels;
    private ArrayList<StoreModel> mStoreModels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup Home Stores and Products Recycler View
        SetupModel setupModel = new SetupModel();
        mProductModels = setupModel.setupProductModel();
        mStoreModels = setupModel.setupStoreModel();

        RecyclerView recyclerVRecommendedProducts = mBinding.recyclerVRecommendedProducts;
        ProductRecyclerViewAdapter productRVAdapter = new ProductRecyclerViewAdapter(requireContext(), mProductModels);
        recyclerVRecommendedProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerVRecommendedProducts.setAdapter(productRVAdapter);

        RecyclerView recyclerVNearbyStore = mBinding.recyclerVNearbyStores;
        StoreRecyclerViewAdapter storeRVAdapter = new StoreRecyclerViewAdapter(requireContext(), mStoreModels);
        recyclerVNearbyStore.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerVNearbyStore.setAdapter(storeRVAdapter);

        // Initialize Search Bar & Buttons Views
        mIntentSearch = new Intent(getActivity(), SearchActivity.class);
        mIntentNearby = new Intent(getActivity(), NearbyActivity.class);
        mIntentDeal = new Intent(getActivity(), DealActivity.class);
        mIntentMap = new Intent(getActivity(), MapsActivity.class);
        mIntentFavorite = new Intent(getActivity(), FavoriteActivity.class);
        mSearchVSearchField = mBinding.searchVHomeSearchField;

        // Handle Search Bar & Buttons Event
        mBinding.btnSearchCategoryFilter.setOnClickListener(v -> homeSearchBarOnClick(false));

        mSearchVSearchField.setOnClickListener(v -> homeSearchBarOnClick(true));
        mSearchVSearchField.setOnSearchClickListener(v -> homeSearchBarOnClick(true));

        mBinding.btnNearby.setOnClickListener(v -> startActivity(mIntentNearby));
        mBinding.btnDeal.setOnClickListener(v -> startActivity(mIntentDeal));
        mBinding.btnMap.setOnClickListener(v -> startActivity(mIntentMap));
        mBinding.btnFavorite.setOnClickListener(v -> startActivity(mIntentFavorite));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void homeSearchBarOnClick(boolean isTyping) {
        // Could be improve using ViewModel I guess
        mIntentSearch.putExtra("isTyping", isTyping);
        startActivity(mIntentSearch);
        mSearchVSearchField.setIconified(true);
    }
}

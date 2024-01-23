package com.lspuspcc.tinda.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
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
    private ArrayList<ProductModel> mProductModels;
    private ArrayList<StoreModel> mStoreModels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

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

        // Setup Search Bar & Buttons Event
        Intent intentSearch = new Intent(getActivity(), SearchActivity.class);
        Intent intentNearby = new Intent(getActivity(), NearbyActivity.class);
        Intent intentDeal = new Intent(getActivity(), DealActivity.class);
        Intent intentMap = new Intent(getActivity(), MapsActivity.class);
        Intent intentFavorite = new Intent(getActivity(), FavoriteActivity.class);

        SearchView searchVSearchField = mBinding.searchVHomeSearchField;
        Button btnSearchCategory = mBinding.btnSearchCategory;
        Button btnNearby = mBinding.btnNearby;
        Button btnDeal = mBinding.btnDeal;
        Button btnMap = mBinding.btnMap;
        Button btnFavorite = mBinding.btnFavorite;

        btnSearchCategory.setOnClickListener(view -> homeSearchBarOnClick(intentSearch, searchVSearchField, false));
        searchVSearchField.setOnClickListener(view -> homeSearchBarOnClick(intentSearch, searchVSearchField, true));
        searchVSearchField.setOnSearchClickListener(view -> homeSearchBarOnClick(intentSearch, searchVSearchField, true));
        btnNearby.setOnClickListener(view -> startActivity(intentNearby));
        btnDeal.setOnClickListener(view -> startActivity(intentDeal));
        btnMap.setOnClickListener(view -> startActivity(intentMap));
        btnFavorite.setOnClickListener(view -> startActivity(intentFavorite));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void homeSearchBarOnClick(Intent intentSearch, SearchView searchVSearchField, boolean isTyping) {
        // Could be improve using ViewModel I guess
        intentSearch.putExtra("isTyping", isTyping);
        startActivity(intentSearch);
        searchVSearchField.setIconified(true);
    }
}

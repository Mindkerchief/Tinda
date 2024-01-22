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

import com.lspuspcc.tinda.DealActivity;
import com.lspuspcc.tinda.FavoriteActivity;
import com.lspuspcc.tinda.MapsActivity;
import com.lspuspcc.tinda.NearbyActivity;
import com.lspuspcc.tinda.ProductModel;
import com.lspuspcc.tinda.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.SearchActivity;
import com.lspuspcc.tinda.StoreModel;
import com.lspuspcc.tinda.StoreRecyclerViewAdapter;
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
        mProductModels = new ArrayList<>();
        mStoreModels = new ArrayList<>();
        setupProductModel();
        setupStoreModel();

        RecyclerView recyclerVRecommendedProducts = mBinding.recyclerVRecommendedProducts;
        ProductRecyclerViewAdapter productRVAdapter = new ProductRecyclerViewAdapter(requireContext(), mProductModels);
        recyclerVRecommendedProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerVRecommendedProducts.setAdapter(productRVAdapter);

        RecyclerView recyclerVNearbyStore = mBinding.recyclerVNearbyStore;
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

        SearchView searchVSearchField = root.findViewById(R.id.searchVHomeSearchField);
        Button btnSearchCategory = root.findViewById(R.id.btnSearchCategory);
        Button btnNearby = root.findViewById(R.id.btnNearby);
        Button btnDeal = root.findViewById(R.id.btnDeal);
        Button btnMap = root.findViewById(R.id.btnMap);
        Button btnFavorite = root.findViewById(R.id.btnFavorite);

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

    private void setupProductModel() {
        // Temporary implementation for testing purposes
        int productImage = R.drawable.product_sample_image;
        String productName = getResources().getString(R.string.label_product_name);
        String productStore = getResources().getString(R.string.label_product_store);
        String productPrice = getResources().getString(R.string.label_product_price);

        for (int i = 0; i < 10; i++) {
            mProductModels.add(new ProductModel(productImage, productName, productStore, productPrice));
        }
    }

    private void setupStoreModel() {
        // Temporary implementation for testing purposes
        int storeImage = R.drawable.store_sample_image;
        String storeName = getResources().getString(R.string.label_product_store);
        String storeAddress = getResources().getString(R.string.label_store_address);
        String storeCategory = getResources().getString(R.string.label_store_category);

        for (int i = 0; i < 10; i++) {
            mStoreModels.add(new StoreModel(storeImage, storeName, storeAddress, storeCategory));
        }
    }

    private void homeSearchBarOnClick(Intent intentSearch, SearchView searchVSearchField, boolean isTyping) {
        // Could be improve using ViewModel I guess
        intentSearch.putExtra("isTyping", isTyping);
        startActivity(intentSearch);
        searchVSearchField.setIconified(true);
    }
}

package com.lspuspcc.tinda.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lspuspcc.tinda.DealActivity;
import com.lspuspcc.tinda.FavoriteActivity;
import com.lspuspcc.tinda.MapsActivity;
import com.lspuspcc.tinda.NearbyActivity;
import com.lspuspcc.tinda.ProductModel;
import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.SearchActivity;
import com.lspuspcc.tinda.StoreModel;
import com.lspuspcc.tinda.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<ProductModel> productModels;
    private ArrayList<StoreModel> storeModels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        productModels = new ArrayList<>();
        storeModels = new ArrayList<>();
        setupProductModel();
        setupStoreModel();

        Intent intentSearch = new Intent(getActivity(), SearchActivity.class);
        Intent intentNearby = new Intent(getActivity(), NearbyActivity.class);
        Intent intentDeal = new Intent(getActivity(), DealActivity.class);
        Intent intentMap = new Intent(getActivity(), MapsActivity.class);
        Intent intentFavorite = new Intent(getActivity(), FavoriteActivity.class);

        EditText eTextSearch = root.findViewById(R.id.eTextSearch);
        Button btnCategory = root.findViewById(R.id.btnCategory);
        Button btnNearby = root.findViewById(R.id.btnNearby);
        Button btnDeal = root.findViewById(R.id.btnDeal);
        Button btnMap = root.findViewById(R.id.btnMap);
        Button btnFavorite = root.findViewById(R.id.btnFavorite);

        btnCategory.setOnClickListener(view -> startActivity(intentSearch));
        btnNearby.setOnClickListener(view -> startActivity(intentNearby));
        btnDeal.setOnClickListener(view -> startActivity(intentDeal));
        btnMap.setOnClickListener(view -> startActivity(intentMap));
        btnFavorite.setOnClickListener(view -> startActivity(intentFavorite));

        eTextSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startActivity(intentSearch);
                    return true;
                }
                return false;
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupProductModel() {
        int productImage = R.drawable.ic_launcher_background;
        String productName = getResources().getString(R.string.label_product_name);
        String productStore = getResources().getString(R.string.label_product_store);
        String productPrice = getResources().getString(R.string.label_product_price);

        for (int i = 0; i <= 10; i++) {
            productModels.add(new ProductModel(productImage, productName, productStore, productPrice));
        }
    }

    private void setupStoreModel() {
        int productImage = R.drawable.ic_launcher_background;
        String storeName = getResources().getString(R.string.label_product_store);
        String storeAddress = getResources().getString(R.string.label_store_address);
        String storeCategory = getResources().getString(R.string.label_store_category);

        for (int i = 0; i <= 10; i++) {
            productModels.add(new ProductModel(productImage, storeName, storeAddress, storeCategory));
        }
    }
}
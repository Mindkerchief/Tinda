package com.lspuspcc.tinda.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SearchView;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.domain.CategoryModel;
import com.lspuspcc.tinda.domain.CategoryRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;
    private ArrayList<CategoryModel> mCategoryModels;
    private ArrayList<ProductModel> mProductModels;
    private ArrayList<StoreModel> mStoreModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        boolean isTyping = getIntent().getBooleanExtra("isTyping", false);
        SearchView searchVSearchField = findViewById(R.id.searchV_searchField);

        if (isTyping) searchVSearchField.setIconified(false);

        SetupModel setupModel = new SetupModel();
        mProductModels = setupModel.setupProductModel();
        mCategoryModels = setupModel.setupSubCategoryModel(0);

        RecyclerView recyclerVSearchResults = mBinding.recyclerVSearchResults;
        ProductRecyclerViewAdapter productRVAdapter = new ProductRecyclerViewAdapter(this, mProductModels);
        recyclerVSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerVSearchResults.setAdapter(productRVAdapter);

        RecyclerView recyclerVSubCategory = mBinding.recyclerVSubCategory;
        CategoryRecyclerViewAdapter categoryRVAdapter = new CategoryRecyclerViewAdapter(this, mCategoryModels);
        recyclerVSubCategory.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerVSubCategory.setAdapter(categoryRVAdapter);

        searchVSearchField.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                searchVSearchField.setIconified(false);
            return false;
        });
    }
}

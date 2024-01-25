package com.lspuspcc.tinda.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.domain.SearchCategoryModel;
import com.lspuspcc.tinda.domain.SearchCategoryRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SubCategoryModel;
import com.lspuspcc.tinda.domain.SubCategoryRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;
    private ArrayList<SearchCategoryModel> mSearchCategoryModels;
    private ArrayList<SubCategoryModel> mSubCategoryModels;
    private ArrayList<ProductModel> mProductModels;
    private ArrayList<StoreModel> mStoreModels;
    private SetupModel mSetupModel;
    private ConstraintLayout mConstraintLCategory;
    private SearchCategoryRecyclerViewAdapter mSearchCategoryRVAdapter;
    private SubCategoryRecyclerViewAdapter mSubCategoryRVAdapter;
    private byte mCurrentCategoryIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Initialize most of the Views
        mConstraintLCategory = mBinding.constraintLSearchCategory;
        mSetupModel = new SetupModel();

        SearchView searchVSearchField = mBinding.searchVSearchField;
        RecyclerView recyclerVSearchCategory = mBinding.recyclerVSearchCategory;
        RecyclerView recyclerVSearchResults = mBinding.recyclerVSearchResults;
        RecyclerView recyclerVSubCategory = mBinding.recyclerVSubCategory;
        Button btnSearchCategoryFilter = mBinding.btnSearchCategoryFilter;

        // Takes Home Fragment Search Bar Action using Extra
        if (getIntent().getBooleanExtra("isTyping", false))
            searchVSearchField.setIconified(false);
        else
            btnSearchCategoryFilter.performClick();

        // Enables layout transition
        mConstraintLCategory.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // Initialize Search Bar Category Recycler View
        mSearchCategoryModels = mSetupModel.setupSearchCategoryModel();
        mSearchCategoryRVAdapter = new SearchCategoryRecyclerViewAdapter(this, mSearchCategoryModels);
        recyclerVSearchCategory.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerVSearchCategory.setAdapter(mSearchCategoryRVAdapter);

        // Initialize Search Bar Subcategory Recycler View
        mSubCategoryModels = mSetupModel.setupSubCategoryModel(0);
        mSubCategoryRVAdapter = new SubCategoryRecyclerViewAdapter(this, mSubCategoryModels);
        recyclerVSubCategory.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerVSubCategory.setAdapter(mSubCategoryRVAdapter);

        // Initialize Search Results Recycler View
        mProductModels = mSetupModel.setupProductModel();
        ProductRecyclerViewAdapter productRVAdapter = new ProductRecyclerViewAdapter(this, mProductModels);
        recyclerVSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerVSearchResults.setAdapter(productRVAdapter);

        // Handle Button Event
        searchVSearchField.setOnClickListener(v -> searchVSearchField.setIconified(false));
    }

    public void expandCollapseConstraintLCategory(View view) {
        int viewVisibility = (mConstraintLCategory.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(mConstraintLCategory, new AutoTransition());
        mConstraintLCategory.setVisibility(viewVisibility);
        mCurrentCategoryIndex = 0;
    }

    public void updateRecyclerVSubCategory(View view) {
        byte index = (byte) view.getTag();

        // Use condition to prevent spam
        if (mCurrentCategoryIndex != index) {
            mSubCategoryRVAdapter.notifyItemRangeRemoved(0, mSubCategoryModels.size());
            mSubCategoryModels = mSetupModel.setupSubCategoryModel(index);
            mSubCategoryRVAdapter.updateCategoryModel(mSubCategoryModels);
            mSubCategoryRVAdapter.notifyItemRangeInserted(0, mSubCategoryModels.size());
            mCurrentCategoryIndex = index;
        }
    }
}

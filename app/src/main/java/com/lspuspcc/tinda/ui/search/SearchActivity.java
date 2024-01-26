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
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.domain.SearchCategoryModel;
import com.lspuspcc.tinda.domain.SearchCategoryRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SubCategoryModel;
import com.lspuspcc.tinda.domain.SubCategoryRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;
    private ConstraintLayout mConstraintLCategory;
    private ConstraintLayout mConstraintLSearchResults;
    private SearchCategoryRecyclerViewAdapter mSearchCategoryRVAdapter;
    private SubCategoryRecyclerViewAdapter mSubCategoryRVAdapter;
    private ProductRecyclerViewAdapter mProductRVAdapter;
    private ArrayList<SearchCategoryModel> mSearchCategoryModels;
    private ArrayList<SubCategoryModel> mSubCategoryModels;
    private ArrayList<ProductModel> mProductResults;
    private ArrayList<StoreModel> mStoreResults;
    private SetupModel mSetupModel;
    private byte mCurrentCategoryIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Initialize most of the Views
        mConstraintLSearchResults = mBinding.constraintLSearchResults;
        mConstraintLCategory = mBinding.constraintLSearchCategory;
        mSetupModel = new SetupModel();

        SearchView searchVSearchField = mBinding.searchVSearchField;
        RecyclerView recyclerVSearchCategory = mBinding.recyclerVSearchCategory;
        RecyclerView recyclerVStoreResults = mBinding.recyclerVStoreResults;
        RecyclerView recyclerVProductResults = mBinding.recyclerVProductResults;
        RecyclerView recyclerVSubCategory = mBinding.recyclerVSubCategory;
        Button btnSearchCategoryFilter = mBinding.btnSearchCategoryFilter;

        // Takes Home Fragment Search Bar Action using Extra
        if (getIntent().getBooleanExtra("isTyping", false))
            searchVSearchField.setIconified(false);
        else
            btnSearchCategoryFilter.performClick();

        // Enables layout transition
        // mConstraintLCategory.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

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

        // Initialize Store Search Results Recycler View
        mStoreResults = mSetupModel.setupStoreModel();
        StoreRecyclerViewAdapter storeRVAdapter = new StoreRecyclerViewAdapter(this, mStoreResults);
        recyclerVStoreResults.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerVStoreResults.setAdapter(storeRVAdapter);

        // Initialize Products Search Results Recycler View
        mProductResults = mSetupModel.setupProductModel();
        mProductRVAdapter = new ProductRecyclerViewAdapter(this, mProductResults);
        recyclerVProductResults.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerVProductResults.setAdapter(mProductRVAdapter);

        // Handle Button Event
        searchVSearchField.setOnClickListener(v -> searchVSearchField.setIconified(false));
        searchVSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateRecyclerVProductResults();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: Give search suggestions
                return false;
            }
        });
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

    private void updateRecyclerVProductResults() {
        if (mConstraintLSearchResults.getVisibility() == View.GONE)
            mConstraintLSearchResults.setVisibility(View.VISIBLE);

        TransitionManager.beginDelayedTransition(mConstraintLSearchResults, new AutoTransition());
        mProductResults = mSetupModel.setupProductModel();
        mProductRVAdapter.updateRecyclerVProducts(mProductResults);
        mProductRVAdapter.notifyDataSetChanged();
    }
}

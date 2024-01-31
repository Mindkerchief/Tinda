package com.lspuspcc.tinda.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SubCategoryModel;
import com.lspuspcc.tinda.domain.SubCategoryRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;

import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;
    private ConstraintLayout mConstraintLCategory;
    private ConstraintLayout mConstraintLSearchResults;
    private SearchView mSearchVSearchField;
    private SubCategoryRecyclerViewAdapter mSubCategoryRVAdapter;
    private StoreRecyclerViewAdapter mStoreRVAdapter;
    private ProductRecyclerViewAdapter mProductRVAdapter;
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
        mSearchVSearchField = mBinding.searchVSearchField;
        mSetupModel = new SetupModel();

        RecyclerView recyclerVStoreResults = mBinding.recyclerVStoreResults;
        RecyclerView recyclerVProductResults = mBinding.recyclerVProductResults;
        RecyclerView recyclerVSubCategory = mBinding.recyclerVSubCategory;
        TabLayout tabLSearchCategory = mBinding.tabLSearchCategory;
        Button btnSearchCategoryFilter = mBinding.btnSearchCategoryFilter;

        // Initialize and add the temporary tabs in Category TabLayout
        addCategoryTab(tabLSearchCategory);

        // Takes Home Fragment Search Bar Action using Extra
        if (getIntent().getBooleanExtra("isTyping", false))
            mSearchVSearchField.setIconified(false);
        else
            btnSearchCategoryFilter.performClick();

        // Enables layout transition
        // mConstraintLCategory.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // Initialize Search Bar Subcategory Recycler View
        mSubCategoryModels = mSetupModel.setupSubCategoryModel(0);
        mSubCategoryRVAdapter = new SubCategoryRecyclerViewAdapter(this, mSubCategoryModels);
        recyclerVSubCategory.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerVSubCategory.setAdapter(mSubCategoryRVAdapter);

        // Initialize Store Search Results Recycler View
        mStoreResults = mSetupModel.setupStoreModel(false);
        mStoreRVAdapter = new StoreRecyclerViewAdapter(this, mStoreResults, R.layout.card_store_vertical);
        recyclerVStoreResults.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerVStoreResults.setAdapter(mStoreRVAdapter);

        // Initialize Products Search Results Recycler View
        mProductResults = mSetupModel.setupProductModel();
        mProductRVAdapter = new ProductRecyclerViewAdapter(this, mProductResults);
        recyclerVProductResults.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerVProductResults.setAdapter(mProductRVAdapter);

        // Handle Views Event
        tabLSearchCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateRecyclerVSubCategory(Byte.parseByte(Objects.requireNonNull(tab.getTag()).toString()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mSearchVSearchField.setOnClickListener(v -> mSearchVSearchField.setIconified(false));
        mSearchVSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateRecyclerVProductResults(mSearchVSearchField);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: Give search suggestions
                return false;
            }
        });
    }

    private void addCategoryTab(TabLayout tabLSearchCategory) {
        String[] categoryNames = mSetupModel.getProductCategory();

        for (int i = 0; i < 10; i++) {
            TabLayout.Tab newTab = tabLSearchCategory.newTab();
            newTab.setIcon(R.drawable.ic_basket);
            newTab.setText(categoryNames[i]);
            newTab.setTag(i);
            tabLSearchCategory.addTab(newTab);
        }
    }

    public void expandCollapseConstraintLCategory(View view) {
        int viewVisibility = (mConstraintLCategory.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(mConstraintLCategory, new AutoTransition());
        mConstraintLCategory.setVisibility(viewVisibility);
        mCurrentCategoryIndex = 0;
    }

    public void updateRecyclerVSubCategory(byte index) {
        // Use condition to prevent spam
        if (mCurrentCategoryIndex != index) {
            mSubCategoryRVAdapter.notifyItemRangeRemoved(0, mSubCategoryModels.size());
            mSubCategoryModels = mSetupModel.setupSubCategoryModel(index);
            mSubCategoryRVAdapter.updateCategoryModel(mSubCategoryModels);
            mSubCategoryRVAdapter.notifyItemRangeInserted(0, mSubCategoryModels.size());
            mCurrentCategoryIndex = index;
        }
    }

    public void updateRecyclerVProductResults(View view) {
        // Show Search Result Layout
        if (mConstraintLSearchResults.getVisibility() == View.GONE)
            mConstraintLSearchResults.setVisibility(View.VISIBLE);

        // Close Category Filter after choosing sub category
        if (mConstraintLCategory.getVisibility() == View.VISIBLE)
            expandCollapseConstraintLCategory(view);

        // Update Store Results
        mStoreRVAdapter.notifyItemRangeRemoved(0, mStoreResults.size());
        mStoreResults = mSetupModel.setupStoreModel(false);
        mStoreRVAdapter.updateRecyclerVStore(mStoreResults);
        mStoreRVAdapter.notifyItemRangeInserted(0, mStoreResults.size());

        // Update Product Results
        mProductRVAdapter.notifyItemRangeRemoved(0, mProductResults.size());
        mProductResults = mSetupModel.setupProductModel();
        mProductRVAdapter.updateRecyclerVProducts(mProductResults);
        mProductRVAdapter.notifyItemRangeInserted(0, mProductResults.size());

        TransitionManager.beginDelayedTransition(mConstraintLSearchResults, new AutoTransition());
        mBinding.nestedSVSearchResults.scrollTo(0,0);
        getOnBackPressedDispatcher();
    }
}

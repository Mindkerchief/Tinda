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
    private ActivitySearchBinding mSearchBinding;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mSearchBinding.getRoot());

        // Initialize most of the Views
        mConstraintLSearchResults = mSearchBinding.constraintLSearchResults;
        mConstraintLCategory = mSearchBinding.constraintLSearchCategory;
        mSearchVSearchField = mSearchBinding.searchVSearchField;
        mSetupModel = new SetupModel();

        RecyclerView recyclerVStoreResults = mSearchBinding.recyclerVStoreResults;
        RecyclerView recyclerVProductResults = mSearchBinding.recyclerVProductResults;
        RecyclerView recyclerVSubCategory = mSearchBinding.recyclerVSubCategory;
        TabLayout tabLSearchCategory = mSearchBinding.tabLSearchCategory;
        Button btnSearchCategoryFilter = mSearchBinding.btnSearchCategoryFilter;

        // Initialize and add the temporary tabs in Category TabLayout
        addCategoryTab(tabLSearchCategory);

        // Takes Home Fragment Search Bar Action using Extra
        if (getIntent().getBooleanExtra("isTyping", false))
            mSearchVSearchField.setIconified(false);
        else
            btnSearchCategoryFilter.performClick();

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

        mSearchVSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateRecyclerVProductResults(mSearchVSearchField);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Hide category when start typing
                if (mConstraintLCategory.isShown())
                    expandCollapseCategoryExplicitly(View.GONE);

                // TODO: Give search suggestions
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchBinding = null;
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

    public void searchFieldOnClick(View view) {
        mSearchVSearchField.setIconified(false);

        // Hide category when typing
        if (mConstraintLCategory.isShown())
            expandCollapseCategoryExplicitly(View.GONE);
    }

    public void expandCollapseCategoryImplicitly(View view) {
        int viewVisibility = (mConstraintLCategory.isShown())? View.GONE : View.VISIBLE;
        expandCollapseCategoryExplicitly(viewVisibility);

        // Stop typing when category is expand
        if (viewVisibility == View.VISIBLE)
            mSearchVSearchField.clearFocus();
    }

    private void expandCollapseCategoryExplicitly(int viewVisibility) {
        TransitionManager.beginDelayedTransition(mConstraintLCategory, new AutoTransition());
        mConstraintLCategory.setVisibility(viewVisibility);
    }

    public void updateRecyclerVSubCategory(byte index) {
        // Index is store in Views Tag
        mSubCategoryRVAdapter.notifyItemRangeRemoved(0, mSubCategoryModels.size());
        mSubCategoryModels = mSetupModel.setupSubCategoryModel(index);
        mSubCategoryRVAdapter.updateCategoryModel(mSubCategoryModels);
        mSubCategoryRVAdapter.notifyItemRangeInserted(0, mSubCategoryModels.size());
    }

    public void updateRecyclerVProductResults(View view) {
        // Show Search Result Layout
        if (!mConstraintLSearchResults.isShown())
            mConstraintLSearchResults.setVisibility(View.VISIBLE);

        // Close Category Filter after choosing sub category
        expandCollapseCategoryExplicitly(View.GONE);

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

        // Scroll to the top and remove keyboard
        TransitionManager.beginDelayedTransition(mConstraintLSearchResults, new AutoTransition());
        mSearchBinding.nestedSVSearchResults.scrollTo(0,0);
        mSearchVSearchField.clearFocus();
    }
}

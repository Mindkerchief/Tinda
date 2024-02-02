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

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;
import com.lspuspcc.tinda.ui.searchbar.SearchBarCallback;
import com.lspuspcc.tinda.ui.searchbar.SearchBarViewModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchBarCallback {
    private ActivitySearchBinding mSearchBinding;
    private ConstraintLayout mConstraintLSearchResults;
    private StoreRecyclerViewAdapter mStoreRVAdapter;
    private ProductRecyclerViewAdapter mProductRVAdapter;
    private ArrayList<ProductModel> mProductResults;
    private ArrayList<StoreModel> mStoreResults;
    private SetupModel mSetupModel;
    private SearchBarViewModel mSearchBarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mSearchBinding.getRoot());

        // Initialize most of the Views
        mConstraintLSearchResults = mSearchBinding.constraintLSearchResults;
        mSearchBarViewModel = new SearchBarViewModel(this, mSearchBinding.includeLSearchBar, "search");
        mSetupModel = new SetupModel();

        RecyclerView recyclerVStoreResults = mSearchBinding.recyclerVStoreResults;
        RecyclerView recyclerVProductResults = mSearchBinding.recyclerVProductResults;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchBarViewModel = null;
        mSearchBinding = null;
    }
    @Override
    public void updateResults() {
        // Show Search Result Layout
        if (!mConstraintLSearchResults.isShown())
            mConstraintLSearchResults.setVisibility(View.VISIBLE);

        // Close Category Filter after choosing sub category
        mSearchBarViewModel.showHideCategoryExplicitly(View.GONE);

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
        mSearchBinding.includeLSearchBar.searchVSearchField.clearFocus();
    }

    public void subCategoryOnClick(View view) {
        updateResults();
    }
}

package com.lspuspcc.tinda.ui.searchbar;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.SearchResultBinding;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;

import java.util.ArrayList;

public class SearchResultViewModel implements SearchBarCallback {
    private final SearchBarViewModel mSearchBarViewModel;
    private final ConstraintLayout mConstraintLSearchResults;
    private final NestedScrollView mNestedScrollView;
    private final SetupModel mSetupModel;
    private StoreRecyclerViewAdapter mStoreRVAdapter;
    private ProductRecyclerViewAdapter mProductRVAdapter;
    private ArrayList<ProductModel> mProductResults;
    private ArrayList<StoreModel> mStoreResults;
    private final String mIncludedIn;

    public SearchResultViewModel(Context context, SearchBarViewModel searchBarViewModel,
                                 SearchResultBinding searchBarBinding, NestedScrollView nestedScrollView, String includedIn) {
        this.mSearchBarViewModel = searchBarViewModel;
        this.mNestedScrollView = nestedScrollView;
        this.mSetupModel = new SetupModel();
        this.mIncludedIn = includedIn;
        mSearchBarViewModel.setSearchBarCallback(this);


        mConstraintLSearchResults = searchBarBinding.constraintLSearchResults;

        RecyclerView recyclerVStoreResults = searchBarBinding.recyclerVStoreResults;
        RecyclerView recyclerVProductResults = searchBarBinding.recyclerVProductResults;

        // Assign appropriate result title
        switch (mIncludedIn) {
            case "home":
                searchBarBinding.textVStoreResultTitle.setText(R.string.label_nearby_store);
                searchBarBinding.textVProductResultTitle.setText(R.string.label_available_products);
                break;
            case "search":
                searchBarBinding.textVStoreResultTitle.setText(R.string.label_store_results);
                searchBarBinding.textVProductResultTitle.setText(R.string.label_product_results);
                searchBarBinding.constraintLSearchResults.setVisibility(View.GONE);
                break;
            case "nearby":
                searchBarBinding.textVStoreResultTitle.setText(R.string.label_nearby_store);
                searchBarBinding.textVProductResultTitle.setText(R.string.label_available_products);
                searchBarBinding.cardVProductResultTitle.setVisibility(View.GONE);
                searchBarBinding.recyclerVProductResults.setVisibility(View.GONE);
                break;
            case "deal":
                searchBarBinding.textVStoreResultTitle.setText(R.string.button_deal);
                searchBarBinding.textVProductResultTitle.setText(R.string.label_product_results);
                searchBarBinding.cardVProductResultTitle.setVisibility(View.GONE);
                searchBarBinding.recyclerVProductResults.setVisibility(View.GONE);
                break;
        }

        // Initialize Store Search Results Recycler View
        boolean isHomeOrSearch = mIncludedIn.equals("home") | mIncludedIn.equals("search");

        if (isHomeOrSearch) {
            mStoreResults = mSetupModel.setupStoreModel(false);
            mStoreRVAdapter = new StoreRecyclerViewAdapter(context, mStoreResults, R.layout.card_store_vertical);
            recyclerVStoreResults.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
        }
        else if (mIncludedIn.equals("nearby") | mIncludedIn.equals("deal")) {
            if (mIncludedIn.equals("nearby"))
                mStoreResults = mSetupModel.setupStoreModel(true);
            else
                mStoreResults = mSetupModel.setupStoreModel(true);

            mStoreRVAdapter = new StoreRecyclerViewAdapter(context, mStoreResults, R.layout.card_store);
            recyclerVStoreResults.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false));
        }

        recyclerVStoreResults.setAdapter(mStoreRVAdapter);

        // Initialize Products Search Results Recycler View
        if (isHomeOrSearch) {
            mProductResults = mSetupModel.setupProductModel();
            mProductRVAdapter = new ProductRecyclerViewAdapter(context, mProductResults);
            recyclerVProductResults.setLayoutManager(new GridLayoutManager(context, 2));
            recyclerVProductResults.setAdapter(mProductRVAdapter);
        }
    }

    @Override
    public void updateResults() {
        if (mIncludedIn.equals("home") | mIncludedIn.equals("search")) {
            // Show Search Result Layout
            if (!mConstraintLSearchResults.isShown())
                mConstraintLSearchResults.setVisibility(View.VISIBLE);

            // Update Product Results
            mProductRVAdapter.notifyItemRangeRemoved(0, mProductResults.size());
            mProductResults = mSetupModel.setupProductModel();
            mProductRVAdapter.updateRecyclerVProducts(mProductResults);
            mProductRVAdapter.notifyItemRangeInserted(0, mProductResults.size());
        }
        else if (mIncludedIn.equals("nearby") | mIncludedIn.equals("deal")) {
            // Update Store Results
            mStoreRVAdapter.notifyItemRangeRemoved(0, mStoreResults.size());
            mStoreResults = mSetupModel.setupStoreModel(true);
            mStoreRVAdapter.updateRecyclerVStore(mStoreResults);
            mStoreRVAdapter.notifyItemRangeInserted(0, mStoreResults.size());
        }

        // Close Category Filter after choosing sub category
        mSearchBarViewModel.showHideCategoryExplicitly(View.GONE);

        // Scroll to the top and remove keyboard
        TransitionManager.beginDelayedTransition(mConstraintLSearchResults, new AutoTransition());
        mNestedScrollView.scrollTo(0,0);
        mSearchBarViewModel.getSearchVSearchField().clearFocus();
    }
}

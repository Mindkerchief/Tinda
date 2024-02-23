package com.lspuspcc.tinda.ui.searchbar;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.SearchResultBinding;
import com.lspuspcc.tinda.domain.DealRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.StoreVerticalRecyclerViewAdapter;

import java.util.ArrayList;

public class SearchResultViewModel implements SearchBarCallback {
    private final SearchBarViewModel mSearchBarViewModel;
    private final ConstraintLayout mConstraintLSearchResults;
    private final NestedScrollView mNestedScrollView;
    private final SetupModel mSetupModel;
    private StoreVerticalRecyclerViewAdapter mStoreVerticalRVAdapter;
    private StoreRecyclerViewAdapter mStoreRVAdapter;
    private ProductRecyclerViewAdapter mProductRVAdapter;
    private DealRecyclerViewAdapter mDealRVAdapter;
    private final String mIncludedIn;

    public SearchResultViewModel(Context context, SearchBarViewModel searchBarViewModel, SearchResultBinding searchBarBinding,
                                 NestedScrollView nestedScrollView, String includedIn) {
        this.mSearchBarViewModel = searchBarViewModel;
        this.mNestedScrollView = nestedScrollView;
        this.mSetupModel = new SetupModel();
        this.mIncludedIn = includedIn;

        this.mSearchBarViewModel.setSearchBarCallback(this);
        this.mConstraintLSearchResults = searchBarBinding.constraintLSearchResults;

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
        if (mIncludedIn.equals("home") | mIncludedIn.equals("search")) {
            mStoreVerticalRVAdapter = new StoreVerticalRecyclerViewAdapter();
            recyclerVStoreResults.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
            recyclerVStoreResults.setAdapter(mStoreVerticalRVAdapter);

            mProductRVAdapter = new ProductRecyclerViewAdapter();
            recyclerVProductResults.setAdapter(mProductRVAdapter);

            if (mIncludedIn.equals("home")) {
                mStoreVerticalRVAdapter.updateRecyclerVStore(mSetupModel.setupStoreVerticalModel());
                mProductRVAdapter.updateRecyclerVProducts(mSetupModel.setupProductModel());
            }
        }
        else if (mIncludedIn.equals("nearby")) {
            mStoreRVAdapter = new StoreRecyclerViewAdapter();
            recyclerVStoreResults.setAdapter(mStoreRVAdapter);
            mStoreRVAdapter.updateRecyclerVStore(mSetupModel.setupStoreModel());
        }
        else if (mIncludedIn.equals("deal")) {
            mDealRVAdapter = new DealRecyclerViewAdapter();
            recyclerVStoreResults.setAdapter(mDealRVAdapter);
            mDealRVAdapter.updateDealModel(mSetupModel.setupDealModel());
        }
    }

    @Override
    public void updateResults() {
        if (mIncludedIn.equals("home") | mIncludedIn.equals("search")) {
            // Show Search Result Layout
            if (!mConstraintLSearchResults.isShown())
                mConstraintLSearchResults.setVisibility(View.VISIBLE);

            mStoreVerticalRVAdapter.updateRecyclerVStore(mSetupModel.setupStoreVerticalModel());
            mProductRVAdapter.updateRecyclerVProducts(mSetupModel.setupProductModel());
        }
        else if (mIncludedIn.equals("nearby"))
            mStoreRVAdapter.updateRecyclerVStore(mSetupModel.setupStoreModel());

        else if (mIncludedIn.equals("deal"))
            mDealRVAdapter.updateDealModel(mSetupModel.setupDealModel());

        // Close Category Filter after choosing sub category
        mSearchBarViewModel.showHideCategoryExplicitly(View.GONE);

        // Scroll to the top and remove keyboard
        TransitionManager.beginDelayedTransition(mConstraintLSearchResults, new AutoTransition());
        mNestedScrollView.scrollTo(0,0);
        mSearchBarViewModel.getSearchVSearchField().clearFocus();
    }
}

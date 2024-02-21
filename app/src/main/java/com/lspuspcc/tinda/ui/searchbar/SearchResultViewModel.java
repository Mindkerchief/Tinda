package com.lspuspcc.tinda.ui.searchbar;

import android.content.Context;
import android.os.Handler;
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
import com.lspuspcc.tinda.domain.DealModel;
import com.lspuspcc.tinda.domain.DealRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.StoreVerticalModel;
import com.lspuspcc.tinda.domain.StoreVerticalRecyclerViewAdapter;

import java.util.ArrayList;

public class SearchResultViewModel implements SearchBarCallback {
    private final SearchBarViewModel mSearchBarViewModel;
    private final ConstraintLayout mConstraintLSearchResults;
    private final Handler mStoreHandler;
    private final Handler mProductHandler;
    private final NestedScrollView mNestedScrollView;
    private final SetupModel mSetupModel;
    private StoreVerticalRecyclerViewAdapter mStoreVerticalRVAdapter;
    private StoreRecyclerViewAdapter mStoreRVAdapter;
    private ProductRecyclerViewAdapter mProductRVAdapter;
    private DealRecyclerViewAdapter mDealRVAdapter;
    private ArrayList<ProductModel> mProductResults;
    private ArrayList<StoreVerticalModel> mStoreVerticalResults;
    private ArrayList<StoreModel> mStoreResults;
    private ArrayList<DealModel> mDealResults;
    private final String mIncludedIn;

    public SearchResultViewModel(Context context, SearchBarViewModel searchBarViewModel, SearchResultBinding searchBarBinding,
                                 NestedScrollView nestedScrollView, String includedIn) {
        this.mSearchBarViewModel = searchBarViewModel;
        this.mStoreHandler = new Handler();
        this.mProductHandler = new Handler();
        this.mNestedScrollView = nestedScrollView;
        this.mSetupModel = new SetupModel();
        this.mIncludedIn = includedIn;

        this.mSearchBarViewModel.setSearchBarCallback(this);
        this.mConstraintLSearchResults = searchBarBinding.constraintLSearchResults;

        SearchResultsRunnable searchResultsRunnable = new SearchResultsRunnable(context,
                searchBarBinding.recyclerVStoreResults, searchBarBinding.recyclerVProductResults);
        new Thread(searchResultsRunnable).start();

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
    }

    @Override
    public void updateResults() {

        if (mIncludedIn.equals("home") | mIncludedIn.equals("search")) {
            // Show Search Result Layout
            if (!mConstraintLSearchResults.isShown())
                mConstraintLSearchResults.setVisibility(View.VISIBLE);

            mStoreHandler.post(() -> {
                // Update Store Results
                mStoreVerticalResults = mSetupModel.setupStoreVerticalModel();
                mStoreVerticalRVAdapter.updateRecyclerVStore(mStoreVerticalResults);
            });

            mProductHandler.post(() -> {
                // Update Product Results
                mProductResults = mSetupModel.setupProductModel();
                mProductRVAdapter.updateRecyclerVProducts(mProductResults);
            });
        }
        else if (mIncludedIn.equals("nearby")) {
            mStoreHandler.post(() -> {
                // Update Store Results
                mStoreResults = mSetupModel.setupStoreModel();
                mStoreRVAdapter.updateRecyclerVStore(mStoreResults);
            });
        }
        else if (mIncludedIn.equals("deal")) {
            mStoreHandler.post(() -> {
                // Update Deal Results
                mDealResults = mSetupModel.setupDealModel();
                mDealRVAdapter.updateDealModel(mDealResults);
            });
        }

        // Close Category Filter after choosing sub category
        mSearchBarViewModel.showHideCategoryExplicitly(View.GONE);

        // Scroll to the top and remove keyboard
        TransitionManager.beginDelayedTransition(mConstraintLSearchResults, new AutoTransition());
        mNestedScrollView.scrollTo(0,0);
        mSearchBarViewModel.getSearchVSearchField().clearFocus();
    }

    class SearchResultsRunnable implements Runnable {
        private final Context mContext;
        private final RecyclerView mRecyclerVStoreResults;
        private final RecyclerView mRecyclerVProductResults;

        public SearchResultsRunnable(Context context, RecyclerView recyclerVStoreResults, RecyclerView recyclerVProductResults) {
            this.mContext = context;
            this.mRecyclerVStoreResults = recyclerVStoreResults;
            this.mRecyclerVProductResults = recyclerVProductResults;
        }

        @Override
        public void run() {
            // Initialize Store Search Results Recycler View
            boolean isHomeOrSearch = mIncludedIn.equals("home") | mIncludedIn.equals("search");

            if (isHomeOrSearch) {
                mStoreHandler.post(() -> {
                    mStoreVerticalResults = mSetupModel.setupStoreVerticalModel();
                    mStoreVerticalRVAdapter = new StoreVerticalRecyclerViewAdapter(mStoreVerticalResults);
                    mRecyclerVStoreResults.setLayoutManager(new LinearLayoutManager(mContext,
                            LinearLayoutManager.HORIZONTAL, false));
                    mRecyclerVStoreResults.setAdapter(mStoreVerticalRVAdapter);
                });
            }
            else if (mIncludedIn.equals("nearby")) {
                mStoreHandler.post(() -> {
                    mStoreResults = mSetupModel.setupStoreModel();
                    mStoreRVAdapter = new StoreRecyclerViewAdapter(mStoreResults);
                    mRecyclerVStoreResults.setLayoutManager(new LinearLayoutManager(mContext,
                            LinearLayoutManager.VERTICAL, false));
                    mRecyclerVStoreResults.setAdapter(mStoreRVAdapter);
                });
            }
            else if (mIncludedIn.equals("deal")) {
                mStoreHandler.post(() -> {
                    mDealResults = mSetupModel.setupDealModel();
                    mDealRVAdapter = new DealRecyclerViewAdapter(mDealResults);
                    mRecyclerVStoreResults.setLayoutManager(new LinearLayoutManager(mContext,
                            LinearLayoutManager.VERTICAL, false));
                    mRecyclerVStoreResults.setAdapter(mDealRVAdapter);
                });
            }

            // Initialize Products Search Results Recycler View
            if (isHomeOrSearch) {
                mProductHandler.post(() -> {
                    mProductRVAdapter = new ProductRecyclerViewAdapter(new ArrayList<>());
                    mRecyclerVProductResults.setLayoutManager(new GridLayoutManager(mContext, 2));
                    mRecyclerVProductResults.setAdapter(mProductRVAdapter);
                    mProductResults = mSetupModel.setupProductModel();

                    for (int i = 0; i < mProductResults.size(); i++) {
                        int finalI = i;
                        mRecyclerVProductResults.post(() -> {
                            ProductModel productModel = mProductResults.get(finalI);
                            mProductRVAdapter.mProductModels.add(productModel);
                            mProductRVAdapter.notifyItemInserted(finalI);
                        });
                    }
                });
            }
        }
    }
}

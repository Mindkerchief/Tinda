package com.lspuspcc.tinda.ui.searchbar;

import android.content.res.ColorStateList;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.SearchBarBinding;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.SubCategoryModel;
import com.lspuspcc.tinda.domain.SubCategoryRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class SearchBarViewModel {
    private SearchBarCallback mSearchBarCallback;
    private ConstraintLayout mConstraintLCategory;
    private SearchView mSearchVSearchField;
    private SetupModel mSetupModel;
    private SubCategoryRecyclerViewAdapter mSubCategoryRVAdapter;
    private ArrayList<SubCategoryModel> mSubCategoryModels;
    private String mIncludedIn;
    public static Boolean sShowCategory;

    public SearchBarViewModel() {
        // Empty constructor for displaying just the Search Bar
    }

    public SearchBarViewModel(SearchBarBinding searchBarBinding, String includedIn) {
        this.mSetupModel = new SetupModel();
        this.mIncludedIn = includedIn;
        this.mConstraintLCategory = searchBarBinding.constraintLSearchCategory;
        this.mSearchVSearchField = searchBarBinding.searchVSearchField;

        SearchBarRunnable searchBarRunnable = new SearchBarRunnable(searchBarBinding.recyclerVSubCategoryList,
                searchBarBinding.tabLSearchCategoryList, searchBarBinding.btnSearchCategory);
        new Thread(searchBarRunnable).start();
    }

    private void setupSearchBarHint() {
        mSearchVSearchField.post(() -> {
            switch (mIncludedIn) {
                case "search":
                    mSearchVSearchField.setQueryHint("Search local products");
                    break;
                case "nearby":
                    mSearchVSearchField.setQueryHint("Search nearby stores");
                    break;
                case "deal":
                    mSearchVSearchField.setQueryHint("Search easy deals");
                    break;
            }
        });
    }

    private void addCategoryTab(TabLayout tabLSearchCategory) {
        tabLSearchCategory.post(() -> {
            String[] categoryNames = new String[10];

            switch (mIncludedIn) {
                case "search":
                    categoryNames = mSetupModel.getProductCategory();
                    break;
                case "nearby":
                    categoryNames = mSetupModel.getStoreCategory();
                    break;
                case "deal":
                    categoryNames = mSetupModel.getDealCategory();
                    break;
            }

            for (int i = 0; i < categoryNames.length; i++) {
                TabLayout.Tab newTab = tabLSearchCategory.newTab();
                newTab.setCustomView(R.layout.search_custom_tab);
                View customTab = newTab.getCustomView();

                assert customTab != null;
                customTab.findViewById(R.id.imageV_searchCategory).setBackgroundResource(R.drawable.ic_basket);
                TextView textVSearchCategory = customTab.findViewById(R.id.textV_searchCategory);
                textVSearchCategory.setText(categoryNames[i]);

                // Highlight the first tab
                if (i == 0) {
                    changeTabColor(Objects.requireNonNull(newTab.getCustomView()).findViewById(
                            R.id.imageV_searchCategory), -15222408);
                }

                newTab.setTag(i);
                tabLSearchCategory.addTab(newTab);
            }
        });
    }

    private void changeTabColor(ImageView tabIcon, int newColor) {
        tabIcon.setBackgroundTintList(ColorStateList.valueOf(newColor));
    }

    public void showHideCategoryExplicitly(int viewVisibility) {
        TransitionManager.beginDelayedTransition(mConstraintLCategory, new AutoTransition());
        mConstraintLCategory.setVisibility(viewVisibility);
    }

    private void searchFieldOnClick() {
        mSearchVSearchField.setIconified(false);

        // Hide category when typing
        if (mConstraintLCategory.isShown())
            showHideCategoryExplicitly(View.GONE);
    }

    private void searchCategoryOnClick() {
        int viewVisibility = (mConstraintLCategory.isShown()) ? View.GONE : View.VISIBLE;
        showHideCategoryExplicitly(viewVisibility);

        // Stop typing when category is expand
        if (viewVisibility == View.VISIBLE)
            mSearchVSearchField.clearFocus();
    }

    public SearchView getSearchVSearchField() {
        return mSearchVSearchField;
    }

    public void setSearchBarCallback(SearchBarCallback searchBarCallback) {
        this.mSearchBarCallback = searchBarCallback;
    }

    class SearchBarRunnable implements Runnable {
        private final RecyclerView mRecyclerVSubCategory;
        private final TabLayout mTabLSearchCategory;
        private final Button mBtnSearchCategoryFilter;

        public SearchBarRunnable(RecyclerView recyclerVSubCategory, TabLayout tabLSearchCategory,
                                 Button btnSearchCategoryFilter) {
            mRecyclerVSubCategory = recyclerVSubCategory;
            mTabLSearchCategory = tabLSearchCategory;
            mBtnSearchCategoryFilter = btnSearchCategoryFilter;
        }

        @Override
        public void run() {
            // Initialize and add the Tabs in Category TabLayout
            setupSearchBarHint();
            addCategoryTab(mTabLSearchCategory);

            mBtnSearchCategoryFilter.setOnClickListener(v -> searchCategoryOnClick());

            // Initialize Search Bar Subcategory Recycler View
            if (mIncludedIn.equals("search")) {
                mSubCategoryModels = mSetupModel.setupSubCategoryModel(0);
                mSubCategoryRVAdapter = new SubCategoryRecyclerViewAdapter(mSubCategoryModels);

                mRecyclerVSubCategory.post(() -> {
                    mRecyclerVSubCategory.setLayoutManager(new GridLayoutManager(
                            mConstraintLCategory.getContext(), 4));
                    mRecyclerVSubCategory.setAdapter(mSubCategoryRVAdapter);
                    mRecyclerVSubCategory.setVisibility(View.VISIBLE);

                    if (sShowCategory) {
                        mSearchVSearchField.setIconified(false);
                        sShowCategory = false;
                    }
                    else mBtnSearchCategoryFilter.performClick();
                });
            }

            // Handle Views Event
            mSearchVSearchField.setOnClickListener(v -> searchFieldOnClick());
            mSearchVSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchBarCallback.updateResults();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Hide category when start typing
                    if (mConstraintLCategory.isShown())
                        showHideCategoryExplicitly(View.GONE);

                    // TODO: Give search suggestions
                    return false;
                }
            });

            mTabLSearchCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    changeTabColor(Objects.requireNonNull(tab.getCustomView()).findViewById(
                            R.id.imageV_searchCategory), -15222408);

                    switch (mIncludedIn) {
                        case "search":
                            // Update SubCategory
                            mSubCategoryModels = mSetupModel.setupSubCategoryModel(
                                    Byte.parseByte(Objects.requireNonNull(tab.getTag()).toString()));
                            mSubCategoryRVAdapter.updateCategoryModel(mSubCategoryModels);
                            break;
                        case "nearby":
                        case "deal":
                            mSearchBarCallback.updateResults();
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    changeTabColor(Objects.requireNonNull(tab.getCustomView()).findViewById(
                            R.id.imageV_searchCategory), -10066330);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            });
        }
    }
}

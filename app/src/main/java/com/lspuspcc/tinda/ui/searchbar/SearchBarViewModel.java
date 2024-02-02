package com.lspuspcc.tinda.ui.searchbar;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.CardSearchBarBinding;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.SubCategoryModel;
import com.lspuspcc.tinda.domain.SubCategoryRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class SearchBarViewModel {
    private final ConstraintLayout mConstraintLCategory;
    private final SearchView mSearchVSearchField;
    private final SetupModel mSetupModel;
    private final SubCategoryRecyclerViewAdapter mSubCategoryRVAdapter;
    private ArrayList<SubCategoryModel> mSubCategoryModels;
    private final String mIncludedIn;
    public static Boolean sShowCategory;

    public SearchBarViewModel(Context context, CardSearchBarBinding searchBar, String includedIn) {
        SearchBarCallback searchBarCallback = (SearchBarCallback) context;
        this.mSetupModel = new SetupModel();
        this.mIncludedIn = includedIn;

        mConstraintLCategory = searchBar.constraintLSearchCategory;
        mSearchVSearchField = searchBar.searchVSearchField;

        RecyclerView recyclerVSubCategory = searchBar.recyclerVSubCategory;
        TabLayout tabLSearchCategory = searchBar.tabLSearchCategory;
        Button btnSearchCategoryFilter = searchBar.btnSearchCategoryFilter;

        // Initialize and add the temporary tabs in Category TabLayout
        addCategoryTab(tabLSearchCategory);

        // Initialize Search Bar Subcategory Recycler View
        mSubCategoryModels = mSetupModel.setupSubCategoryModel(0);
        mSubCategoryRVAdapter = new SubCategoryRecyclerViewAdapter(context, mSubCategoryModels);
        recyclerVSubCategory.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerVSubCategory.setAdapter(mSubCategoryRVAdapter);

        if (mIncludedIn.equals("search"))
            recyclerVSubCategory.setVisibility(View.VISIBLE);

        if (sShowCategory) {
            mSearchVSearchField.setIconified(false);
            sShowCategory = false;
        }
        else btnSearchCategoryFilter.performClick();

        // Handle Views Event
        btnSearchCategoryFilter.setOnClickListener(v -> searchCategoryOnClick());
        mSearchVSearchField.setOnClickListener(v -> searchFieldOnClick());
        mSearchVSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBarCallback.updateResults();
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

        tabLSearchCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (mIncludedIn) {
                    case "search":
                        updateSubCategory(Byte.parseByte(Objects.requireNonNull(tab.getTag()).toString()));
                        break;
                    case "nearby":
                        searchBarCallback.updateResults();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void addCategoryTab(TabLayout tabLSearchCategory) {
        String[] categoryNames = new String[10];

        switch (mIncludedIn) {
            case "search":
                categoryNames = mSetupModel.getProductCategory();
                break;
            case "nearby":
                categoryNames = mSetupModel.getStoreCategory();
                break;
        }

        if (categoryNames != null)
            for (int i = 0; i < 10; i++) {
                TabLayout.Tab newTab = tabLSearchCategory.newTab();
                newTab.setIcon(R.drawable.ic_basket);
                newTab.setText(categoryNames[i]);
                newTab.setTag(i);
                tabLSearchCategory.addTab(newTab);
        }
    }

    private void updateSubCategory(byte index) {
        // Index is store in Views Tag
        mSubCategoryRVAdapter.notifyItemRangeRemoved(0, mSubCategoryModels.size());
        mSubCategoryModels = mSetupModel.setupSubCategoryModel(index);
        mSubCategoryRVAdapter.updateCategoryModel(mSubCategoryModels);
        mSubCategoryRVAdapter.notifyItemRangeInserted(0, mSubCategoryModels.size());
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
}

package com.mindkerchief.tinda.ui.searchbar;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.mindkerchief.tinda.R;
import com.mindkerchief.tinda.databinding.SearchBarBinding;
import com.mindkerchief.tinda.domain.SetupModel;
import com.mindkerchief.tinda.domain.SubCategoryRecyclerViewAdapter;

import java.util.Objects;

public class SearchBarViewModel {
    private SearchBarCallback mSearchBarCallback;
    private ConstraintLayout mConstraintLCategory;
    private SearchView mSearchVSearchField;
    private SetupModel mSetupModel;
    private SubCategoryRecyclerViewAdapter mSubCategoryRVAdapter;
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

        TabLayout tabLSearchCategory = searchBarBinding.tabLSearchCategoryList;
        RecyclerView recyclerVSubCategory = searchBarBinding.recyclerVSubCategoryList;
        Button btnSearchCategoryFilter = searchBarBinding.btnSearchCategory;

        // Initialize and add the Tabs in Category TabLayout
        setupSearchBarHint();
        addCategoryTab(tabLSearchCategory);

        btnSearchCategoryFilter.setOnClickListener(v -> searchCategoryOnClick());

        // Initialize Search Bar Subcategory Recycler View
        if (mIncludedIn.equals("search")) {
            if (sShowCategory) {
                mSearchVSearchField.setIconified(false);
                sShowCategory = false;
            }
            else btnSearchCategoryFilter.performClick();

            mSubCategoryRVAdapter = new SubCategoryRecyclerViewAdapter();
            recyclerVSubCategory.setAdapter(mSubCategoryRVAdapter);
            recyclerVSubCategory.setVisibility(View.VISIBLE);
            mSubCategoryRVAdapter.updateSubCategoryModel(mSetupModel.setupSubCategoryModel(0));
        }

        // Handle Views Event
        mSearchVSearchField.setOnClickListener(v -> searchFieldOnClick());
        mSearchVSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchBarCallback.updateResults(query);
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
                changeTabColor(Objects.requireNonNull(tab.getCustomView()).findViewById(
                        R.id.imageV_searchCategory), -15222408);

                switch (mIncludedIn) {
                    case "search":
                        // Update SubCategory
                        mSubCategoryRVAdapter.updateSubCategoryModel(mSetupModel.setupSubCategoryModel(
                                Byte.parseByte(Objects.requireNonNull(tab.getTag()).toString())));
                        break;
                    case "nearby":
                    case "deal":
                        TextView textVSearchCategory = tab.getCustomView().findViewById(R.id.textV_searchCategory);
                        mSearchBarCallback.updateResults(textVSearchCategory.getText());
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

    private void setupSearchBarHint() {
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
    }

    private void addCategoryTab(TabLayout tabLSearchCategory) {
        String[] categoryNames;

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
            default:
                categoryNames = new String[1];
        }

        for (int i = 0; i < categoryNames.length; i++) {
            TabLayout.Tab newTab = tabLSearchCategory.newTab();
            newTab.setCustomView(R.layout.search_custom_tab);
            View customTab = newTab.getCustomView();

            assert customTab != null;
            customTab.findViewById(R.id.imageV_searchCategory).setBackgroundResource(R.drawable.ic_basket);
            TextView textVSearchCategory = customTab.findViewById(R.id.textV_searchCategory);
            textVSearchCategory.setText(categoryNames[i]);

            if (i == 0) {
                changeTabColor(Objects.requireNonNull(newTab.getCustomView()).findViewById(
                        R.id.imageV_searchCategory), -15222408);
            }

            newTab.setTag(i);
            tabLSearchCategory.addTab(newTab);
        }
    }

    private void changeTabColor(ImageView tabIcon, int newColor) {
        tabIcon.setBackgroundTintList(ColorStateList.valueOf(newColor));
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

    public void showHideCategoryExplicitly(int viewVisibility) {
        mConstraintLCategory.setVisibility(viewVisibility);
    }

    public SearchView getSearchVSearchField() {
        return mSearchVSearchField;
    }

    public void setSearchBarCallback(SearchBarCallback searchBarCallback) {
        this.mSearchBarCallback = searchBarCallback;
    }
}

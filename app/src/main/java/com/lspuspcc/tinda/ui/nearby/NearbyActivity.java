package com.lspuspcc.tinda.ui.nearby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.lspuspcc.tinda.databinding.ActivityNearbyBinding;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;
import com.lspuspcc.tinda.domain.StoreRecyclerViewAdapter;

import java.util.ArrayList;

public class NearbyActivity extends AppCompatActivity {
    private ActivityNearbyBinding mNearbyBinding;
    private ConstraintLayout mConstraintLCategory;
    private SearchView mSearchVSearchField;
    private StoreRecyclerViewAdapter mStoreRVAdapter;
    private ArrayList<StoreModel> mStoreResults;
    private SetupModel mSetupModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNearbyBinding = ActivityNearbyBinding.inflate(getLayoutInflater());
        setContentView(mNearbyBinding.getRoot());

        // Initialize most of the Views
        mConstraintLCategory = mNearbyBinding.includeLSearchBar.constraintLSearchCategory;
        mSearchVSearchField = mNearbyBinding.includeLSearchBar.searchVSearchField;
        mSetupModel = new SetupModel();

        RecyclerView recyclerVStoreResults = mNearbyBinding.recyclerVStoreResults;
        TabLayout tabLStoreCategory = mNearbyBinding.includeLSearchBar.tabLSearchCategory;
        Button btnSearchCategoryFilter = mNearbyBinding.includeLSearchBar.btnSearchCategoryFilter;

        // Initialize Store Search Results Recycler View
        mStoreResults = mSetupModel.setupStoreModel(true);
        mStoreRVAdapter = new StoreRecyclerViewAdapter(this, mStoreResults, R.layout.card_store);
        recyclerVStoreResults.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerVStoreResults.setAdapter(mStoreRVAdapter);

        // Initialize and add the temporary tabs in Category TabLayout
        addCategoryTab(tabLStoreCategory);

        // Immediately display the Nearby Stores
        updateRecyclerVStoreResults(btnSearchCategoryFilter);

        // Handle Views Event
        tabLStoreCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateRecyclerVStoreResults(btnSearchCategoryFilter);
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
                updateRecyclerVStoreResults(mSearchVSearchField);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
        mNearbyBinding = null;
    }

    private void addCategoryTab(TabLayout tabLSearchCategory) {
        String[] categoryNames = mSetupModel.getStoreCategory();

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

    public void updateRecyclerVStoreResults(View view) {
        // Close Category Filter after choosing sub category
        expandCollapseCategoryExplicitly(View.GONE);

        // Update Store Results
        mStoreRVAdapter.notifyItemRangeRemoved(0, mStoreResults.size());
        mStoreResults = mSetupModel.setupStoreModel(true);
        mStoreRVAdapter.updateRecyclerVStore(mStoreResults);
        mStoreRVAdapter.notifyItemRangeInserted(0, mStoreResults.size());

        // Scroll to the top and remove keyboard
        TransitionManager.beginDelayedTransition(mNearbyBinding.constraintLSearchResults, new AutoTransition());
        mNearbyBinding.nestedSVSearchResults.scrollTo(0,0);
        mSearchVSearchField.clearFocus();
    }
}

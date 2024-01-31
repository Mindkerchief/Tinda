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
    private ActivityNearbyBinding mBinding;
    private ConstraintLayout mConstraintLCategory;
    private ConstraintLayout mConstraintLSearchResults;
    private SearchView mSearchVSearchField;
    private StoreRecyclerViewAdapter mStoreRVAdapter;
    private ArrayList<StoreModel> mStoreResults;
    private SetupModel mSetupModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNearbyBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Initialize most of the Views
        mConstraintLSearchResults = mBinding.constraintLSearchResults;
        mConstraintLCategory = mBinding.constraintLStoreCategory;
        mSearchVSearchField = mBinding.searchVSearchField;
        mSetupModel = new SetupModel();

        RecyclerView recyclerVStoreResults = mBinding.recyclerVStoreResults;
        TabLayout tabLStoreCategory = mBinding.tabLStoreCategory;
        Button btnSearchCategoryFilter = mBinding.btnSearchCategoryFilter;

        // Initialize Store Search Results Recycler View
        mStoreResults = mSetupModel.setupStoreModel(true);
        mStoreRVAdapter = new StoreRecyclerViewAdapter(this, mStoreResults, R.layout.card_store);
        recyclerVStoreResults.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerVStoreResults.setAdapter(mStoreRVAdapter);

        // Enables layout transition
        // mConstraintLCategory.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

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

        mSearchVSearchField.setOnClickListener(v -> mSearchVSearchField.setIconified(false));
        mSearchVSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateRecyclerVStoreResults(mSearchVSearchField);
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
        String[] categoryNames = mSetupModel.getStoreCategory();

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
    }

    public void updateRecyclerVStoreResults(View view) {
        // Close Category Filter after choosing category
        if (mConstraintLCategory.getVisibility() == View.VISIBLE)
            expandCollapseConstraintLCategory(view);

        // Update Store Results
        mStoreRVAdapter.notifyItemRangeRemoved(0, mStoreResults.size());
        mStoreResults = mSetupModel.setupStoreModel(true);
        mStoreRVAdapter.updateRecyclerVStore(mStoreResults);
        mStoreRVAdapter.notifyItemRangeInserted(0, mStoreResults.size());

        TransitionManager.beginDelayedTransition(mConstraintLSearchResults, new AutoTransition());
        mBinding.nestedSVSearchResults.scrollTo(0,0);
        getOnBackPressedDispatcher();
    }
}
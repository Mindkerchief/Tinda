package com.lspuspcc.tinda.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.lspuspcc.tinda.databinding.ActivitySearchBinding;
import com.lspuspcc.tinda.domain.CategoryModel;
import com.lspuspcc.tinda.domain.CategoryRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.ProductModel;
import com.lspuspcc.tinda.domain.ProductRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;
import com.lspuspcc.tinda.domain.StoreModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;
    private ArrayList<CategoryModel> mCategoryModels;
    private ArrayList<ProductModel> mProductModels;
    private ArrayList<StoreModel> mStoreModels;
    private SetupModel mSetupModel;
    private ConstraintLayout mConstraintLCategory;
    private CategoryRecyclerViewAdapter mCategoryRVAdapter;
    private byte mCurrentCategoryIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Initialize most of the Views
        mConstraintLCategory = mBinding.constraintLCategory;
        mSetupModel = new SetupModel();

        SearchView searchVSearchField = mBinding.searchVSearchField;
        RecyclerView recyclerVSearchResults = mBinding.recyclerVSearchResults;
        RecyclerView mRecyclerVSubCategory = mBinding.recyclerVSubCategory;
        Button btnSearchCategory = mBinding.btnSearchCategory;
        Button btnElectronicsCategory = mBinding.btnElectronicsCategory;
        Button btnAppliancesCategory = mBinding.btnAppliancesCategory;
        Button btnBeautyCategory = mBinding.btnBeautyCategory;
        Button btnToysCategory = mBinding.btnToysCategory;
        Button btnGroceryCategory = mBinding.btnGroceryCategory;
        Button btnFurnitureCategory = mBinding.btnFurnitureCategory;
        Button btnClothingCategory = mBinding.btnClothingCategory;
        Button btnFootwearCategory = mBinding.btnFootwearCategory;
        Button btnSportsCategory = mBinding.btnSportsCategory;
        Button btnHardwareCategory = mBinding.btnHardwareCategory;

        // Takes Home Fragment Search Bar Action using Extra
        if (getIntent().getBooleanExtra("isTyping", false))
            searchVSearchField.setIconified(false);
        else
            btnSearchCategory.performClick();

        // Prevents Category Model from having NullException
        mCategoryModels = mSetupModel.setupSubCategoryModel(0);

        // Enables layout transition
        mConstraintLCategory.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // Initialize Search Bar Subcategory Recycler View
        mCategoryRVAdapter = new CategoryRecyclerViewAdapter(this, mCategoryModels);
        mRecyclerVSubCategory.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerVSubCategory.setAdapter(mCategoryRVAdapter);

        // Initialize Search Results Recycler View
        mProductModels = mSetupModel.setupProductModel();
        ProductRecyclerViewAdapter productRVAdapter = new ProductRecyclerViewAdapter(this, mProductModels);
        recyclerVSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerVSearchResults.setAdapter(productRVAdapter);

        // Handles Buttons OnCLick Event
        btnElectronicsCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 0));
        btnAppliancesCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 1));
        btnBeautyCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 2));
        btnToysCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 3));
        btnGroceryCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 4));
        btnFurnitureCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 5));
        btnClothingCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 6));
        btnFootwearCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 7));
        btnSportsCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 8));
        btnHardwareCategory.setOnClickListener(view -> updateRecyclerVSubCategory((byte) 9));

        // TODO: Improve this Search Field OnTouch Event
        searchVSearchField.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                searchVSearchField.setIconified(false);
            return false;
        });
    }

    public void expandCollapseConstraintLCategory(View view) {
        int viewVisibility = (mConstraintLCategory.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(mConstraintLCategory, new AutoTransition());
        mConstraintLCategory.setVisibility(viewVisibility);
    }

    private void updateRecyclerVSubCategory(byte index) {
        // Use condition to prevent spam
        if (mCurrentCategoryIndex != index) {
            mCategoryRVAdapter.notifyItemRangeRemoved(0, mCategoryModels.size());
            mCategoryModels = mSetupModel.setupSubCategoryModel(index);
            mCategoryRVAdapter.updateCategoryModel(mCategoryModels);
            mCategoryRVAdapter.notifyItemRangeInserted(0, mCategoryModels.size());
            mCurrentCategoryIndex = index;
        }
    }
}

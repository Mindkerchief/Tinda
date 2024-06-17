package com.lspuspcc.tinda.ui.basket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lspuspcc.tinda.databinding.FragmentBasketBinding;
import com.lspuspcc.tinda.domain.BasketModel;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Objects;

public class BasketFragment extends Fragment {
    private BasketViewModel mBasketViewModel;
    private FragmentBasketBinding mBasketBinding;
    private TextView mTextVItemCount, mTextVSubTotalAmount;
    private MutableLiveData<HashSet<BasketModel>> mSelectedItems;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBasketViewModel = new ViewModelProvider(this).get(BasketViewModel.class);
        mBasketBinding = FragmentBasketBinding.inflate(inflater, container, false);
        return mBasketBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLBasketSections = mBasketBinding.tabLBasketSections;

        mTextVItemCount = mBasketBinding.textVItemCount;
        mTextVSubTotalAmount = mBasketBinding.textVSubTotalAmount;
        mSelectedItems = mBasketViewModel.getSelectedItems();

        // 1
        BasketSectionAdapter basketSectionAdapter = new BasketSectionAdapter(this, mSelectedItems);
        ViewPager2 viewPagerBasketSections = mBasketBinding.viewPagerBasketSections;
        viewPagerBasketSections.setAdapter(basketSectionAdapter);
        new TabLayoutMediator(tabLBasketSections, viewPagerBasketSections,
                ((tab, position) -> tab.setText("OBJECT" + (position + 1)))).attach();

        // Observe whenever selected items changes
        mSelectedItems.observe(getViewLifecycleOwner(), basketModel -> setCountAndSubTotal());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBasketBinding = null;
    }

    public void setCountAndSubTotal() {
        // Recompute Subtotal and count every changes using observer
        double newSubTotalAmount = 0d;
        int newItemCount = 0;

        for (BasketModel basketModel : Objects.requireNonNull(mSelectedItems.getValue())) {
            newSubTotalAmount += basketModel.getProductTotalPrice();
            newItemCount += Objects.requireNonNull(basketModel.getProductCount().getValue());
        }

        String newSubTotal = new DecimalFormat("₱###,###,###,##0.00").format(newSubTotalAmount);
        mTextVItemCount.setText(String.valueOf(newItemCount));
        mTextVSubTotalAmount.setText(newSubTotal);
    }
}

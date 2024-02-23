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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lspuspcc.tinda.databinding.FragmentBasketBinding;
import com.lspuspcc.tinda.domain.BasketModel;
import com.lspuspcc.tinda.domain.BasketRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class BasketFragment extends Fragment {
    private FragmentBasketBinding mBasketBinding;
    private TextView mTextVItemCount, mTextVSubTotalAmount;
    private MutableLiveData<HashSet<BasketModel>> mSelectedItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BasketViewModel basketViewModel = new ViewModelProvider(this).get(BasketViewModel.class);
        mBasketBinding = FragmentBasketBinding.inflate(inflater, container, false);
        return mBasketBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = mBasketBinding.tabLBasketSection;
        mTextVItemCount = mBasketBinding.textVItemCount;
        mTextVSubTotalAmount = mBasketBinding.textVSubTotalAmount;

        ArrayList<BasketModel> basketModels = new SetupModel().setupBasketModel();
        mSelectedItems = new MutableLiveData<>(new HashSet<>());
        BasketRecyclerViewAdapter basketRVAdapter = new BasketRecyclerViewAdapter(this,
                basketModels, mSelectedItems);
        RecyclerView recyclerVBasketItems = mBasketBinding.recyclerVBasketItems;
        recyclerVBasketItems.setAdapter(basketRVAdapter);

        mSelectedItems.observe(getViewLifecycleOwner(), basketModel -> setCountAndSubTotal());
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

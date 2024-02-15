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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class BasketFragment extends Fragment {
    private FragmentBasketBinding mBasketBinding;
    private TextView mTextVItemCount, mTextVSubTotalAmount;
    private BasketRecyclerViewAdapter mBasketRVAdapter;
    private ArrayList<BasketModel> mBasketModels;
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

        mBasketModels = new SetupModel().setupBasketModel();
        mSelectedItems = new MutableLiveData<>(new HashSet<>());
        mBasketRVAdapter = new BasketRecyclerViewAdapter(this, mBasketModels, mSelectedItems);
        RecyclerView recyclerVBasketItems = mBasketBinding.recyclerVBasketItems;
        recyclerVBasketItems.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerVBasketItems.setAdapter(mBasketRVAdapter);

        mSelectedItems.observe(getViewLifecycleOwner(), basketModels -> setCountAndSubTotal());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBasketBinding = null;
    }

    public void setCountAndSubTotal() {
        double newSubTotalAmount = 0d;
        int newItemCount = 0;

        for (BasketModel basketModel : mSelectedItems.getValue()) {
            newSubTotalAmount += basketModel.getProductTotalPrice();
            newItemCount += basketModel.getProductCount().getValue();
        }

        String newSubTotal = String.format(Locale.ENGLISH, "₱%.2f", newSubTotalAmount);
        mTextVItemCount.setText(String.valueOf(newItemCount));
        mTextVSubTotalAmount.setText(newSubTotal);
    }
}

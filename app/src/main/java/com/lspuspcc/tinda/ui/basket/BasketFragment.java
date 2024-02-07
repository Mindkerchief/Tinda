package com.lspuspcc.tinda.ui.basket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lspuspcc.tinda.databinding.FragmentBasketBinding;
import com.lspuspcc.tinda.domain.BasketModel;
import com.lspuspcc.tinda.domain.BasketRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;

import java.util.ArrayList;

public class BasketFragment extends Fragment implements BasketCallBack {
    private FragmentBasketBinding mBasketBinding;
    private TextView mTextVItemCount, mTextVSubTotalAmount;
    private BasketRecyclerViewAdapter mBasketRVAdapter;
    private ArrayList<BasketModel> mBasketModels;

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
        RecyclerView recyclerVBasketItems = mBasketBinding.recyclerVBasketItems;
        mTextVItemCount = mBasketBinding.textVItemCount;
        mTextVSubTotalAmount = mBasketBinding.textVSubTotalAmount;
        SetupModel setupModel = new SetupModel();

        mBasketModels = setupModel.setupBasketModel();
        mBasketRVAdapter = new BasketRecyclerViewAdapter(this, mBasketModels);
        recyclerVBasketItems.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerVBasketItems.setAdapter(mBasketRVAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBasketBinding = null;
    }

    @Override
    public void setCountAndSubTotal(boolean isChecked, double itemPrice, short itemCount) {
        // Make price and count negative to subtract instead if item is unchecked
        if (!isChecked) {
            itemPrice *= -1;
            itemCount *= -1;
        }

        // Set price and count new value
        String newSubTotalAmount = "₱" + (Double.parseDouble(mTextVSubTotalAmount.getText()
                .toString().substring(1)) + itemPrice);
        mTextVItemCount.setText(String.valueOf(Integer.parseInt(mTextVItemCount.getText().toString()) + itemCount));
        mTextVSubTotalAmount.setText(newSubTotalAmount);
    }
}

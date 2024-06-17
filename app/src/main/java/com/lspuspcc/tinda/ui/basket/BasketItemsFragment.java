package com.lspuspcc.tinda.ui.basket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.domain.BasketModel;
import com.lspuspcc.tinda.domain.BasketRecyclerViewAdapter;
import com.lspuspcc.tinda.domain.SetupModel;

import java.util.ArrayList;
import java.util.HashSet;

public class BasketItemsFragment extends Fragment {
    private RecyclerView mRecyclerVBasketItems;
    private MutableLiveData<HashSet<BasketModel>> mSelectedItems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket_items, container, false);
        mRecyclerVBasketItems = view.findViewById(R.id.recyclerV_basketItems);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<BasketModel> basketModels = new SetupModel().setupBasketModel();
        // 4
        BasketRecyclerViewAdapter basketRVAdapter = new BasketRecyclerViewAdapter(this.getViewLifecycleOwner(),
                basketModels, mSelectedItems);
        mRecyclerVBasketItems.setAdapter(basketRVAdapter);
    }

    public void setSelectedItems(MutableLiveData<HashSet<BasketModel>> selectedItems) {
        this.mSelectedItems = selectedItems;
    }
}
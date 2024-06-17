package com.lspuspcc.tinda.ui.basket;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lspuspcc.tinda.domain.BasketModel;

import java.util.HashSet;

public class BasketViewModel extends ViewModel {

    private BasketSectionAdapter mBasketSectionAdapter;
    private MutableLiveData<HashSet<BasketModel>> mSelectedItems;

    public BasketViewModel() {
        mSelectedItems = new MutableLiveData<>(new HashSet<>());
    }

    public BasketSectionAdapter getBasketSectionAdapter() {
        return mBasketSectionAdapter;
    }

    public MutableLiveData<HashSet<BasketModel>> getSelectedItems() {
        return mSelectedItems;
    }

    public void setBasketSectionAdapter(BasketSectionAdapter basketSectionAdapter) {
        this.mBasketSectionAdapter = basketSectionAdapter;
    }
}
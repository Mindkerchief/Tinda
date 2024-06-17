package com.mindkerchief.tinda.ui.basket;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mindkerchief.tinda.domain.BasketModel;

import java.util.HashSet;

public class BasketSectionAdapter extends FragmentStateAdapter {
    private final BasketItemsFragment basketItemsFragment;

    public BasketSectionAdapter(@NonNull Fragment fragment, MutableLiveData<HashSet<BasketModel>> selectedItems) {
        super(fragment);
        // 2
        basketItemsFragment = new BasketItemsFragment();
        basketItemsFragment.setSelectedItems(selectedItems);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 3
        if (position == 0) {
            return basketItemsFragment;
        }
        else
            return new FavoritesFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

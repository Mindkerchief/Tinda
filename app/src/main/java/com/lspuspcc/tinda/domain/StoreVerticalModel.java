package com.lspuspcc.tinda.domain;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class StoreVerticalModel {
    private final int mStoreImage;
    private final String mStoreName;
    private final String mStoreAddress;
    private final String mStoreCategory;

    public StoreVerticalModel(int storeImage, String storeName, String storeAddress, String storeCategory) {
        this.mStoreImage = storeImage;
        this.mStoreName = storeName;
        this.mStoreAddress = storeAddress;
        this.mStoreCategory = storeCategory;
    }

    public int getStoreImage() {
        return mStoreImage;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public String getStoreAddress() {
        return mStoreAddress;
    }

    public String getStoreCategory() {
        return mStoreCategory;
    }

    @BindingAdapter("android:loadStoreVerticalImage")
    public static void loadImage(ImageView imageVStoreVerticalImage, int storeVerticalImage) {
        Glide.with(imageVStoreVerticalImage).load(storeVerticalImage).into(imageVStoreVerticalImage);
    }
}

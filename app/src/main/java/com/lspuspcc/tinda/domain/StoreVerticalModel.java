package com.lspuspcc.tinda.domain;

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
}

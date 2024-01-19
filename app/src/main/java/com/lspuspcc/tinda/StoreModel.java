package com.lspuspcc.tinda;

public class StoreModel {
    private int mStoreImage;
    private String mStoreName;
    private String mStoreAddress;
    private String mStoreCategory;

    public StoreModel(int storeImage, String storeName, String storeAddress, String storeCategory) {
        this.mStoreImage = storeImage;
        this.mStoreName = storeName;
        this.mStoreAddress = storeAddress;
        this.mStoreCategory = storeCategory;
    }

    /**
     * Temporary implementation of model for testing purposes
     */

    public int getmStoreImage() {
        return mStoreImage;
    }

    public String getmStoreName() {
        return mStoreName;
    }

    public String getmStoreAddress() {
        return mStoreAddress;
    }

    public String getmStoreCategory() {
        return mStoreCategory;
    }
}

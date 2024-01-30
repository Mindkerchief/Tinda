package com.lspuspcc.tinda.domain;

public class StoreModel {
    private int mStoreImage;
    private String mStoreName;
    private String mStoreAddress;
    private String mStoreCategory;
    private int[] mStoreFeatureImages;
    private float[] mStoreFeaturePrices;

    public StoreModel(int storeImage, String storeName, String storeAddress, String storeCategory) {
        this.mStoreImage = storeImage;
        this.mStoreName = storeName;
        this.mStoreAddress = storeAddress;
        this.mStoreCategory = storeCategory;
    }

    public StoreModel(int storeImage, String storeName, String storeAddress, String storeCategory,
                      int[] storeFeatureImages, float[] storeFeaturePrices) {
        this.mStoreImage = storeImage;
        this.mStoreName = storeName;
        this.mStoreAddress = storeAddress;
        this.mStoreCategory = storeCategory;
        this.mStoreFeatureImages = storeFeatureImages;
        this.mStoreFeaturePrices = storeFeaturePrices;
    }

    /**
     * Temporary implementation of model for testing purposes
     */

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

    public int[] getStoreFeatureImages() {
        return mStoreFeatureImages;
    }

    public float[] getStoreFeaturePrices() {
        return mStoreFeaturePrices;
    }
}

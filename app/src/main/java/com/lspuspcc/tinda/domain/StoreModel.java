package com.lspuspcc.tinda.domain;

import java.text.DecimalFormat;

public class StoreModel extends StoreVerticalModel {
    private final int[] mStoreFeatureImages;
    private final float[] mStoreFeaturePrices;
    private final DecimalFormat mPeroCurrencyFormat;

    public StoreModel(int storeImage, String storeName, String storeAddress, String storeCategory,
                      int[] storeFeatureImages, float[] storeFeaturePrices) {
        super(storeImage, storeName, storeAddress, storeCategory);
        this.mStoreFeatureImages = storeFeatureImages;
        this.mStoreFeaturePrices = storeFeaturePrices;
        this.mPeroCurrencyFormat = new DecimalFormat("₱###,###,###,##0.00");
    }

    public int getStoreFeatureImage1() {
        return mStoreFeatureImages[0];
    }

    public String getStoreFeatureFormattedPrice1() {
        return mPeroCurrencyFormat.format(mStoreFeaturePrices[0]);
    }

    public int getStoreFeatureImage2() {
        return mStoreFeatureImages[1];
    }

    public String getStoreFeatureFormattedPrice2() {
        return mPeroCurrencyFormat.format(mStoreFeaturePrices[1]);
    }

    public int getStoreFeatureImage3() {
        return mStoreFeatureImages[2];
    }

    public String getStoreFeatureFormattedPrice3() {
        return mPeroCurrencyFormat.format(mStoreFeaturePrices[2]);
    }

    public int getStoreFeatureImage4() {
        return mStoreFeatureImages[3];
    }

    public String getStoreFeatureFormattedPrice4() {
        return mPeroCurrencyFormat.format(mStoreFeaturePrices[3]);
    }

    public int getStoreFeatureImage5() {
        return mStoreFeatureImages[4];
    }

    public String getStoreFeatureFormattedPrice5() {
        return mPeroCurrencyFormat.format(mStoreFeaturePrices[4]);
    }
}

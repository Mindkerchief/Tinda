package com.mindkerchief.tinda.domain;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.Objects;

public class BasketModel {
    private boolean mIsProductSelected;
    private final int mProductImage;
    private final String mProductName;
    private final String mProductStore;
    private final Float mProductPrice;
    private final MutableLiveData<Byte> mProductCount;
    private double mProductTotalPrice;

    public BasketModel(boolean isProductSelected, int productImage, String productName,
                       String productStore, float productPrice, byte productCount) {
        // TODO: Must be always updated from the database
        this.mIsProductSelected = isProductSelected;
        this.mProductImage = productImage;
        this.mProductName = productName;
        this.mProductStore = productStore;
        this.mProductPrice = productPrice;
        this.mProductCount = new MutableLiveData<>(productCount);
        this.mProductTotalPrice = mProductPrice * productCount;
    }

    public boolean getIsProductSelected() {
        return mIsProductSelected;
    }

    public void setIsProductSelected(boolean isProductSelected) {
        this.mIsProductSelected = isProductSelected;
    }

    public int getProductImage() {
        return mProductImage;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getProductStore() {
        return mProductStore;
    }

    public float getProductPrice() {
        return mProductPrice;
    }

    public MutableLiveData<Byte> getProductCount() {
        return mProductCount;
    }

    public double getProductTotalPrice() {
        return mProductTotalPrice;
    }

    public String getFormattedProductPrice() {
        return new DecimalFormat("₱###,###,###,##0.00").format(mProductPrice);
    }

    public void updateCount(int countToToAdd) {
        byte newProductCount = (byte) (Objects.requireNonNull(mProductCount.getValue()) + countToToAdd);

        mProductCount.postValue(newProductCount);
        mProductTotalPrice = mProductPrice * newProductCount;
    }

    @BindingAdapter("android:loadBasketImage")
    public static void loadImage(ImageView imageVProductImage, int productImage) {
        Glide.with(imageVProductImage).load(productImage).into(imageVProductImage);
    }
}

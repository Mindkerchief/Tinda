package com.lspuspcc.tinda.domain;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class SubCategoryModel {
    private final int mSubCategoryImage;
    private final String mSubCategoryName;

    public SubCategoryModel(int mSubCategoryImage, String mSubCategory) {
        this.mSubCategoryImage = mSubCategoryImage;
        this.mSubCategoryName = mSubCategory;
    }

    public int getSubCategoryImage() {
        return mSubCategoryImage;
    }

    public String getSubCategoryName() {
        return mSubCategoryName;
    }

    @BindingAdapter("android:loadSubCategoryImage")
    public static void loadImage(ImageView imageVSubCategoryImage, int subCategoryImage) {
        Glide.with(imageVSubCategoryImage).load(subCategoryImage).into(imageVSubCategoryImage);
    }
}

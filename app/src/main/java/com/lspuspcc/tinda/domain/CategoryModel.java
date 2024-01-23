package com.lspuspcc.tinda.domain;

public class CategoryModel {
    private int mCategoryImage;
    private String mCategoryName;

    public CategoryModel(int mCategoryImage, String mCategory) {
        this.mCategoryImage = mCategoryImage;
        this.mCategoryName = mCategory;
    }

    public int getmCategoryImage() {
        return mCategoryImage;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }
}

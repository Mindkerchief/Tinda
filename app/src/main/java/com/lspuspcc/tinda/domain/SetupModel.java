package com.lspuspcc.tinda.domain;

import androidx.lifecycle.MutableLiveData;

import com.lspuspcc.tinda.R;

import java.util.ArrayList;

public class SetupModel {

    public ArrayList<ProductModel> setupProductModel() {
        // Temporary implementation for testing purposes
        ArrayList<ProductModel> productModels = new ArrayList<>();

        int productImage = R.drawable.sample_product_image;
        String productName = "KARSON Scientific Calculator Multiple Modes Intuitive Interface";
        String productStore = "Expressions - Ultimart San Pablo";
        String productPrice = "₱169.00";

        for (int i = 0; i < 10; i++) {
            productModels.add(new ProductModel(productImage, productName, productStore, productPrice));
        }
        return productModels;
    }

    public ArrayList<StoreModel> setupStoreModel(boolean isFeatureIncluded) {
        // Temporary implementation for testing purposes
        ArrayList<StoreModel> storeModels = new ArrayList<>();

        int storeImage = R.drawable.sample_store_image;
        String storeName = "Expressions - Ultimart San Pablo";
        String storeAddress = "San Pablo";
        String storeCategory = "School/Office Supplies";
        int[] storeFeatureImages = new int[5];
        float[] storeFeaturePrices = new float[5];

        for (int i = 0; i < 10; i++) {
            if (isFeatureIncluded) {
                for (int j = 0; j < 5; j++) {
                    storeFeatureImages[j] = R.drawable.sample_product_image;
                    storeFeaturePrices[j] = 169.0f + j;
                }

                storeModels.add(new StoreModel(storeImage, storeName, storeAddress, storeCategory,
                        storeFeatureImages, storeFeaturePrices));
            }
            else {
                storeModels.add(new StoreModel(storeImage, storeName, storeAddress, storeCategory));
            }
        }
        return storeModels;
    }

    public ArrayList<SubCategoryModel> setupSubCategoryModel(int subCategoryIndex) {
        // Temporary implementation for testing purposes
        ArrayList<SubCategoryModel> subCategoryModels = new ArrayList<>();

        int categoryImage = R.drawable.sample_product_image;
        String[][] subCategory = {
            {"Smartphone", "Keyboard", "Earphone", "Headphone"},
            {"Television", "Speaker", "Refrigerator", "Fans", "Toaster"},
            {"Lotion", "Deodorant", "Shampoo", "Perfume", "Wax"},
            {"Car", "Gun", "Barbie", "Ball", "Card"},
            {"Rice", "Vegetable", "Fruits", "Canned Goods"},
            {"Chair", "Stand", "Closet", "Bed", "Table"},
            {"T-shirt", "Short", "Jeans", "Pants", "Polo"},
            {"Slippers", "Sandals", "Sneakers", "Black-shoes", "High-hills"},
            {"Treadmill", "Weights", "Ball"},
            {"Tool", "Parts", "Paint"}
        };

        for (int i = 0; i < subCategory[subCategoryIndex].length; i++) {
            subCategoryModels.add(new SubCategoryModel(categoryImage, subCategory[subCategoryIndex][i]));
        }
        return subCategoryModels;
    }

    public ArrayList<DealModel> setupDealModel() {
        ArrayList<DealModel> dealModels = new ArrayList<>();

        int dealImage = R.drawable.sample_product_image;
        String dealProduct = "KARSON Scientific Calculator Multiple Modes Intuitive Interface";
        String dealAddress = "San Pablo";
        float dealPrice = 169.00f;

        for (int i = 0; i < 10; i++) {
            dealPrice += 10;
            dealModels.add(new DealModel(dealImage, dealProduct, dealAddress, dealPrice));
        }
        return dealModels;
    }

    public ArrayList<BasketModel> setupBasketModel() {
        ArrayList<BasketModel> dealModels = new ArrayList<>();

        boolean isProductSelected = false;
        int productImage = R.drawable.sample_product_image;
        String productName = "KARSON Scientific Calculator Multiple Modes Intuitive Interface";
        String productStore = "Expression - San Pablo";
        float productPrice = 169.69f;

        for (int i = 0; i < 5; i++) {
            productPrice += 10;
            dealModels.add(new BasketModel(isProductSelected, productImage, productName, productStore,
                    productPrice, (byte) (i + 1)));
        }
        return dealModels;
    }

    public String[] getProductCategory() {
        return new String[] {
                "Electronics",  "Appliances",
                "Beauty",       "Toys",
                "Grocery",      "Furniture",
                "Clothing",     "Footwear",
                "Sports",       "Hardware"
        };
    }

    public String[] getStoreCategory() {
        // Stores can have multiple category
        return new String[] {
                "Office Supplies",      "Appliances",
                "Beauty",               "Entertainment",
                "Grocery",              "Furniture",
                "Fashion",              "Mixed",
                "Food",                 "Hardware"
        };
    }

    public String[] getDealCategory() {
        // Stores can have multiple category
        return new String[] {
                "Deal A",       "Deal B",
                "Deal C",       "Deal D",
                "Deal E",       "Deal F",
                "Deal G",       "Deal H",
                "Deal I",       "Deal J"
        };
    }
}

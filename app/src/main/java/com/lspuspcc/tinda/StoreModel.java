package com.lspuspcc.tinda;

public class StoreModel {
    private int storeImage;
    private String storeName;
    private String storeAddress;
    private String storeCategory;

    public StoreModel(int storeImage, String storeName, String storeAddress, String storeCategory) {
        this.storeImage = storeImage;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeCategory = storeCategory;
    }

    public int getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(int storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }
}

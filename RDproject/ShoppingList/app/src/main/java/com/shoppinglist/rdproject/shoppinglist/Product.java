package com.shoppinglist.rdproject.shoppinglist;



public class Product {

        private String name;
        private String quantity;
        private int photoId;

        public Product(String name, String quantity, int photoId) {
            this.name = name;
            this.quantity = quantity;
            this.photoId = photoId;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}

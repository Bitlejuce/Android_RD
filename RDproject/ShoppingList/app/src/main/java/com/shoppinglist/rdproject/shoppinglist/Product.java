package com.shoppinglist.rdproject.shoppinglist;



public class Product {

        private String name;
        private String quantity;
        private int status;

        public Product(String name, String quantity) {
            this.name = name;
            this.quantity = quantity;
            this.status = DBHelper.STATUS_TODO;
        }

    public Product(String name, String quantity, int status) {
        this.name = name;
        this.quantity = quantity;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

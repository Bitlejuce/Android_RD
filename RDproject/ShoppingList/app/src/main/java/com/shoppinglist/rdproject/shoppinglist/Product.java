package com.shoppinglist.rdproject.shoppinglist;



public class Product {

        public String name;
        public String quantity;
        public int status;

    public Product() {
    }

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

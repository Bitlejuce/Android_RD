package com.shoppinglist.rdproject.shoppinglist;


import java.util.ArrayList;
import java.util.List;

public class DataListHolder {

    private List<Product> shoppingList;
    private List<Product> doneList;

    public List<Product> getShoppingList() {
        initializeData();
        return shoppingList;
    }

    public List<Product> getDoneList() {
        initializeData();
        return doneList;
    }

    private void initializeData(){
        doneList = new ArrayList<>();
        shoppingList = new ArrayList<>();

        shoppingList.add(new Product("Vodka", "5"));
        shoppingList.add(new Product("Meat", "2"));
        shoppingList.add(new Product("Cheese", "0,5"));
        shoppingList.add(new Product("Salo", "2"));
        shoppingList.add(new Product("Bread", "2"));
        shoppingList.add(new Product("Onion", "3"));
        shoppingList.add(new Product("Garlic", "1"));
        shoppingList.add(new Product("Fish", "2"));
        shoppingList.add(new Product("Cola", "1,5"));

        doneList.add(new Product("Salmon", "4,5"));
    }
}

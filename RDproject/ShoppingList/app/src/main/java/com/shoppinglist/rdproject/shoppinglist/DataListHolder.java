package com.shoppinglist.rdproject.shoppinglist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataListHolder {

    private List<Product> shoppingList;
    private List<Product> doneList;
    private List<String> listTables;
    private Context context;
    private SQLiteDatabase shoppingListDB;
    private String tableName;

    public DataListHolder(Context context, String tableName) {
        this.context = context;
        this.tableName = tableName;
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.tableName = tableName;
        shoppingListDB = dbHelper.getWritableDatabase();
    }

    public long insert(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, product.getName());
        cv.put(DBHelper.COLUMN_QUANTITY, product.getQuantity());
        cv.put(DBHelper.COLUMN_STATUS, DBHelper.STATUS_TODO);
        return shoppingListDB.insert(tableName, null, cv);
    }
    public int update(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, product.getName());
        cv.put(DBHelper.COLUMN_QUANTITY, product.getQuantity());
        cv.put(DBHelper.COLUMN_STATUS, product.getStatus());
        return shoppingListDB.update(tableName, cv, DBHelper.COLUMN_NAME + " = ?", new String[] {String.valueOf(product.getName()) });
    }
    public int deleteAll() {
        return shoppingListDB.delete(tableName, null, null);
    }
     public void delete(Product product) {
        shoppingListDB.delete(tableName, DBHelper.COLUMN_NAME + " = ?", new String[] { String.valueOf(product.getName()) });
    }
    public void deleteDone() {
        for (Product p: getDoneList()) {
            shoppingListDB.delete(tableName, DBHelper.COLUMN_NAME + " = ?", new String[] { String.valueOf(p.getName()) });
        }
    }
    public ArrayList<Product> selectAll() {
        Cursor mCursor = shoppingListDB.rawQuery("SELECT * FROM " + tableName, null);
        ArrayList<Product> arr = new ArrayList<>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                String productName = mCursor.getString(1);
                String productQuantity = mCursor.getString(2);
                int status = mCursor.getInt(3);
                arr.add(new Product(productName, productQuantity, status));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }

    public List<Product> getShoppingList() {
        ArrayList<Product> all = selectAll();
        ArrayList<Product> toDoList = new ArrayList<>();
        if (all.isEmpty()) return toDoList;
        for (Product p: all) {
            if (p.getStatus() == DBHelper.STATUS_TODO){
                toDoList.add(p);
            }
        }
        return toDoList;
    }

    public List<Product> getDoneList() {
        ArrayList<Product> all = selectAll();
        ArrayList<Product> doneList = new ArrayList<>();
        if (all.isEmpty()) return doneList;
        for (Product p: all) {
            if (p.getStatus() == DBHelper.STATUS_DONE){
                doneList.add(p);
            }
        }
        return doneList;
    }
    public void close() {
        shoppingListDB.close();
    }
}

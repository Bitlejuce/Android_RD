package com.shoppinglist.rdproject.shoppinglist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shoppinglist.rdproject.shoppinglist.DBHelper.*;

public class DataListHolder {

    private Context context;
    private SQLiteDatabase shoppingListDB;
    private String tableName;
    private DBHelper dbHelper;

    public DataListHolder(Context context, String tableName) {
        this.context = context;
        this.tableName = tableName;
        dbHelper = new DBHelper(context);
        dbHelper.tableName = tableName;
        shoppingListDB = dbHelper.getWritableDatabase();
    }

    public long insert(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, product.getName());
        cv.put(DBHelper.COLUMN_QUANTITY, product.getQuantity());
        cv.put(DBHelper.COLUMN_STATUS, product.getStatus());
        return shoppingListDB.insert(tableName, null, cv);
    }

    public int update(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, product.getName());
        cv.put(DBHelper.COLUMN_QUANTITY, product.getQuantity());
        cv.put(DBHelper.COLUMN_STATUS, product.getStatus());
        return shoppingListDB.update(tableName, cv, COLUMN_NAME + " = ?", new String[] {String.valueOf(product.getName()) });
    }

    public int deleteAll() {
        return shoppingListDB.delete(tableName, null, null);
    }

     public void delete(Product product) {
        shoppingListDB.delete(tableName, COLUMN_NAME + " = ?", new String[] { String.valueOf(product.getName()) });
    }

    public void deleteTable(String table){
        shoppingListDB.execSQL("DROP TABLE IF EXISTS " + table);
    }

    public void deleteDone() {
        for (Product p: getDoneList()) {
            shoppingListDB.delete(tableName, COLUMN_NAME + " = ?", new String[] { String.valueOf(p.getName()) });
        }
    }

    public ArrayList<Product> selectAll() {
        ArrayList<Product> arr = new ArrayList<>();

            Cursor mCursor = shoppingListDB.rawQuery("SELECT * FROM " + tableName, null);
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
   /* public void renameTable(String newName){
        shoppingListDB.beginTransaction();
        try{
            shoppingListDB.execSQL("ALTER TABLE " + tableName + " RENAME TO " + newName+";");
            shoppingListDB.setTransactionSuccessful();
            tableName = newName;
        } finally{
            shoppingListDB.endTransaction();
        }
    }*/

    public void createTableIfNotExists(String tableName) {
        this.tableName = tableName;
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " TEXT, " +
                COLUMN_STATUS + " INTEGER); ";
        shoppingListDB.execSQL(query);
        //dbHelper.onUpgrade(shoppingListDB, 1, 2);
        Log.d("TEST", "createTableIfNotExists called");
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

    public List<String> getListOfLists() {
        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = shoppingListDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }
        c.close();
        arrTblNames.remove("android_metadata");
        arrTblNames.remove("sqlite_sequence");
        return arrTblNames;
    }

    public int getNextIndexOfTable(){
        int index = 0;
        List<String> arrTblNames = getListOfLists();
        for (String s: arrTblNames){
            int listIndex = Integer.parseInt(s.replace("Newlist", ""));
            index = index > listIndex ? index : listIndex;
        }
        return index+1;
    }
}

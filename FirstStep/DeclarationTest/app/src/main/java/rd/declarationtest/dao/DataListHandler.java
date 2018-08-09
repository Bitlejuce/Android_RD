package rd.declarationtest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rd.declarationtest.pojo.Item;

import static rd.declarationtest.dao.DBHelper.COLUMN_COMMENT;
import static rd.declarationtest.dao.DBHelper.COLUMN_FAVORITE;
import static rd.declarationtest.dao.DBHelper.COLUMN_LINK_PDF;
import static rd.declarationtest.dao.DBHelper.COLUMN_PERSON_FIRSTNAME;
import static rd.declarationtest.dao.DBHelper.COLUMN_PERSON_ID;
import static rd.declarationtest.dao.DBHelper.COLUMN_PERSON_LASTNAME;
import static rd.declarationtest.dao.DBHelper.COLUMN_PERSON_PLACE_OF_WORK;
import static rd.declarationtest.dao.DBHelper.COLUMN_PERSON_POSITION;

public class DataListHandler {
    private Context context;
    private SQLiteDatabase personListDB;
    private String tableName;
    private DBHelper dbHelper;

    public DataListHandler(Context context, String tableName) {
        this.context = context;
        this.tableName = tableName;
        dbHelper = new DBHelper(context);
        dbHelper.setTableName(tableName);
        personListDB = dbHelper.getWritableDatabase();
    }

    public long insert(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PERSON_ID, item.getId());
        cv.put(COLUMN_PERSON_FIRSTNAME, item.getFirstname());
        cv.put(COLUMN_PERSON_LASTNAME, item.getLastname());
        cv.put(COLUMN_PERSON_PLACE_OF_WORK, item.getPlaceOfWork());
        cv.put(COLUMN_PERSON_POSITION, item.getPosition());
        cv.put(COLUMN_LINK_PDF, item.getLinkPDF());
        cv.put(COLUMN_COMMENT, item.getComment());
        cv.put(COLUMN_FAVORITE, item.getFavorite());
        return personListDB.insert(tableName, null, cv);
    }

    public void insertAll(List<Item> list) {
        personListDB.beginTransaction();
        try {
            for (Item item: list){
                insert(item);
            }
            personListDB.setTransactionSuccessful();
        }
        finally {
            personListDB.endTransaction();
        }
    }

    public int deleteAll() {
        return personListDB.delete(tableName, null, null);
    }

    public void delete(Item item) {
        personListDB.delete(tableName, COLUMN_PERSON_ID + " = ?", new String[] { String.valueOf(item.getId()) });
    }

    public ArrayList<Item> getFavoriteList() {
        ArrayList<Item> arr = new ArrayList<>();

        Cursor mCursor = personListDB.rawQuery("SELECT * FROM " + tableName, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                Item item = new Item();

                String id = mCursor.getString(1);
                String firstname = mCursor.getString(2);
                String lastname = mCursor.getString(3);
                String placeOfWork = mCursor.getString(4);
                String position = mCursor.getString(5);
                String linkPDF = mCursor.getString(6);
                String comment = mCursor.getString(7);
                int favorite = mCursor.getInt(8);

                item.setId(id);
                item.setFirstname(firstname);
                item.setLastname(lastname);
                item.setPlaceOfWork(placeOfWork);
                item.setPosition(position);
                item.setLinkPDF(linkPDF);
                item.setComment(comment);
                item.setFavorite(favorite);
                arr.add(item);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;

    }

    public void close() {
        personListDB.close();
    }
}

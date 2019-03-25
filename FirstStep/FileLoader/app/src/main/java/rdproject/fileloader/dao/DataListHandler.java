package rdproject.fileloader.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import rdproject.fileloader.pojo.Link;
import static rdproject.fileloader.dao.DBHelper.*;


public class DataListHandler {

    private SQLiteDatabase linkListDB;
    private String tableName;
    private DBHelper dbHelper;

    public DataListHandler(Context context, String tableName) {
        this.tableName = tableName;
        dbHelper = new DBHelper(context);
        dbHelper.setTableName(tableName);
        linkListDB = dbHelper.getWritableDatabase();
    }

    public long insert(Link item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LINK, item.getLink());
        cv.put(COLUMN_DATE, item.getDateMills());
        cv.put(COLUMN_STATUS, item.getStatus());
        return linkListDB.insert(tableName, null, cv);
    }

//    public void insertAll(List<Link> list) {
//        linkListDB.beginTransaction();
//        try {
//            for (Link item: list){
//                insert(item);
//            }
//            linkListDB.setTransactionSuccessful();
//        }
//        finally {
//            linkListDB.endTransaction();
//        }
//    }

    public int deleteAll() {
        return linkListDB.delete(tableName, null, null);
    }

//    public void delete(Link item) {
//        linkListDB.delete(tableName, COLUMN_LINK + " = ?", new String[] { String.valueOf(item.getLink()) });
//    }

    public ArrayList<Link> getSavedLinks() {
        ArrayList<Link> arr = new ArrayList<>();

        Cursor mCursor = linkListDB.rawQuery("SELECT * FROM " + tableName, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                String link = mCursor.getString(1);
                long date = mCursor.getLong(2);
                int status = mCursor.getInt(3);

                Link item = new Link(link, date, status);

                arr.add(item);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }

    public void close() {
        linkListDB.close();
    }
}


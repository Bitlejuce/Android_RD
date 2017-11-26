package rdproject.a34_sqlite_start;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ro on 10.10.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VER = 1;
    public static final String DB_NAME = "contactsDB";
    public static final String TABLE_NAME = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "_name";
    public static final String KEY_EMAIL = "_email";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_EMAIL + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);

    }
}

package rdproject.fileloader.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SAVEDLINKS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LINK = "LINK";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_STATUS = "STATUS";

    private String tableName = "linksList";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LINK + " TEXT, " +
                COLUMN_DATE + " INTEGER, " +
                COLUMN_STATUS + " INTEGER) ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}


package rd.fordewindcompanytesttask.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FAVORITE PERSONS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOGIN = "LOGIN";
    public static final String COLUMN_USER_ID = "USERID";
    public static final String COLUMN_AVATAR_URL = "AVATARURL";
    public static final String COLUMN_FOLLOWERS_URL = "FOLLOWERSURL";
    public static final String COLUMN_HTML_URL = "HTMLURL";
    public static final String COLUMN_COMMENT = "COMMENT";
    public static final String COLUMN_FAVORITE = "FAVORITE";


    private String tableName;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LOGIN + " TEXT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_AVATAR_URL + " TEXT, " +
                COLUMN_FOLLOWERS_URL + " TEXT, " +
                COLUMN_HTML_URL + " TEXT, " +
                COLUMN_COMMENT + " TEXT, " +
                COLUMN_FAVORITE + " INTEGER); ";

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


package rd.declarationtest.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FAVORITE PERSONS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERSON_ID = "PERSONID";
    public static final String COLUMN_PERSON_FIRSTNAME = "FIRSTNAME";
    public static final String COLUMN_PERSON_LASTNAME = "LASTNAME";
    public static final String COLUMN_PERSON_PLACE_OF_WORK = "PLACEOFWORK";
    public static final String COLUMN_PERSON_POSITION = "POSITION";
    public static final String COLUMN_LINK_PDF = "LINKPDF";
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
                COLUMN_PERSON_ID + " TEXT, " +
                COLUMN_PERSON_FIRSTNAME + " TEXT, " +
                COLUMN_PERSON_LASTNAME + " TEXT, " +
                COLUMN_PERSON_PLACE_OF_WORK + " TEXT, " +
                COLUMN_PERSON_POSITION + " TEXT, " +
                COLUMN_LINK_PDF + " TEXT, " +
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


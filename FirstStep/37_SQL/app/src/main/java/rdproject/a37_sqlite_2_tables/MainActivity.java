package rdproject.a37_sqlite_2_tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "MyLog";

    //int [] positionId = {1, 2, 3, 4, 5};
    String[] positionName = {"Maintenance", "Nurse", "Doctor", "Lawyer", "Manager"};
    int [] positionSalary = {8000, 12000, 30000, 35000, 70000};

    String[] peopleName = {"John", "Barbara", "Jackson", "Robby", "Kevin", "Roman", "Katelyn"};
    int[] peoplePosition = {1, 4, 2, 1, 2, 4, 5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper helper = new DbHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor;

        Log.d(LOG_TAG, "                         Таблица должностей");
        cursor = database.query("Positions", null, null, null, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "------------------------------------------------");

        Log.d(LOG_TAG, "                          Таблица работников");
        cursor = database.query("People", null, null, null, null, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "-------------------------------------------------");

        Log.d(LOG_TAG, "                                 Объединенная таблица роукверри");
        String selectQuerry = "SELECT People.id, People.PeopleName, Positions.PositionName\n" +
                "                FROM People \n" +
                "                INNER JOIN Positions ON\n" +
                "                People.PeoplePosition = Positions.id";
        cursor = database.rawQuery(selectQuerry, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--------------------------------------------------");

        helper.close();
    }

    void logCursor(Cursor cursor) {
        if (cursor == null) return;

        if (cursor.moveToFirst()) {
            do {
                String string = "";
                for (String s : cursor.getColumnNames()){
                    String tmp = s + " = " + cursor.getString(cursor.getColumnIndex(s))+ "                ";
                    string += tmp;
                }
                Log.d(LOG_TAG, string);
            }
            while (cursor.moveToNext());

        }

    }

    public class DbHelper extends SQLiteOpenHelper{
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(LOG_TAG, "============Table created!=============");
            ContentValues contentValues = new ContentValues();
            String createPositionTable = "CREATE TABLE Positions (\n" +
                    "    id           INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    PositionName TEXT,\n" +
                    "    Salary       INTEGER\n" +
                    ");";
            sqLiteDatabase.execSQL(createPositionTable);
            for (int i = 0; i < positionName.length; i++){
                contentValues.clear();
                contentValues.put("PositionName", positionName[i]);
                contentValues.put("Salary", positionSalary[i]);
                sqLiteDatabase.insert("Positions", null, contentValues);

            }

            String createPeopleTable = "CREATE TABLE People (\n" +
                    "    id             INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    PeopleName     TEXT,\n" +
                    "    PeoplePosition INTEGER\n" +
                    ");";
            sqLiteDatabase.execSQL(createPeopleTable);
            for (int i =0; i < peopleName.length; i++) {
                contentValues.clear();
                contentValues.put("PeopleName", peopleName[i]);
                contentValues.put("PeoplePosition", peoplePosition[i]);
                sqLiteDatabase.insert("People", null, contentValues);

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public DbHelper(Context context) {
            super(context, "DB_37_Lesson", null, 1);
        }
    }
}

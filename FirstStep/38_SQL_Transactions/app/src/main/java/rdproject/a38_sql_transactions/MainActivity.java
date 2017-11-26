package rdproject.a38_sql_transactions;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLClientInfoException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.button)
    Button button;
    SQLiteDatabase database;
    public static final String DB_NAME = "38_Test_DB";
    public static final String TABLE_NAME = "SomeTable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDb();


    }

    private void initDb() {
        database = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "FirstNumber INTEGER, SecondNumber INT, ResultNumber INT)");
        database.delete(TABLE_NAME, null, null);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        database.delete(TABLE_NAME, null, null);
        long time1 = System.currentTimeMillis();
        initTable();
        long time2 = System.currentTimeMillis() - time1;
        textView2.setText("Time: " + Long.toString(time2) + "  ms");
    }

    private void initTable() {
        String sql = " INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();

        try {
            for (int i = 0; i < 1000; i++) {
                statement.clearBindings();
                statement.bindLong(1, i);
                statement.bindLong(2, i);
                statement.bindLong(3, i*i);
                statement.execute();

                /*
                ContentValues cv = new ContentValues();
                cv.put("FirstNumber", i);
                cv.put("SecondNumber", i);
                cv.put("ResultNumber", i*i);
                database.insert(TABLE_NAME, null, cv);

*/
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}

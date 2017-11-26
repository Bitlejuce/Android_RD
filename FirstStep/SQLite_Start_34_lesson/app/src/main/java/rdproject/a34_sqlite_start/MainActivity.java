package rdproject.a34_sqlite_start;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //  EditText name, email, id;
    //  Button add, read, clear, upd, del;

    private static final String KEY_COUNT = "COUNT";
    @BindView(R.id.id1)
    EditText id1;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.read)
    Button read;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.lay1)
    LinearLayout lay1;
    @BindView(R.id.upd)
    Button upd;
    @BindView(R.id.del)
    Button del;
    @BindView(R.id.lay2)
    LinearLayout lay2;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        helper = new DBHelper(this);
        if (savedInstanceState != null) {
            String btnTxt = savedInstanceState.getString(KEY_COUNT);
            add.setText(btnTxt);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_COUNT, add.getText().toString());
    }



    @OnClick({R.id.add, R.id.read, R.id.clear, R.id.upd, R.id.del})
    public void onClick(View v) {
        String nameField = name.getText().toString();
        String emailField = email.getText().toString();
        String idField = id1.getText().toString();

        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {
            case R.id.add:
                contentValues.put(DBHelper.KEY_NAME, nameField);
                contentValues.put(DBHelper.KEY_EMAIL, emailField);
                database.insert(DBHelper.TABLE_NAME, null, contentValues);
                add.setText("just added");
                break;
            case R.id.read:
                Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int idName = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int idEmail = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
                    do {
                        Log.d("MyLog", "ID = " + cursor.getInt(idIndex) + ", NAME = " + cursor.getString(idName) +
                                ", EMAIL = " + cursor.getString(idEmail));
                    }
                    while (cursor.moveToNext());
                } else {
                    Log.d("MyLog", "Empty table");
                }
                cursor.close();
                break;
            case R.id.clear:
                database.delete(DBHelper.TABLE_NAME, null, null);
                break;
            case R.id.del:
                if (nameField.equalsIgnoreCase("")) break;
                int delCount = database.delete(DBHelper.TABLE_NAME, DBHelper.KEY_NAME + "= ?", new String[]{nameField});
                Log.d("MyLog", "delete row count = " + delCount);
                break;
            case R.id.upd:
                if (idField.equalsIgnoreCase("")) break;
                contentValues.put(DBHelper.KEY_NAME, nameField);
                contentValues.put(DBHelper.KEY_EMAIL, emailField);
                int updCount = database.update(DBHelper.TABLE_NAME, contentValues, DBHelper.KEY_ID + "= ?", new String[]{idField});
                Log.d("MyLog", "updates row count = " + updCount);
                break;
        }
        helper.close();
    }
}

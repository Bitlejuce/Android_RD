package rdproject.a33_preferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button load;
    Button save;
    EditText editText;
    final String SAVED_TEXT = "saved text";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        save = (Button) findViewById(R.id.saveBtn);
        load = (Button) findViewById(R.id.loadBtn);

        save.setOnClickListener(this);
        load.setOnClickListener(this);

        loadText();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.saveBtn:
                saveText();
                break;
            case R.id.loadBtn:
                loadText();
                break;
            default: break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    private void loadText() {
        preferences = getSharedPreferences("PreferenceFile", MODE_PRIVATE);
        String loaded = preferences.getString(SAVED_TEXT, "");
        editText.setText(loaded);
        Toast.makeText(MainActivity.this, "Text Loaded", Toast.LENGTH_SHORT).show();

    }

    private void saveText() {
        preferences = getSharedPreferences("PreferenceFile", MODE_PRIVATE);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(SAVED_TEXT, editText.getText().toString());
        ed.commit();
        Toast.makeText(this, SAVED_TEXT, Toast.LENGTH_SHORT).show();

    }
}

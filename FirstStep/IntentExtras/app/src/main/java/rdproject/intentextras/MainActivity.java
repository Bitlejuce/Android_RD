package rdproject.intentextras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);


    }

    public void toSecondActivity(View view) {
        Intent intent = new Intent(this, NameActiv.class);
        intent.putExtra("name", text.getText().toString());
        startActivityForResult(intent, 1 );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        String str = data.getStringExtra("name");
        text.setText("Your input is:   " +  str);
    }
}


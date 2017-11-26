package rdproject.intentextras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NameActiv extends AppCompatActivity {

    TextView textView;
    EditText textForBack;
    Button sendTo1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

         textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();

         textForBack = (EditText) findViewById(R.id.editText2);
        sendTo1Button = (Button) findViewById(R.id.sendTo1Act);

        String name = intent.getStringExtra("name");
       // name = " What da fuck";
        textView.setText(name);
    }

    public void backToFirstActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", textForBack.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}

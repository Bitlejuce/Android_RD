package rdproject.intent_26;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button timeButton, dateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeButton = (Button) findViewById(R.id.time_btn);
        dateButton = (Button) findViewById(R.id.date_btn);

        timeButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.time_btn:
                intent = new Intent("rdprogect.time");
                startActivity(intent);
                break;

            case R.id.date_btn:
                intent = new Intent("rdprogect.date");
                startActivity(intent);
                break;

        }

    }
}

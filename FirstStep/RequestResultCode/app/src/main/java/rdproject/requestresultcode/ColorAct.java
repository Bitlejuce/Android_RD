package rdproject.requestresultcode;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ColorAct extends AppCompatActivity implements View.OnClickListener{

    Button red, green, blue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        red = (Button) findViewById(R.id.red);
        green = (Button) findViewById(R.id.green);
        blue = (Button) findViewById(R.id.blue);

        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.red:
                intent.putExtra("color", Color.RED);
                break;
            case R.id.green:
                intent.putExtra("color", Color.GREEN);
                break;
            case R.id.blue:
                intent.putExtra("color", Color.BLUE);
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}

package rdproject.requestresultcode;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGNMENT = 2;


    Button color, alignment;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        color = (Button) findViewById(R.id.color);
        alignment = (Button) findViewById(R.id.alignment);

        color.setOnClickListener(this);
        alignment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.color :
                intent = new Intent(this, ColorAct.class);
                startActivityForResult(intent, REQUEST_CODE_COLOR);
                break;
            case R.id.alignment:
                intent = new Intent(this, AlignmentAct.class);
                startActivityForResult(intent, REQUEST_CODE_ALIGNMENT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("myLog", "requestCode = " + requestCode + "   resultCode = " + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_COLOR:
                    int color = data.getIntExtra("color", Color.WHITE);
                    textView.setTextColor(color);
                    break;
                case REQUEST_CODE_ALIGNMENT:
                    int align = data.getIntExtra("alignment", Gravity.LEFT);
                    textView.setGravity(align);
                    break;
            }
        }
        else Toast.makeText(this, "Something is wrong!", Toast.LENGTH_SHORT).show();
    }
}

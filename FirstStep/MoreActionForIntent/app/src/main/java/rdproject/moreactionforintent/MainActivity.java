package rdproject.moreactionforintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button time =(Button) findViewById(R.id.buttonTime);
        Button date =(Button) findViewById(R.id.buttonDate);
        time.setOnClickListener(this);
        date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.buttonTime:
                 intent = new Intent("rdproject.showtime.time");
                startActivity(intent);
                break;

            case R.id.buttonDate:
                 intent = new Intent("rdproject.showdate.date");
                startActivity(intent);
                break;
        }
    }
}

package rdproject.intent_26;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy");
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView textView = (TextView) findViewById(R.id.dateView);
        textView.setText(time);
    }
}

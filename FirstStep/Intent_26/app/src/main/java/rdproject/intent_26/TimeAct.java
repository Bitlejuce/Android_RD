package rdproject.intent_26;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", new Locale("UA"));
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+03"));
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView textView = (TextView) findViewById(R.id.timeView);
        textView.setText(time);
    }
}

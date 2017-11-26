package rdproject.moreactionforintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        String action = intent.getAction();

        String format = "", textInfo = "";

        if (action.equals("rdproject.showtime.time")){
            format = "HH:mm:ss";
            textInfo = "Time: ";
        } else
            if (action.equals("rdproject.showdate.date")) {
                format = "dd:MM:yyyy";
                textInfo = "Date: ";
            }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+03"));
        String out = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView editText = (TextView) findViewById(R.id.editText);
        editText.setText(textInfo + out);


    }
}

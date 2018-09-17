package btest.bapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BMainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmain);

        textView = findViewById(R.id.text_view);
        textTimer = findViewById(R.id.text_timer);


        Intent intent = getIntent();
        String from = getIntent().getStringExtra("from");
        if (from == null) {
            closeIfLaunchedNotFromA();
        }else {

            String linkToPic = intent.getStringExtra("linkToPic");
            int status = intent.getIntExtra("status", 3);
            textView.setText(linkToPic);
        }


    }

    private void closeIfLaunchedNotFromA() {
        textView.setText(R.string.after_10_sec);
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                String message = getString(R.string.time_left)
                        + millisUntilFinished / 1000;
                textTimer.setText(message);
            }
            public void onFinish() {
                finish();
            }
        }.start();
    }
}

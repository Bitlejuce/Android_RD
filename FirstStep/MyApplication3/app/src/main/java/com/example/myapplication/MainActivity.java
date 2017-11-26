package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.myapplication.R.string.*;

public class MainActivity extends AppCompatActivity {

    private final static String LOG = "ThisIsMyLog";

    TextView tupoText;
    Button button1;
    Button button2;
    Button button3;
    ImageView imageView;
    Button changeImage;
    Button toastBut;
    int currentPicNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscreen);

        Log.d(LOG, "хз че тут писать");
        tupoText = (TextView) findViewById(R.id.tupo_text);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        toastBut = (Button) findViewById(R.id.toastBut);
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageResource(R.drawable.q1);
        changeImage =(Button) findViewById(R.id.changeImage);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "method onClick is running");
                switch (v.getId()) {
                    case R.id.button1 : tupoText.setText(button1_pres); break;
                    case R.id.button2 : tupoText.setText(button2_pres); break;
                    case R.id.button3 : tupoText.setText(button3_pres); break;
                    case R.id.changeImage : changingImage();
                }
            }
        };
            button1.setOnClickListener(listener);
            button2.setOnClickListener(listener);
            button3.setOnClickListener(listener);
            changeImage.setOnClickListener(listener);

            tupoText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   button1.setText(text_pres);
                   button2.setText(text_pres);
                   button3.setText(text_pres);
                }
            });
        toastBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = new Toast(getBaseContext());
                View layout = getLayoutInflater().inflate(R.layout.toast,null);
                toast.setView(layout);
                toast.setGravity(Gravity.CENTER, 0, -250);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }

    public void changingImage() {
        if (currentPicNumber > 10) currentPicNumber = 1;

        int drawable;
        switch (currentPicNumber) {
            case 1 : drawable = R.drawable.q2; currentPicNumber++; break;
            case 2 : drawable = R.drawable.q3; currentPicNumber++; break;
            case 3 : drawable = R.drawable.q4; currentPicNumber++; break;
            case 4 : drawable = R.drawable.q5; currentPicNumber++; break;
            case 5 : drawable = R.drawable.q6; currentPicNumber++; break;
            case 6 : drawable = R.drawable.q7; currentPicNumber++; break;
            case 7 : drawable = R.drawable.q8; currentPicNumber++; break;
            case 8 : drawable = R.drawable.q9; currentPicNumber++; break;
            case 9 : drawable = R.drawable.q10; currentPicNumber++; break;
            case 10 : drawable = R.drawable.q1; currentPicNumber++; break;
            default: drawable = R.drawable.q1; currentPicNumber++;
        }
        imageView.setImageResource(drawable);
    }
}

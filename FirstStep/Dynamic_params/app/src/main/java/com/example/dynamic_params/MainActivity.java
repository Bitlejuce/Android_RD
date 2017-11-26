package com.example.dynamic_params;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    Button button1, button2;
    SeekBar seekBar;
    LinearLayout.LayoutParams lp1;
    LinearLayout.LayoutParams lp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        lp1 = (LinearLayout.LayoutParams) button1.getLayoutParams();
        lp2 = (LinearLayout.LayoutParams) button2.getLayoutParams();


    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "O-o-o-la-la!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int leftValue = progress;
        int rightValue = seekBar.getMax() - progress;
        lp1.weight = leftValue;
        lp2.weight = rightValue;

        button1.setText(String.valueOf(leftValue));
        button2.setText(String.valueOf(rightValue));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

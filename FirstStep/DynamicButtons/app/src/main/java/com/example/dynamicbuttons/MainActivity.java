package com.example.dynamicbuttons;

import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout mainLayout;
    LinearLayout txtBtnLayout;
    LinearLayout forButtonCreated;
    RadioGroup rg;

    EditText text;
    Button create;
    Button del;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usefull_act);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        txtBtnLayout = (LinearLayout) findViewById(R.id.txt_btn);
        forButtonCreated = (LinearLayout) findViewById(R.id.for_btn_created);
        rg = (RadioGroup) findViewById(R.id.rg);
        text = (EditText) findViewById(R.id.editText);
        create = (Button) findViewById(R.id.create_btn);
        del = (Button) findViewById(R.id.del_btn);

        create.setOnClickListener(this);
        del.setOnClickListener(this);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.clearComposingText();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_btn: {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int btnGravity = Gravity.LEFT;

                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.left:
                        btnGravity = Gravity.LEFT;
                        break;
                    case R.id.center:
                        btnGravity = Gravity.CENTER_HORIZONTAL;
                        break;
                    case R.id.right:
                        btnGravity = Gravity.RIGHT;
                        break;
                }
                layoutParams.gravity = btnGravity;
                Button buttonNEW = new Button(this);
                buttonNEW.setText(text.getText());
                forButtonCreated.addView(buttonNEW, layoutParams);
                break;
            }
            case R.id.del_btn : {
                forButtonCreated.removeAllViews();
                Toast.makeText(this, "All Buttons are DELETED!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}

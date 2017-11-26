package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import static com.example.myapplication.R.string.*;

public class MainActivity extends AppCompatActivity {
    Button info;
    Button change_text;
    CheckBox checkBoxInfo;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         info = (Button) findViewById(R.id.info);
         change_text = (Button) findViewById(R.id.change_text);
         checkBoxInfo = (CheckBox) findViewById(R.id.checkBoxInfo);
         txtView = (TextView) findViewById(R.id.textView);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



       info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Info.class));
            }
        });

    }

        public void clickButton3(View view){
            String currentText = String.valueOf(txtView.getText());
            if (currentText.equals("What's up buddy!")) {
                txtView.setText(hi_buddy);
            }
            else txtView.setText(whats_up_buddy);

          /*int rnd = (int) (Math.random()*50000);
            String s = String.valueOf(rnd);
            txtView.setText(s);  */
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

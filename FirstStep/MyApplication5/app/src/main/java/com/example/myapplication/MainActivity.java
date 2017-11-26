package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.myapplication.R.id.*;
import static com.example.myapplication.R.id.la;

public class MainActivity extends AppCompatActivity {

    TextView la, ne;
    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_BLUE = 2;
    final int MENU_COLOR_GREEN = 3;

    final int MENU_SIZE_22 = 4;
    final int MENU_SIZE_26 = 5;
    final int MENU_SIZE_30 = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        la = (TextView)findViewById(R.id.la);
        ne = (TextView)findViewById(R.id.ne);

        registerForContextMenu(la);
        registerForContextMenu(ne);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.la: {
                menu.add(0, MENU_COLOR_RED, 1, "Red");
                menu.add(0, MENU_COLOR_BLUE, 2, "Blue");
                menu.add(0, MENU_COLOR_GREEN, 3, "Green");
                break;
            }
            case R.id.ne: {
                menu.add(0, MENU_SIZE_22, 1, "22");
                menu.add(0, MENU_SIZE_26, 2, "26");
                menu.add(0, MENU_SIZE_30, 3, "30");
                break;
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_COLOR_RED: la.setTextColor(Color.RED);
                la.setText("Color RED");
                break;
            case MENU_COLOR_BLUE: la.setTextColor(Color.BLUE);
                la.setText("Color BLUE");
                break;
            case MENU_COLOR_GREEN: la.setTextColor(Color.GREEN);
                la.setText("Color GREEN");
                break;
            case MENU_SIZE_22: ne.setTextSize(22);
                ne.setText("Size changed to 22");
                break;
            case MENU_SIZE_26: ne.setTextSize(26);
                ne.setText("Size changed to 26");
                break;
            case MENU_SIZE_30: ne.setTextSize(30);
                ne.setText("Size changed to 30");
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case  R.id.action_settings : {
                Toast.makeText(MainActivity.this, getString(R.string.action_settings), Toast.LENGTH_LONG).show();
                return true;
            }
             case  action_11 : {
                Toast.makeText(MainActivity.this, getString(R.string.action_1), Toast.LENGTH_LONG).show();
                return true;
            }
             case  action_22 : {
                Toast.makeText(MainActivity.this, getString(R.string.action_2), Toast.LENGTH_LONG).show();
                return true;
            }
             case  action_33 : {
                Toast.makeText(MainActivity.this, getString(R.string.action_3), Toast.LENGTH_LONG).show();
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }
}

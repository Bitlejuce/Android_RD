package atest.aapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import atest.aapplication.dao.DataListHandler;
import atest.aapplication.pojo.Link;

public class MainActivity extends AppCompatActivity {

    final Uri LINKS_URI = Uri.parse("content://atest.aapplication.LinksList/links");
    private EditText mTextMessage;
    private TextInputLayout inputLayout;
    private Button okButton;
    private RecyclerView listView;
    private List<Link> linkList;
    private DataListHandler handler;
    private RVadapter rVadapter;
    private Menu menu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_test:
                    inputLayout.setVisibility(View.VISIBLE);
                    okButton.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    menu.setGroupVisible(R.id.menu_group, false);
                    return true;
                case R.id.navigation_history:
                    inputLayout.setVisibility(View.INVISIBLE);
                    okButton.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    menu.setGroupVisible(R.id.menu_group, true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        okButton = findViewById(R.id.button);
        inputLayout = findViewById(R.id.message_layout);
        listView = findViewById(R.id.list_view);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        handler = new DataListHandler(this, "linksList");
        linkList = handler.getSavedLinks();
        rVadapter = new RVadapter(this);
        listView.setAdapter(rVadapter);
        getContentResolver().registerContentObserver(LINKS_URI, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                if (MainActivity.this.getWindow().getDecorView().getRootView().isShown()) {
                    refreshListView();
                }
                super.onChange(selfChange);
            }
        });
    }

    private void refreshListView() {
        linkList.clear();
        linkList.addAll(handler.getSavedLinks());
        rVadapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        refreshListView();
        super.onResume();
    }

    public void onButtonClicked(View view) {
        if (TextUtils.isEmpty(mTextMessage.getText())){
            mTextMessage.setError("Field cannot be empty!");
            return;
        }

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("btest.bapplication", "btest.bapplication.BMainActivity"));
        intent.putExtra("linkToPic",mTextMessage.getText().toString().trim());
        intent.putExtra("from","button");
        intent.putExtra("status",Link.STATUS_UNKNOWN);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, R.string.you_need_abb_b, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        this.menu = menu;

        menu.setGroupVisible(R.id.menu_group, false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_date:
                Collections.sort(linkList, new Comparator<Link>() {
                    @Override
                    public int compare(Link link, Link t1) {
                        return Long.valueOf(link.getDateMills()).compareTo(t1.getDateMills());
                    }
                });
                rVadapter.notifyDataSetChanged();
                return true;
            case R.id.sort_by_status:
                Collections.sort(linkList, new Comparator<Link>() {
                    @Override
                    public int compare(Link link, Link t1) {
                        return Integer.valueOf(link.getStatus()).compareTo(t1.getStatus());
                    }
                });
                rVadapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    @Override
    protected void onDestroy() {
        handler.close();
        super.onDestroy();
    }
}

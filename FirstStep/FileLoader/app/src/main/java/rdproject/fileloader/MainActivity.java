package rdproject.fileloader;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rdproject.fileloader.dao.DataListHandler;
import rdproject.fileloader.pojo.Link;
import rdproject.fileloader.util.FileLoader;
import rdproject.fileloader.util.OnSuccessListener;

public class MainActivity extends AppCompatActivity {


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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        handler = new DataListHandler(this, "linksList");
        linkList = handler.getSavedLinks();
        rVadapter = new RVadapter(this);
        listView.setAdapter(rVadapter);
        checkStoragePermission();
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
        if (TextUtils.isEmpty(mTextMessage.getText())) {
            mTextMessage.setError("Field cannot be empty!");
            return;
        }

        String uriToFile = mTextMessage.getText().toString().trim();
        FileLoader fileLoader = new FileLoader();
        fileLoader.addOnEventListener(new OnSuccessListener() {
            @Override
            public void onSuccess(String message) {
                Log.d("FileLoaderUtility", "Downloaded successfully at: " + message);
                 Link link = new Link(message, System.currentTimeMillis(), Link.STATUS_LOADED);
                 handler.insert(link);
                 refreshListView();
                Toast.makeText(MainActivity.this.getApplicationContext(),"Downloaded successfully at: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("FileLoaderUtility", "Downloaded fail: " + errorMessage);
                Link link = new Link(errorMessage, System.currentTimeMillis(), Link.STATUS_ERROR);
                handler.insert(link);
                refreshListView();
                Toast.makeText(MainActivity.this.getApplicationContext(),"Download Failed: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        checkStoragePermission();
        fileLoader.execute(uriToFile);
    }

    private void checkStoragePermission() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                int res = checkCallingOrSelfPermission(permission);

            if (!(res == PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "SD Card not found", Toast.LENGTH_LONG).show();

        }
    }

    @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.main_screen, menu);
            this.menu = menu;

            menu.setGroupVisible(R.id.menu_group, false);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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
                case R.id.clear_list:
                    linkList.clear();
                    rVadapter.notifyDataSetChanged();
                    handler.deleteAll();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public List<Link> getLinkList () {
            return linkList;
        }

        @Override
        protected void onDestroy () {
            handler.close();
            super.onDestroy();
        }

    }


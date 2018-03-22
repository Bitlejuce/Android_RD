package com.shoppinglist.rdproject.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shoppinglist.rdproject.shoppinglist.adapters.RVAdapter;
import com.shoppinglist.rdproject.shoppinglist.dialogs.AddDialog;
import com.shoppinglist.rdproject.shoppinglist.dialogs.AddListDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddDialog.OnTextInputListener, AddListDialog.OnListNameInputListener {
    public static final String APP_PREFERENCES = "listsettings";
    public static final String APP_PREFERENCES_LIST_NAME = "listname";
    private SharedPreferences mSettings;
    private RecyclerView rViewToDo;
    private RecyclerView rViewDone;
    private RVAdapter rAdapterToDo;
    private RVAdapter rAdapterDone;
    private RecyclerView.LayoutManager rLayoutManagerDo;
    private RecyclerView.LayoutManager rLayoutManagerDone;
    private DataListHolder dataListHolder;
    private List<Product> shoppingList;
    private List<Product> doneList;
    private String listName;
    private List<String> listOfLists;
    private SparseArray<String> mapOfLists;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDialog().show(getFragmentManager(), "AddDialog");

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // end of template
        // start code
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        loadPreferences();
        setTitle(listName);
        dataListHolder = new DataListHolder(MainScreen.this, listName);
        shoppingList = dataListHolder.getShoppingList();
        doneList = dataListHolder.getDoneList();
        //listOfLists = dataListHolder.getListOfLists();
        mapOfLists = dataListHolder.getmapOfLists();

        rViewToDo = findViewById(R.id.lis_to_do);
        rViewToDo.setHasFixedSize(true);
        rLayoutManagerDo = new LinearLayoutManager(this);
        rViewToDo.setLayoutManager(rLayoutManagerDo);
        rAdapterToDo = new RVAdapter(this, shoppingList, R.id.lis_to_do, listName);
        rViewToDo.setAdapter(rAdapterToDo);

        rViewDone = findViewById(R.id.list_done);
        rViewDone.setHasFixedSize(true);
        rLayoutManagerDone = new LinearLayoutManager(this);
        rViewDone.setLayoutManager(rLayoutManagerDone);
        rAdapterDone = new RVAdapter(this, doneList, R.id.list_done, listName);
        rViewDone.setAdapter(rAdapterDone);

    }

    @Override  // here we receive users input in Dialog and add it to shopping list
    public void getUserInput(String input, String quantity) {
        Product product = new Product(input, quantity, 0);
        shoppingList.add(0, product);
        rAdapterToDo.notifyItemInserted(0);
        dataListHolder.insert(product);
    }
    @Override  // here we get name of new list
    public void getListNameInput(String input) {
        this.listName = input;
        setTitle(input);
        dataListHolder.createTableIfNotExists(input);
        //listOfLists = dataListHolder.getListOfLists();
        shoppingList.clear();
        doneList.clear();
        shoppingList.addAll(dataListHolder.getShoppingList());
        doneList.addAll(dataListHolder.getDoneList());
        rAdapterToDo.notifyDataSetChanged();
        rAdapterDone.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, "Cannot share yet", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.clear_all:
                shoppingList.clear();
                doneList.clear();
                rAdapterToDo.notifyDataSetChanged();
                rAdapterDone.notifyDataSetChanged();
                dataListHolder.deleteAll();
                return true;
            case R.id.clear_done:
                doneList.clear();
                rAdapterDone.notifyDataSetChanged();
                dataListHolder.deleteDone();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.new_list) {
            new AddListDialog().show(getFragmentManager(), "AddListDialog");
          // dataListHolder.createTableIfNotExists(listName);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {  // need more tests
        super.onPause();
        // Remember data
       savePreferences();
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
            // Getting listname from preferences
            loadPreferences();
        Toast.makeText(this, "loaded after calling onResume", Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    protected void onDestroy() {
        // Remember data
        savePreferences();
        //closing database connection
        dataListHolder.close();
        super.onDestroy();
    }

    void savePreferences() {
        SharedPreferences.Editor ed = mSettings.edit();
        ed.putString(APP_PREFERENCES_LIST_NAME, listName);
        ed.apply();
    }

    void loadPreferences() {
        if (mSettings.contains(APP_PREFERENCES_LIST_NAME)) {
            listName = mSettings.getString(APP_PREFERENCES_LIST_NAME, "");
            Log.d("TEST", listName);
        }
        else listName = "Newlist1";
    }


}

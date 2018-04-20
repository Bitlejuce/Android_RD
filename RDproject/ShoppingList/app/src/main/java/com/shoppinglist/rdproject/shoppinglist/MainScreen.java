package com.shoppinglist.rdproject.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shoppinglist.rdproject.shoppinglist.adapters.RVAdapter;
import com.shoppinglist.rdproject.shoppinglist.dialogs.*;
import com.shoppinglist.rdproject.shoppinglist.login.LoginActivity;
import com.shoppinglist.rdproject.shoppinglist.login.UserForRealtimeDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddDialog.OnTextInputListener, AddListDialog.OnListNameInputListener,
        RenameListDialog.OnListListRenameListener, ChooseListDialog.OnChooseListListener, ModifyListDialog.OnModifyListListener, ModifyItemDialog.OnItemModifyListener {
    public static final String TAG = "MainActivity";
    public static final int LOGIN_RESULT = 2121;
    public static final String APP_PREFERENCES = "listsettings";
    public static final String APP_PREFERENCES_LIST_NAME = "listName";
    public static final String APP_PREFERENCES_MAP_OF_LISTS = "mapOfLists";
    public static final String APP_PREFERENCES_USER_EMAIL = "userEmail";
    public String userId;
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
    private List<String> listOfListsToDisplay;
    private Map<String, String> mapOfLists;
    private Spinner chooseListSpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    private ImageView userPicView;
    private TextView userNameView;
    private TextView userMailView;
    private MenuItem logMenuItem;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.ic_add_shopping_cart_black_48dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDialog().show(getFragmentManager(), "AddDialog");

            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //logMenuItem = navigationView.getMenu().findItem(R.id.log_out);
        // end of template
        // start code
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        loadPreferences();
        bindUserViews();
        setTitle(mapOfLists.get(listName));
        dataListHolder = new DataListHolder(MainScreen.this, listName);
        shoppingList = dataListHolder.getShoppingList();
        doneList = dataListHolder.getDoneList();

        rViewToDo = findViewById(R.id.lis_to_do);
        rViewToDo.setHasFixedSize(true);
        rLayoutManagerDo = new LinearLayoutManager(this);
        rViewToDo.setLayoutManager(rLayoutManagerDo);
        rAdapterToDo = new RVAdapter(this, shoppingList, R.id.lis_to_do, dataListHolder);
        rViewToDo.setAdapter(rAdapterToDo);

        rViewDone = findViewById(R.id.list_done);
        rViewDone.setHasFixedSize(true);
        rLayoutManagerDone = new LinearLayoutManager(this);
        rViewDone.setLayoutManager(rLayoutManagerDone);
        rAdapterDone = new RVAdapter(this, doneList, R.id.list_done, dataListHolder);
        rViewDone.setAdapter(rAdapterDone);
        listOfListsToDisplay = getListOfTablesToDisplay();
        chooseListSpinner = createSpinner();
        //Firebase ini
        userId =  Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabaseUtil.getDatabase();

        Log.d(TAG, "end of onCreate   " +  FacebookSdk.getApplicationSignature(getApplicationContext()));
    }
    @Override  // here we receive users input in Dialog and add it to shopping list
    public void getUserInput(String input, String quantity) {
        Product product = new Product(input, quantity, 0);
        shoppingList.add(0, product);
        rAdapterToDo.notifyItemInserted(0);
        dataListHolder.insert(product);
        rViewToDo.scrollToPosition(0);
        databaseRef.child(mapOfLists.get(listName)).child(input).setValue(product);    //  FIREBASE +
    }
    @Override  // here we get name of new list
    public void getListNameInput(String input) {
        String newTableName = "Newlist" + (dataListHolder.getMaxIndexOfTable() + 1);
        this.listName = newTableName;
        mapOfLists.put(newTableName, input);
        setTitle(input);
        renewViewOfMainScreen(newTableName);
        listOfListsToDisplay.add(0, input);
        spinnerAdapter.notifyDataSetChanged();
        chooseListSpinner.setSelection(0);
    }
    @Override  // here we get NEW name of new list (AddListDialog)
    public void getNewListNameInput(String input) {
        databaseRef.child(mapOfLists.get(listName)).removeValue();    //  FIREBASE +
        listOfListsToDisplay.remove(mapOfLists.get(listName));
        listOfListsToDisplay.add(0, input);
        mapOfLists.put(listName, input);
        setTitle(input);
        spinnerAdapter.notifyDataSetChanged();
        databaseInitialize(mAuth.getCurrentUser());          //  FIREBASE +
    }
    @Override   //getting user choice and update UI  (ChooseListDialog)
    public void getUserChoice(String listNameToDisplay) {
        for (String key : mapOfLists.keySet()) {
            if (mapOfLists.get(key).equals(listNameToDisplay)) {
                listName = key;
                break;
            }
        }
        setTitle(listNameToDisplay);
        renewViewOfMainScreen(listName);
        chooseListSpinner.setSelection(listOfListsToDisplay.indexOf(listNameToDisplay));
        drawer.closeDrawer(GravityCompat.START);
        databaseInitialize(mAuth.getCurrentUser());    //  FIREBASE+
    }
    @Override // from Modify List Dialog
    public void getUserConfirm(String option) {
        databaseRef.child(mapOfLists.get(listName)).removeValue();    //  FIREBASE +
            if (option.equals(getResources().getString(R.string.delete_list))) {
                dataListHolder.deleteTable(listName);
                listOfListsToDisplay.remove(mapOfLists.get(listName));
                mapOfLists.remove(listName);

                if (mapOfLists.isEmpty()) {
                    renewViewOfMainScreen("Newlist1");
                    setTitle("Newlist1");
                    mapOfLists.put("Newlist1", "Newlist1");
                    listOfListsToDisplay.add("Newlist1");
                    spinnerAdapter.notifyDataSetChanged();
                } else {
                    String firstTable = dataListHolder.getListOfLists().get(0);
                    listName = firstTable;
                    setTitle(mapOfLists.get(firstTable));
                    listOfListsToDisplay.remove(mapOfLists.get(firstTable));
                    listOfListsToDisplay.add(0, mapOfLists.get(firstTable));
                    spinnerAdapter.notifyDataSetChanged();
                    chooseListSpinner.setSelection(0);
                    renewViewOfMainScreen(firstTable);
                    databaseInitialize(mAuth.getCurrentUser());
                }
                databaseInitialize(mAuth.getCurrentUser());    //  FIREBASE +
                return;
            }else if(option.equals(getResources().getString(R.string.clear_done))) {
                doneList.clear();
                rAdapterDone.notifyDataSetChanged();
                dataListHolder.deleteDone();
                databaseInitialize(mAuth.getCurrentUser());    //  FIREBASE +
                return;
            }else if(option.equals(getResources().getString(R.string.clear_all))) {
                shoppingList.clear();
                doneList.clear();
                rAdapterToDo.notifyDataSetChanged();
                rAdapterDone.notifyDataSetChanged();
                dataListHolder.deleteAll();
                databaseInitialize(mAuth.getCurrentUser());    //  FIREBASE +
                return;
        }
        databaseInitialize(mAuth.getCurrentUser());    //  FIREBASE +
    }

    @Override
    public void getItemModificationInput(String input, List<Product> product, int position) {
        Product p = product.get(position);
        databaseRef.child(mapOfLists.get(listName)).child(p.getName()).removeValue();   //  FIREBASE +
        if (input == null) {
            product.remove(position);
            dataListHolder.delete(p);
            rAdapterToDo.notifyDataSetChanged();
            rAdapterDone.notifyDataSetChanged();
        } else {
            dataListHolder.delete(p);
            p.setName(input);
            dataListHolder.insert(p);
            rAdapterToDo.notifyDataSetChanged();
            rAdapterDone.notifyDataSetChanged();
            databaseRef.child(mapOfLists.get(listName)).child(p.getName()).setValue(p);    //  FIREBASE +
        }
    }

    private void renewViewOfMainScreen(String newTableName) {
        dataListHolder.createTableIfNotExists(newTableName);
        shoppingList.clear();
        doneList.clear();
        shoppingList.addAll(dataListHolder.getShoppingList());
        doneList.addAll(dataListHolder.getDoneList());
        rAdapterToDo.notifyDataSetChanged();
        rAdapterDone.notifyDataSetChanged();
        //createSpinner();
    }

    @Override
    public void onBackPressed() {
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
        ModifyListDialog modifyDialog;
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, "Cannot share yet", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.rename:
                new RenameListDialog().show(getFragmentManager(), "RenameListDialog");
                return true;
            case R.id.sort_list:
                Collections.sort(shoppingList, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                });
                rAdapterToDo.notifyDataSetChanged();
                return true;
            case R.id.delete:
                modifyDialog = new ModifyListDialog();
                modifyDialog.setOption(getResources().getString(R.string.delete_list));
                modifyDialog.show(getFragmentManager(), "ModifyListDialog");
                return true;
            case R.id.clear_done:
                modifyDialog = new ModifyListDialog();
                modifyDialog.setOption(getResources().getString(R.string.clear_done));
                modifyDialog.show(getFragmentManager(), "ModifyListDialog");
                return true;
            case R.id.clear_all:
                modifyDialog = new ModifyListDialog();
                modifyDialog.setOption(getResources().getString(R.string.clear_all));
                modifyDialog.show(getFragmentManager(), "ModifyListDialog");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.log_out) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_RESULT);
            return true;

        } else if (id == R.id.new_list) {
            new AddListDialog().show(getFragmentManager(), "AddListDialog");
        } else if (id == R.id.choose_list) {
            new ChooseListDialog().show(getFragmentManager(), "ChooseListDialog");
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOGIN_RESULT:{
                    String userName = data.getStringExtra("userName");
                    String userMail = data.getStringExtra("userMail");
                    Uri userPic = data.getParcelableExtra("userPic");

                    fillUserDatails(userName, userMail, userPic);
                }
            }
        }else{
            fillDefaultUserDatails();
        }
    }

    private void fillDefaultUserDatails() {
        userPicView.setImageResource(R.mipmap.ic_launcher_round);
        userNameView.setText(R.string.app_name);
        userMailView.setText(R.string.make_shopping_easier);
        logMenuItem.setTitle(R.string.login);
    }

    private void fillUserDatails(String userName, String userMail, Uri userPic) {

        if (userName != null && !userName.equals("")) {
            userNameView.setText(userName);
        } else {
            userNameView.setText(R.string.app_name);
        }
        if (userMail != null && !userMail.equals("")) {
            userMailView.setText(userMail);
        }else {
            try {
                userMailView.setText(mSettings.getString(APP_PREFERENCES_USER_EMAIL, getResources().getString(R.string.make_shopping_easier)));
            }catch (Exception e){
                userMailView.setText(R.string.make_shopping_easier);
            }
        }
        if (userPic != null) {
            Picasso.get().load(userPic).transform(new CropCircleTransformation()).into(userPicView);
        }else {
            userPicView.setImageResource(R.mipmap.ic_launcher_round);
        }
        logMenuItem.setTitle(R.string.logout);
    }

    @Override
    protected void onPause() {  // need more tests
        super.onPause();
        // Remember data
       savePreferences();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        databaseInitialize(currentUser);
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            String currentUserEmail = currentUser.getEmail();
            if (currentUserEmail == null || currentUserEmail.equals("")){
                //currentUserEmail = FirebaseUser.getProviderData().getEmail();
            }
            fillUserDatails(currentUser.getDisplayName(), currentUser.getEmail(), currentUser.getPhotoUrl());
        }
    }

    private void databaseInitialize(FirebaseUser currentUser){
        databaseRef = database.getReference("Users").child(userId);
        String userName = null;
        String userMail = null;
            if (currentUser != null) {
                userName = currentUser.getDisplayName();
                if (userName == null || userName.equals("")) {
                    userName = "NoNameUser";
                }
                userMail = currentUser.getEmail();
                if (userMail == null || userMail.equals("")) {
                    try {
                        userMail = mSettings.getString(APP_PREFERENCES_USER_EMAIL, getResources().getString(R.string.make_shopping_easier));
                    }catch (Exception e){
                        // do nothing
                    }
                }
            }
           // databaseRef.child(userId).setValue(new UserForRealtimeDatabase(userName, userMail));

        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("email", userMail);
        updates.put("name", userName);

        databaseRef.updateChildren(updates);

            // add current list to Firebase
            for ( Product product : shoppingList){
                databaseRef.child(mapOfLists.get(listName)).child(product.getName()).setValue(product);
            }
            for ( Product product : doneList){
                databaseRef.child(mapOfLists.get(listName)).child(product.getName()).setValue(product);
        }
    }

    @Override
    protected void onDestroy() {
        // Remember data
        savePreferences();
        //closing database connection
        dataListHolder.close();
        super.onDestroy();
    }

    private void bindUserViews() {
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view_header_only);
        View mHeaderView = mNavigationView.getHeaderView(0);
        NavigationView navMenuView = (NavigationView) findViewById(R.id.nav_view);
        logMenuItem = navMenuView.getMenu().findItem(R.id.log_out);
        userPicView = mHeaderView.findViewById(R.id.imageView);
        userNameView = mHeaderView.findViewById(R.id.user_name);
        userMailView = mHeaderView.findViewById(R.id.user_mail);
    }

    void savePreferences() {
        SharedPreferences.Editor ed = mSettings.edit();
        ed.putString(APP_PREFERENCES_LIST_NAME, listName);
        ed.putString(APP_PREFERENCES_MAP_OF_LISTS, saveMapToString(mapOfLists));
        ed.apply();
    }

    void loadPreferences() {
        if (mSettings.contains(APP_PREFERENCES_LIST_NAME)) {
            listName = mSettings.getString(APP_PREFERENCES_LIST_NAME, "");
            String savedMap = mSettings.getString(APP_PREFERENCES_MAP_OF_LISTS, "");
            mapOfLists = getSavedMap(savedMap);
            Log.d("TEST", listName);
        }
        else {
            listName = "Newlist1";
            mapOfLists = new HashMap<>();
            mapOfLists.put(listName, listName);
        }
    }
    private Map<String, String> getSavedMap(String mapSavedToString) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(mapSavedToString);
            Iterator<String> keysItr = jsonObject.keys();
            while(keysItr.hasNext()) {
                String key = keysItr.next();
                String value = (String) jsonObject.get(key);
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
    private String saveMapToString(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();

    }
    public List<String> getListOfTablesToDisplay() {
        List<String> list = new ArrayList<>();
        for (String s: mapOfLists.keySet())
            list.add(mapOfLists.get(s));
        list.remove(mapOfLists.get(listName));
        list.add(0, mapOfLists.get(listName));
        return list;
    }

    private Spinner createSpinner() {
        Spinner spinner = findViewById(R.id.spinner_list);
        spinnerAdapter = getSpinnerAdapter();
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getUserChoice(listOfListsToDisplay.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return spinner;
    }

    @NonNull
    private ArrayAdapter<String> getSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, listOfListsToDisplay);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}

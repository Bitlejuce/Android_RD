package rd.declarationtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import rd.declarationtest.dao.DataListHandler;
import rd.declarationtest.pojo.Item;
import rd.declarationtest.pojo.NazkGovResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private static final String TABLE_NAME = "FAVORITES";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    listToShow.clear();
                    listToShow.addAll(personList);
                    rVadapter.notifyDataSetChanged();
                    return true;
                case R.id.navigation_favorites:
                    personList.clear();
                    personList.addAll(listToShow);
                    listToShow.clear();
                    listToShow.addAll(favoriteList);
                    rVadapter.notifyDataSetChanged();

                    Log.d(TAG, "personList.isEmpty  " + personList.isEmpty());
                    for (Item item123: personList){
                        Log.d(TAG, "onStart    personList)" + item123.getFirstname()+"     " + item123.getFavorite());
                    }
                    return true;
                case R.id.navigation_notifications:
                    personList.clear();
                    personList.addAll(listToShow);
                    listToShow.clear();
                    rVadapter.notifyDataSetChanged();
                    return true;
            }
            return false;
        }
    };
    private MaterialSearchBar searchBar;
    private List<Item> listToShow = new ArrayList<>();
    private List<Item> personList = new ArrayList<>();
    private List<Item> favoriteList;
    private RecyclerView mainView;
    private RVadapter rVadapter;
    private DataListHandler dataListHandler;

    public List<Item> getFavoriteList() {
        return favoriteList;
    }
    public List<Item> getListToShow() {
        return listToShow;
    }
    public RVadapter getrVadapter() {
        return rVadapter;
    }
    public DataListHandler getDataListHandler() {
        return dataListHandler;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //todo extract string resource
        setTitle(getString(R.string.title));
        dataListHandler = new DataListHandler(this, TABLE_NAME);
        favoriteList = dataListHandler.getFavoriteList();
        mainView = findViewById(R.id.recycler_view);
        mainView.setHasFixedSize(true);
        mainView.setLayoutManager(new LinearLayoutManager(this));
        rVadapter = new RVadapter(this);
        mainView.setAdapter(rVadapter);


        for (Item item: favoriteList){
            Log.d(TAG, "onStart    favoriteList)" + item.getFirstname()+"     " + item.getFavorite());
        }

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
               //nothing to do
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if (text.toString().isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.field_cannot_be_empty, Toast.LENGTH_SHORT).show();
                }else {
                    if (navigation.getSelectedItemId() != R.id.navigation_home) {
                        navigation.setSelectedItemId(R.id.navigation_home);
                    }
                    searchResult(text.toString());
                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }
    private void searchResult (String searchText) {
        Log.d(TAG, "START searchResult (String searchText)");
        searchBar.setPlaceHolder(searchText);
        searchBar.disableSearch();
        App.getApi().getResults(searchText).enqueue(new Callback<NazkGovResult>() {
            @Override
            public void onResponse(Call<NazkGovResult> call, Response<NazkGovResult> response) {
                NazkGovResult nazkGovResult = response.body();
                listToShow.clear();
                if (nazkGovResult != null && nazkGovResult.getItems() != null) {
                    listToShow.addAll(nazkGovResult.getItems());
                    rVadapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_results, Toast.LENGTH_SHORT).show();
                }
                rVadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NazkGovResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.search_not_avilable, Toast.LENGTH_SHORT).show();
                Log.d("Error",t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //searchBar.disableSearch();
    }

    @Override
    protected void onStop() {
        dataListHandler.deleteAll();
        Log.d(TAG, "onStop");
        Toast.makeText(MainActivity.this, "onStop", Toast.LENGTH_SHORT).show();
        for (Item item: favoriteList){
            dataListHandler.insert(item);
            Log.d(TAG, "for (Item item: favoriteList)" + item.getFirstname()+"     " + item.getFavorite());
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        dataListHandler.close();
        super.onDestroy();
    }
}

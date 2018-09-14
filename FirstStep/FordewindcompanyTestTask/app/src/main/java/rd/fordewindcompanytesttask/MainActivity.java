package rd.fordewindcompanytesttask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;


import rd.fordewindcompanytesttask.dao.DataListHandler;
import rd.fordewindcompanytesttask.pojo.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AddCommentDialog.OnCommentInputListener {
    public static final String TAG = "MainActivity";
    private static final String TABLE_NAME = "FAVORITES";
    private static final String GITHUB_URL = "https://help.github.com/articles/github-terms-of-service/";

    private MaterialSearchBar searchBar;
    private List<User> listToShow = new ArrayList<>();
    private List<User> personList = new ArrayList<>();
    private List<User> favoriteList;
    private RecyclerView mainView;
    private WebView webView;
    private RVadapter rVadapter;
    private DataListHandler dataListHandler;

    public List<User> getFavoriteList() {
        return favoriteList;
    }
    public List<User> getListToShow() {
        return listToShow;
    }
    public RVadapter getrVadapter() {
        return rVadapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //setTitle(getString(R.string.title));
        dataListHandler = new DataListHandler(this, TABLE_NAME);
        favoriteList = dataListHandler.getFavoriteList();
        webView = findViewById(R.id.web_view);
        mainView = findViewById(R.id.recycler_view);
        mainView.setHasFixedSize(true);
        mainView.setLayoutManager(new LinearLayoutManager(this));
        rVadapter = new RVadapter(this);
        mainView.setAdapter(rVadapter);

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    listToShow.clear();
                    listToShow.addAll(personList);
                    rVadapter.notifyDataSetChanged();
                    mainView.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_favorites:
                    listToShow.clear();
                    listToShow.addAll(favoriteList);
                    rVadapter.notifyDataSetChanged();
                    mainView.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    webView.loadUrl(GITHUB_URL);
                    webView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    private void searchResult (String searchText) {
        searchBar.setPlaceHolder(searchText);
        searchBar.disableSearch();
        App.getApi().getResults(searchText, "100").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> userList = response.body();
                listToShow.clear();
                if (userList != null && userList.size() != 0) {
                    listToShow.addAll(userList);   // add search result to list attached to RecyclerView
                    personList.addAll(listToShow);                 // save results to another List
                    rVadapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_results, Toast.LENGTH_SHORT).show();
                }
                rVadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.search_not_avilable, Toast.LENGTH_SHORT).show();
                Log.d("Error",t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        dataListHandler.deleteAll();
        dataListHandler.insertAll(favoriteList);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        dataListHandler.close();
        super.onDestroy();
    }

    @Override
    public void getCommentInput(String input, int itemPosition) {
        favoriteList.get(itemPosition).setComment(input);
        rVadapter.notifyDataSetChanged();

    }
}

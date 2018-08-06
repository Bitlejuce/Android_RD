package rd.declarationtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import rd.declarationtest.pojo.Item;
import rd.declarationtest.pojo.NazkGovResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(MainActivity.this, "Dash", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications:
                    Toast.makeText(MainActivity.this, "Notif", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };
    private MaterialSearchBar searchBar;
    private List<Item> personList = new ArrayList<>();
    private List<Item> favoriteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //todo extract string resource
        setTitle(getString(R.string.title));

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
                    searchResult(text.toString());
                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }
    private void searchResult (String searchText) {
        searchBar.setPlaceHolder(searchText);
        searchBar.disableSearch();
        App.getApi().getResults(searchText).enqueue(new Callback<NazkGovResult>() {
            @Override
            public void onResponse(Call<NazkGovResult> call, Response<NazkGovResult> response) {
                NazkGovResult nazkGovResult = response.body();
                if (nazkGovResult != null) {
                    String res = nazkGovResult.getItems().get(0).getFirstname();
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Nothing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NazkGovResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Cannot load results", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //searchBar.disableSearch();
    }
}

package rdproject.a44_listview_events;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout layout;
    ListView listView;
    String[] names;
    public final static String LOGd = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.names,
              android.R.layout.simple_list_item_1);
        // R.layout.support_simple_spinner_dropdown_item);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(LOGd, "onItemClick     Item = " + (i+1) + ", Position = " + (l+1));
                Toast.makeText(MainActivity.this, ("Item = " + (i+1) + ", Position = " + (l+1)), Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(LOGd, "itemSelect: position = " + position + ", id = "
                        + id);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOGd, "itemSelect: nothing");
            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               //  Log.d(LOGd, "scrollState = " + scrollState);
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Log.d(LOGd, "scroll: firstVisibleItem = " + firstVisibleItem+ ", visibleItemCount" + visibleItemCount
                        + ", totalItemCount" + totalItemCount);
            }
        });
    }
}
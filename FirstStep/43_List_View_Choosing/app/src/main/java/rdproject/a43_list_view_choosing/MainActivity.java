package rdproject.a43_list_view_choosing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static String myLog = "MyLog";
    String[] names;
    ListView listView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btnChecked);
        button.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.lvMain);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.names,
                android.R.layout.simple_list_item_multiple_choice);
        listView.setAdapter(adapter);

        names = getResources().getStringArray(R.array.names);


    }
/*
    @Override
    public void onClick(View view) {
        String outString = "Checked: " + names[listView.getCheckedItemPosition()];
        Log.d(myLog, outString);
        Toast.makeText(this, outString, Toast.LENGTH_LONG).show();
    }
    */

    @Override
    public void onClick(View view) {
        String outString = "";
        Log.d(myLog, "Checked:");
        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();

        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            int key = sparseBooleanArray.keyAt(i);
            if (sparseBooleanArray.get(key)) {
                Log.d(myLog, names[key]);
                outString = outString + names[key] + ", ";
            }
        }
        Toast.makeText(this, outString.substring(0, outString.length()-2), Toast.LENGTH_LONG).show();
    }
}

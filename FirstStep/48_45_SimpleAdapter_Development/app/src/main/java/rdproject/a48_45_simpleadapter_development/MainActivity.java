package rdproject.a48_45_simpleadapter_development;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String[] names = {"Vasja", "Ro Do", "Katja", "Busja", "Goga"};
    Integer[] phones = {935555555, 935204941, 632626266, 670255656, 970001111};
    int img = R.drawable.me_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List list = new ArrayList<Map<String, Object>>(names.length);

        for (int i = 0; i < names.length; i++) {
            Map map = new HashMap<String, Object>();
            map.put("name", names[i]);
            map.put("phone", phones[i]);
            map.put("img", img);
            list.add(map);
        }
        String[] from = new String[] {"name", "phone","img" };
        int[] to = new int[] {R.id.names, R.id.phones, R.id.imgView};

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item, from, to );
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }
}

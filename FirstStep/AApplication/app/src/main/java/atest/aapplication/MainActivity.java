package atest.aapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import atest.aapplication.dao.DataListHandler;
import atest.aapplication.pojo.Link;

public class MainActivity extends AppCompatActivity {

    private EditText mTextMessage;
    private TextInputLayout inputLayout;
    private Button okButton;
    private RecyclerView listView;
    private List<Link> linkList;
    private DataListHandler handler;
    private RVadapter rVadapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_test:
                    inputLayout.setVisibility(View.VISIBLE);
                    okButton.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.navigation_history:
                    inputLayout.setVisibility(View.INVISIBLE);
                    okButton.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
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

    }

    public void onButtonClicked(View view) {
//        Date currentTime = new Date(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MM ,yyyy HH:mm:ss");
//        //Toast.makeText(this, mTextMessage.getText().toString() + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, mTextMessage.getText().toString() +sdf.format(currentTime), Toast.LENGTH_SHORT).show();
            handler.insert(new Link(mTextMessage.getText().toString().trim(), System.currentTimeMillis(), Link.STATUS_LOADED));

            rVadapter.notifyDataSetChanged();
    }

    public List<Link> getLinkList() {
        return linkList;
    }
}

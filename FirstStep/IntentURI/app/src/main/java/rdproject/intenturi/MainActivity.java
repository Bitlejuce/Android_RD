package rdproject.intenturi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button web, map, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = (Button) findViewById(R.id.web);
        map = (Button) findViewById(R.id.map);
        call = (Button) findViewById(R.id.call);

        web.setOnClickListener(this);
        map.setOnClickListener(this);
        call.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.web:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://javarush.ru"));
                startActivity(intent);
                break;
            case R.id.map:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo: -4.5345435, -90.435436"));
                startActivity(intent);
                break;
            case R.id.call:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 0935204941"));
                startActivity(intent);
                break;

        }

    }
}

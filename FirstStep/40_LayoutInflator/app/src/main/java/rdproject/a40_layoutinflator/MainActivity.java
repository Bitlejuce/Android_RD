package rdproject.a40_layoutinflator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String ML = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
        LayoutInflater la = getLayoutInflater();

        View view1 =la.inflate(R.layout.text, linearLayout,true);
        ViewGroup.LayoutParams layoutParams = view1.getLayoutParams();

      //  linearLayout.addView(view1);

        Log.d(ML, "Class of view " + view1.getClass().toString());
        Log.d(ML, "Class of LayoutParams :  " + layoutParams.getClass());
      //  Log.d(ML, "Text of view: " + ((TextView)view1).getText());

////////////////////////////////////////////////////////////////////////////////////////

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        View view2 =la.inflate(R.layout.text, relativeLayout,true);
        ViewGroup.LayoutParams layoutParams1 = view2.getLayoutParams();

       // relativeLayout.addView(view2);


        Log.d(ML, "Class of view " + view2.getClass().toString());
        Log.d(ML, "Class of LayoutParams :  " + layoutParams1.getClass());
       // Log.d(ML, "Text of view: " + ((TextView)view2).getText());

    }
}

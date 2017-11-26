package relativelayoutbutton.android.izvornikod.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class RelativeLayoutButtonActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        RelativeLayoutButton button1 = new RelativeLayoutButton(this,R.id.button1);
      
        button1.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(RelativeLayoutButtonActivity.this, "RelativeLayoutButton clicked", Toast.LENGTH_LONG).show();
				
			}
		});
        
        RelativeLayoutButton button2 = new RelativeLayoutButton(this,R.id.button2);
        button2.setText(R.id.test_button_text1, "Change");
        button2.setText(R.id.test_button_text2, "text");
        
        RelativeLayoutButton button3 = new RelativeLayoutButton(this,R.id.button3);
        button3.setText(R.id.test_button_text1, "Change");
        button3.setText(R.id.test_button_text2, "image");
        button3.setImageResource(R.id.test_button_image, android.R.drawable.star_big_on);
        
    }
}
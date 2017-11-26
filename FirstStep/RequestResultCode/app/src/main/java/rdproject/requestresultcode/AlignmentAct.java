package rdproject.requestresultcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class AlignmentAct extends AppCompatActivity implements View.OnClickListener{
    Button left, right, center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alignment);

        left = (Button) findViewById(R.id.left);
        center = (Button) findViewById(R.id.center);
        right = (Button) findViewById(R.id.right);

        left.setOnClickListener(this);
        center.setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.left:
                intent.putExtra("alignment", Gravity.LEFT);
                break;
            case R.id.center:
                intent.putExtra("alignment", Gravity.CENTER);
                break;
            case R.id.right:
                intent.putExtra("alignment", Gravity.RIGHT);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}

package servio.rd.servio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import servio.rd.servio.pojo.PlaceUnion;
import servio.rd.servio.pojo.PlaceUnions;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
    }

    public void getPlaces(View view) {
        textView.setText("");
        App.getApi().getResults().enqueue(new Callback<PlaceUnions>() {
            @Override
            public void onResponse(Call<PlaceUnions> call, Response<PlaceUnions> response) {
                PlaceUnions placeUnions = response.body();
                String placeUnionToShow = "";
                for (PlaceUnion placeUnion : placeUnions.getPlaceUnions()){
                    placeUnionToShow += placeUnion.getName();
                    placeUnionToShow+= "\n";
                }
                textView.setText(placeUnionToShow);
            }

            @Override
            public void onFailure(Call<PlaceUnions> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}

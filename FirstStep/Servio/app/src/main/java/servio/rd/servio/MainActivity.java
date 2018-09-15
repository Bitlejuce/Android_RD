package servio.rd.servio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import servio.rd.servio.pojo.Place;
import servio.rd.servio.pojo.PlaceGroup;
import servio.rd.servio.pojo.PlaceGroupSchema;
import servio.rd.servio.pojo.PlaceUnion;
import servio.rd.servio.pojo.PlaceUnions;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView placeUnions;
    private TextView placeGroups;
    private TextView placeGroupSchemas;
    private TextView places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        placeUnions = findViewById(R.id.place_unions);
        placeGroups = findViewById(R.id.place_groups);
        placeGroupSchemas = findViewById(R.id.place_groups_schemas);
        places = findViewById(R.id.places);
    }

    public void getPlaces(View view) {
        placeUnions.setText("");
        App.getApi().getResults().enqueue(new Callback<PlaceUnions>() {
            @Override
            public void onResponse(Call<PlaceUnions> call, Response<PlaceUnions> response) {
                PlaceUnions placeUnions = response.body();
                String placeUnionToShow = "";
                String placeGroupsToShow = "";
                String placeGroupSchemasToShow = "";
                String placeToShow = "";
                for (PlaceUnion placeUnion : placeUnions.getPlaceUnions()) {
                    placeUnionToShow += placeUnion.getName();
                    placeUnionToShow += "\n";

                    for (PlaceGroup placeGroup : placeUnion.getPlaceGroups()) {
                        placeGroupsToShow += placeGroup.getName();
                        placeGroupsToShow += "\n";

                        for (PlaceGroupSchema placeGroupSchema : placeGroup.getPlaceGroupSchemas()) {
                            placeGroupSchemasToShow += placeGroupSchema.getName();
                            placeGroupSchemasToShow += "\n";

                            for (Place place : placeGroupSchema.getPlaces()) {
                                placeToShow += place.getName();
                                placeToShow += "\n";
                            }
                        }
                    }

                    MainActivity.this.placeUnions.setText(placeUnionToShow);
                    MainActivity.this.placeGroups.setText(placeGroupsToShow);
                    MainActivity.this.placeGroupSchemas.setText(placeGroupSchemasToShow);
                    MainActivity.this.places.setText(placeToShow);
                }
            }

            @Override
            public void onFailure(Call<PlaceUnions> call, Throwable t) {
                placeUnions.setText(t.getMessage());
            }
        });
    }
}


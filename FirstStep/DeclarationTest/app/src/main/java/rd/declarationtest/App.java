package rd.declarationtest;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static NazkGovAPI nazkGovAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://public-api.nazk.gov.ua/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nazkGovAPI = retrofit.create(NazkGovAPI.class);
    }

    public static NazkGovAPI getApi() {
        return nazkGovAPI;
    }
}



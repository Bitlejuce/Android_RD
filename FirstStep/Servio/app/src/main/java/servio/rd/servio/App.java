package servio.rd.servio;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static ServioAPI servioAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://sms.servio.support:32892/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servioAPI = retrofit.create(ServioAPI.class);
    }

    public static ServioAPI getApi() {
        return servioAPI;
    }
}
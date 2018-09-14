package rd.fordewindcompanytesttask;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static GitHubUsersAPI gitHubUsersAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubUsersAPI = retrofit.create(GitHubUsersAPI.class);
    }

    public static GitHubUsersAPI getApi() {
        return gitHubUsersAPI;
    }
}



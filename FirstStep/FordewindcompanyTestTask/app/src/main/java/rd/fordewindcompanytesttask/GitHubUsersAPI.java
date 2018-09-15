package rd.fordewindcompanytesttask;

import java.util.List;


import rd.fordewindcompanytesttask.pojo.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubUsersAPI {
    @GET("users")
    Call<List<User>> getResults(@Query("since") String since, @Query("per_page") String per_page);

    @GET("users/{user}/followers")
    Call<List<User>> getFollowers(@Path("user") String user);

}

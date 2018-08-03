package rd.declarationtest;

import rd.declarationtest.pojo.NazkGovResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NazkGovAPI {
    @GET("v1/declaration/")
    Call<NazkGovResult> getResults(@Query("q") String query);

}

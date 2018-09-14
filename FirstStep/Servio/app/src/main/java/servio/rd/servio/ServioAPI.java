package servio.rd.servio;


import retrofit2.Call;
import retrofit2.http.POST;
import servio.rd.servio.pojo.PlaceUnions;

public interface ServioAPI {
    @POST("GetPlaces")
    Call<PlaceUnions> getResults();
}

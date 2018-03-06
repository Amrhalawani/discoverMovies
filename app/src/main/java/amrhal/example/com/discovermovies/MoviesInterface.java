package amrhal.example.com.discovermovies;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Amr hal on 03/03/2018.
 */
public interface MoviesInterface {
    @GET()
    Call<ResponseBody> getMovies(@Url String endPoint, @Query("api_key") String apiKey);

}
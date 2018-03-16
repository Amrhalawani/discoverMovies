package amrhal.example.com.discovermovies;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Amr hal on 14/03/2018.
 */

public interface MoviesInterface {
    @GET("movie/popular")
    Call<ResponseBody> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<ResponseBody> getTopRatedMovies(@Query("api_key") String apiKey);

}

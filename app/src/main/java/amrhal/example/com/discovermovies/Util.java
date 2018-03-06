package amrhal.example.com.discovermovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr hal on 03/03/2018.
 */

public class Util {
    static String Results_jsonArray = "results";
    static String movieTitle = "title";

    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_url = "w185";
    static String posterUrl;

    public static List<MovieModel> parseJson(String json) {
        List<MovieModel> listOfMovies = new ArrayList<>();
        try {
            if (json.isEmpty()){
                Log.e("tag", "Content of String json is Empty ******");
            }

            JSONObject jsonobj = new JSONObject(json);
            Log.e("tag", "********" );
            JSONArray resultJsonArray = jsonobj.getJSONArray(Results_jsonArray);
            Log.e("tag", "***********" );
            for (int i = 0; i < resultJsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);

                String movieName = jsonObject.getString(movieTitle);
                String posterPath = jsonObject.getString("poster_path");
                posterUrl = pic_base_url + pic_size_url + posterPath;
                MovieModel movieModel = new MovieModel(movieName, posterUrl);
                listOfMovies.add(movieModel);
                Log.e("tag", movieName + "  " + posterUrl);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("tag", "******Error in parsing = "+e.getLocalizedMessage()+"******* " +  e.getMessage() );
        }

        return listOfMovies;
    }
}

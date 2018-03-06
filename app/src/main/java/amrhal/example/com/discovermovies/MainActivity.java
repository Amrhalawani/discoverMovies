package amrhal.example.com.discovermovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private TextView mJsonContentTV;
    RecyclerView recyclerView;
    List<MovieModel> list;
    RecyclerAdaptor recyclerAdaptor;

    public static final String base_url = "http://api.themoviedb.org/3/";
    public static final String end_point_popular = "movie/popular";
    public static final String end_point_top_rated = "movie/top_rated";
    public static final String api_key = ""; // type your api key here
    //http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    public static String pic_base_url = "http://image.tmdb.org/t/p/";
    public static String pic_size_url = "w185";
    public static String pic_path = ""; //like ( nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg )

    public  String allJsonContent = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.popularityID:
                    getjson(end_point_popular);
                    if (list.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        mJsonContentTV.setVisibility(View.VISIBLE);
                        Log.e("tag", "list.isEmpty()= "+ list.isEmpty() );
                    }
                    else {
                        recyclerView.setVisibility(View.VISIBLE);
                        mJsonContentTV.setVisibility(View.GONE);
                        Log.e("tag", "list.isEmpty()="+ list.isEmpty() );
                    }
                    return true;
                case R.id.top_ratedID:

                    getjson(end_point_top_rated);
                    if (list.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        mJsonContentTV.setVisibility(View.VISIBLE);
                        Log.e("tag", "list.isEmpty()="+ list.isEmpty() );
                    }
                    else {
                        recyclerView.setVisibility(View.VISIBLE);
                        mJsonContentTV.setVisibility(View.GONE);
                        Log.e("tag", "list.isEmpty()="+ list.isEmpty() );
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getjson(end_point_popular);


        recyclerView = findViewById(R.id.recyclerviewID);
        list = new ArrayList<>();



        recyclerAdaptor = new RecyclerAdaptor(list, getApplicationContext());
        recyclerView.setAdapter(recyclerAdaptor);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mJsonContentTV = (TextView) findViewById(R.id.jsonContentID);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        list = Util.parseJson(allJsonContent); // hey, here is the problem allJsonContent (retrofit Response) is empty and i ca't access to it outside the inner class "Callback<ResponseBody> onResponse " take alook at line 126

       // list.add(new MovieModel("dasdasd","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbGMMqoY48zQQ9xQP0G8J5c_7x5DZIRR0DKFEGi9SUwqN3mkLj"));
        recyclerAdaptor.notifyDataSetChanged();


        Log.e("tag", "list size "+ list.size());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.e("TAG", "onCreate: "+ "allJsonContent = " +allJsonContent);
    }




    public String getjson(String endPoint) {


        final Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).build();
        MoviesInterface m = retrofit.create(MoviesInterface.class);
        m.getMovies(endPoint, api_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    allJsonContent = response.body().string();
                    mJsonContentTV.setText(allJsonContent);
                    Log.e("TAG", " retrofit onResponse: working well ");

                } catch (IOException e) {
                    Log.e("TAG", " retrofit onResponse:  response error and catch -> "+ e.getLocalizedMessage());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("TAG", "****** retrofit onFailure ******** ");
                mJsonContentTV.setText("Error Retrofit onFailure");
            }
        });

        return allJsonContent;
    }
}

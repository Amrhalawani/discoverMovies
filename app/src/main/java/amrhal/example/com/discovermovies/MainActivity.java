package amrhal.example.com.discovermovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.EventLog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import amrhal.example.com.discovermovies.Eventbustest.EventMassege;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private TextView mJsonContentTV;
    private String jsonContent = "";
    RecyclerView recyclerView;
    List<MovieModel> list;
    RecyclerAdaptor recyclerAdaptor;
    boolean aTest;
    Button btnRetry;
    TextView connectTointernetTV;
    public static final String base_url = "http://api.themoviedb.org/3/";
    public static final String api_key = "7dc3c3d78e52290fbaaca09a7fb34436";     //TODO type your api key here

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.popularityID:
                    Log.e(TAG, "onNavigationItemSelected: list is empty = " + list.isEmpty());
                    if (list.isEmpty()) {

                        recyclerView.setVisibility(View.GONE);
                        EventBus.getDefault().post(new EventMassege("Can't access to internet"));

                    } else {
                        getPopularMovies();
                        connectTointernetTV.setVisibility(View.INVISIBLE);
                        btnRetry.setVisibility(View.INVISIBLE);
                    }

                    return true;

                case R.id.top_ratedID:
                    if (list.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        EventBus.getDefault().post(new EventMassege("Can't access to internet"));
                    } else {
                        getTopRatedMovies();
                        connectTointernetTV.setVisibility(View.INVISIBLE);
                        btnRetry.setVisibility(View.INVISIBLE);
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


        connectTointernetTV = findViewById(R.id.emptyViewID);
        btnRetry = findViewById(R.id.button);
        list = new ArrayList<>();


        recyclerView = findViewById(R.id.recyclerviewID);

        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getPopularMovies();
        Log.e(TAG, "onCreate: list size=" + list.size());
        if (list.isEmpty()) {
            Log.e(TAG, "on Create (for visibility) when list.is empty= " + list.isEmpty());
            connectTointernetTV.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
        }
    }


    public void getPopularMovies() {

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).build();
        MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);
        moviesInterface.getPopularMovies(api_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        final String JsonBody = response.body().string();

                        recyclerAdaptor = new RecyclerAdaptor(MainActivity.this);
                        recyclerView.setAdapter(recyclerAdaptor);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        list = Util.parseJsonCTList(JsonBody);
                        recyclerAdaptor.updateData(list);
                        Log.e(TAG, "retrofit onResponse: list size = " + list.size());
                        connectTointernetTV.setVisibility(View.INVISIBLE);
                        btnRetry.setVisibility(View.INVISIBLE);


                        recyclerAdaptor.setOnItemClickListener(new RecyclerAdaptor.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                //  Toast.makeText(MainActivity.this, "Clicked on Pos " + position, Toast.LENGTH_LONG).show();
                                MovieModel movieModel = Util.parsejsonCTmovieObject(JsonBody, position);
                                Log.e(TAG, "main onItemClick: movieModel.getTitle()=" + movieModel.getTitle());

                                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                                intent.putExtra(DetailsActivity.EXTRA_POSITION, position);
                                intent.putExtra(DetailsActivity.EXTRA_TITLE, movieModel.getTitle());
                                intent.putExtra(DetailsActivity.EXTRA_POSTER, movieModel.getPosterUrl());
                                intent.putExtra(DetailsActivity.EXTRA_AVG, movieModel.getVoteAverage());
                                intent.putExtra(DetailsActivity.EXTRA_DATE, movieModel.getReleaseDate());
                                intent.putExtra(DetailsActivity.EXTRA_OVERVIEW, movieModel.getSynopsis());
                                startActivity(intent);

                                //  EventBus.getDefault().post(movieModel);//Todo here eventBus didn't work duo to lifecycle of detailactivity
                                // String s = "http://www.mechanicalboss.com/wp-content/uploads/2016/07/MECHANICAL_BOSS_Logo_2016_Def_SquareVer_A.jpg";
                                // EventBus.getDefault().post(new MovieModel("The Show", s, "13/05/2013", "7.9", "بسم الله الرحمن الرحيم ، تيست تيست تيست تيست"));
                            }
                        });

                    } else {
                        Log.e(TAG, "Retrofit onResponse: response.body() = null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void getTopRatedMovies() {


        final Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).build();
        MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);
        moviesInterface.getTopRatedMovies(api_key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String JsonBody = "";
                    if (response.body() != null) {
                        JsonBody = response.body().string();

                        recyclerView = findViewById(R.id.recyclerviewID);
                        recyclerAdaptor = new RecyclerAdaptor(MainActivity.this);
                        recyclerView.setAdapter(recyclerAdaptor);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        list = Util.parseJsonCTList(JsonBody);
                        recyclerAdaptor.updateData(list);
                        btnRetry.setVisibility(View.INVISIBLE);
                        //MovieModel movieModel = list.get(0);

                        final String finalJsonBody = JsonBody;
                        recyclerAdaptor.setOnItemClickListener(new RecyclerAdaptor.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                //   Toast.makeText(MainActivity.this, "Clicked on Pos " + position, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                                MovieModel movieModel = Util.parsejsonCTmovieObject(finalJsonBody, position);

                                intent.putExtra(DetailsActivity.EXTRA_POSITION, position);
                                intent.putExtra(DetailsActivity.EXTRA_TITLE, movieModel.getTitle());
                                intent.putExtra(DetailsActivity.EXTRA_POSTER, movieModel.getPosterUrl());
                                intent.putExtra(DetailsActivity.EXTRA_AVG, movieModel.getVoteAverage());
                                intent.putExtra(DetailsActivity.EXTRA_DATE, movieModel.getReleaseDate());
                                intent.putExtra(DetailsActivity.EXTRA_OVERVIEW, movieModel.getSynopsis());
                                startActivity(intent);
                            }
                        });


                    } else {
                        Log.e(TAG, "Retrofit onResponse: response.body() = null");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMassege(EventMassege event) {

        final Snackbar mysnackbar = Snackbar.make(findViewById(R.id.container), event.message,
                Snackbar.LENGTH_INDEFINITE);
        mysnackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysnackbar.dismiss();
                getPopularMovies();
            }
        })
                .show();
    }

    public void testEventBus(View view) {
        EventBus.getDefault().post(new EventMassege("First, Check your Internet Connection and then click Retry"));
    }
}


//<integer-array name="deletedMovies">
//<item>441614</item>
//<item>337167</item>
//<item>315837</item>
//<item>339964</item>
//<item>433310</item>
//<item>431530</item>
//<item>341013</item>
//<item>339404</item>
//<item>637</item>
//<item>680</item>
//<item>539</item>
//<item>19404</item>
//<item>55960</item>
//<item>550</item>
//<item>399055</item>
//<item>401981</item>
//<item>141052</item>
//<item>353486</item>
//<item>338970</item>
//</integer-array>
////this method in MainActivity to modify movieList and delete
////movies before populating UI
//private void modifyMovieList(List<MovieModel> list){
//        int[] deletedMoviesID= getResources().getIntArray(R.array.deletedMovies);
//        ModifyMovieList.modifyList(list,deletedMoviesID);
//        }
////I created a seperate class to include this method
//public static void modifyList(List<MovieModel> list,int[] deletedMoviesID){
//        Iterator<MovieModel> iterator= list.iterator();
//        while (iterator.hasNext()) {
//        int id = iterator.next().getId();
//        for (int deletedMovie : deletedMoviesID) {
//        if (id == deletedMovie) {
//        iterator.remove();
//        break;
//}}}}
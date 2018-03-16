package amrhal.example.com.discovermovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    public static String EXTRA_POSITION="position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        //TODO here get the intent (from 163 line MainActivity)and parse json from util by it's positions movie = list.get[position]
    }
}

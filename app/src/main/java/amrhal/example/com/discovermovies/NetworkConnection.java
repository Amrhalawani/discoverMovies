package amrhal.example.com.discovermovies;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import amrhal.example.com.discovermovies.Eventbustest.EventMassege;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by Amr hal on 13/03/2018.
 */

public class NetworkConnection {
   public static boolean result = false;

//    public static boolean isConnected(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null && activeNetwork.isConnected()) {
//            try {
//                URL url = new URL("http://www.google.com/");
//                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
//                urlc.setRequestProperty("User-Agent", "test");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(1000); // mTimeout is in seconds
//                urlc.connect();
//                if (urlc.getResponseCode() == 200) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (IOException e) {
//                Log.i("warning", "Error checking internet connection", e);
//                return false;
//            }
//        }
//
//        return false;
//
//    }

    public interface ConnectionInterface {
        @GET("doodles")
        Call<ResponseBody> getTest();
    }

    public static void connectToInternet() throws IOException {

        final String base_url = "http://www.google.com/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).build();
        ConnectionInterface Connection = retrofit.create(ConnectionInterface.class);
        Connection.getTest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String massege =  response.isSuccessful() ? "Accessing to Internet Succeed" : "Can't access to Internet"; //ternary operator
                Log.e("TAG", "Retrofit onResponse: response.isSuccessful()="+response.isSuccessful()+" / massege = "+massege);
                EventBus.getDefault().post(new EventMassege(massege));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
//
//    private class NetworkTestAsyncT extends AsyncTask<URL, Void, Boolean> {
//        @Override
//        protected Boolean doInBackground(URL... urls) {
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//        }
//
//
//    }

}

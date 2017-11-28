package com.dvb.movie_db.Activities;

import android.content.Context;
import android.content.res.ObbInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.MovementMethod;
import android.util.Log;
import android.widget.Toast;

import com.dvb.movie_db.AlertDialogFragment;
import com.dvb.movie_db.HttpHandler;
import com.dvb.movie_db.Model.MovieReview;
import com.dvb.movie_db.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dmitrybondarenko on 22.11.17.
 */

public class MovieReviewActivity extends AppCompatActivity {

    private static final String TAG = MovieReviewActivity.class.getSimpleName();
    private MovieReview mMovieReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_review_item);

        String apiKey = "?api_key=957c988676c0d274a6d1cc76dd5c8a93";
        String siteUrl = "https://api.themoviedb.org/3/movie/";


        String movieID = getIntent().getExtras().getString("MOVIE_ID");

        String url = siteUrl + movieID + apiKey;

        if (isNetworkAvailable()){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()){
                            mMovieReview = getMovieReviewJson(jsonData);
                        } else {
                            alertUserAboutError();
                        }

                    }
                    catch (JSONException e){
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });

        }
        else {
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
    }

    private MovieReview getMovieReviewJson(String jsonData) throws JSONException {
        JSONObject reviewJson = new JSONObject(jsonData);
        String original_title = reviewJson.getString("original_title");
        String poster_path = reviewJson.getString("poster_path");
        return null;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

}












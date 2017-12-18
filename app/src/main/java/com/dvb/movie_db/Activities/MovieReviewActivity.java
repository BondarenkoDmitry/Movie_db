package com.dvb.movie_db.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvb.movie_db.AlertDialogFragment;
import com.dvb.movie_db.Model.MovieReview;

import com.dvb.movie_db.R;
import com.dvb.movie_db.RoundedTransformation;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.text.DateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.R.attr.apiKey;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by dmitrybondarenko on 22.11.17.
 */

public class MovieReviewActivity extends AppCompatActivity {

    // Interface to change the behavior of the HTTP Request Method
    // I imagined the end of the request ...
    interface MoviesRequestInterface {
        void OnDataAvailable();
    }

    private static final String TAG = MovieReviewActivity.class.getSimpleName();
    private MovieReview mMovieReview;

    @InjectView(R.id.mrOriginalTitle)TextView mOriginalTitle;
    @InjectView(R.id.mrOverview)TextView mOverView;
    @InjectView(R.id.mrReleaseDate) TextView mReleaseDate;
    @InjectView(R.id.mrPoster)ImageView mPoster;
    @InjectView(R.id.mrRating) RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_review);
        ButterKnife.inject(this);


        String apiKey = "?api_key=957c988676c0d274a6d1cc76dd5c8a93";
        String siteUrl = "https://api.themoviedb.org/3/movie/";
        String REVIEWS = "/reviews";
        String VIDEOS = "/videos";

        int movieID = getIntent()
                .getExtras()
                .getInt("MOVIE_ID");

        String url = siteUrl + movieID + apiKey;
        String videoURL = siteUrl + movieID + REVIEWS + apiKey;
        String reviewsULR = siteUrl + movieID + VIDEOS + apiKey;

        // for url (old code as it was before)
        makeHttpRequest(url, new MoviesRequestInterface() {
            @Override
            public void OnDataAvailable() {
                updateDisplay();
            }
        });

        // for videoURL
        /* TODO to be adapted
        makeHttpRequest(videoURL, new MoviesRequestInterface() {
            @Override
            public void OnDataAvailable() {
                updateDisplay();
            }
        });*/

        // for reviews URL
        /* TODO to be adapted
        makeHttpRequest(reviewsULR, new MoviesRequestInterface() {
            @Override
            public void OnDataAvailable() {
                updateDisplay();
            }
        });*/
    }

    private void makeHttpRequest(String url, final MoviesRequestInterface callback) {
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.OnDataAvailable();
                                }
                            });
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

    private void updateDisplay() {
        mOriginalTitle.setText(mMovieReview.getOriginal_title());
        mOverView.setText(mMovieReview.getOverview());

        if (mMovieReview.getDate() != null){
            String formattedDate = DateFormat.getDateInstance().format(mMovieReview.getDate());
            mReleaseDate.setText(formattedDate);
        }

        Picasso.with(mPoster.getContext())
                .load("https://image.tmdb.org/t/p/w185" + mMovieReview.getPoster_path())
                .transform(new RoundedTransformation(5, 0))
                .error(R.mipmap.ic_launcher)
                .into(mPoster);

        mRatingBar.setRating((Integer) mMovieReview.getVote_average());
    }

    private MovieReview getMovieReviewJson(String jsonData) throws JSONException {
        JSONObject reviewJson = new JSONObject(jsonData);

        MovieReview aMovie = new MovieReview();

        aMovie.setOriginal_title(reviewJson.getString("original_title"));
        aMovie.setPoster_path(reviewJson.getString("poster_path"));
        aMovie.setOverview(reviewJson.getString("overview"));
        aMovie.setVote_average(reviewJson.getInt("vote_average"));
        aMovie.setDate(reviewJson.getString("release_date"));

        return aMovie;
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












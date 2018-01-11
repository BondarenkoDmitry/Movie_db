package com.dvb.movie_db.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvb.movie_db.Adapters.ReviewAdapter;
import com.dvb.movie_db.Adapters.VideoAdapter;
import com.dvb.movie_db.Helpers.AlertDialogFragment;
import com.dvb.movie_db.Models.MovieDetails;

import com.dvb.movie_db.Models.Review;
import com.dvb.movie_db.Models.Video;
import com.dvb.movie_db.R;
import com.dvb.movie_db.Helpers.RoundedTransformation;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dmitrybondarenko on 22.11.17.
 */

public class MovieReviewActivity extends AppCompatActivity {



    interface MoviesRequestInterface {
        void onDataAvailable();
        void parseData(String jsonData) throws JSONException;
    }

    private static final String TAG = MovieReviewActivity.class.getSimpleName();
    private MovieDetails mMovieDetails;

    RecyclerView miRecyclerView;
    RecyclerView.Adapter miAdapter;
    RecyclerView.LayoutManager miLayoutManger;

    RecyclerView myRecyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myLayoutManger;

    private ArrayList<Review> myReviews = new ArrayList<>();
    private ArrayList<Video> miVideos = new ArrayList<>();

    @InjectView(R.id.mrOriginalTitle)TextView mOriginalTitle;
    @InjectView(R.id.mrOverview)TextView mOverView;
    @InjectView(R.id.mrReleaseDate) TextView mReleaseDate;
    @InjectView(R.id.mrPoster)ImageView mPoster;
    @InjectView(R.id.mrRating) RatingBar mRatingBar;
    @InjectView(R.id.mrFavB) Button mFavButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_main_movie);
        ButterKnife.inject(this);


        miRecyclerView = (RecyclerView) findViewById(R.id.video_recycler_view);
        miAdapter = new VideoAdapter(miVideos);
        miRecyclerView.setHasFixedSize(true);
        miLayoutManger = new LinearLayoutManager(this);
        miRecyclerView.setLayoutManager(miLayoutManger);
        miRecyclerView.setAdapter(miAdapter);

        myRecyclerView = (RecyclerView) findViewById(R.id.review_recycler_view);
        myAdapter = new ReviewAdapter(myReviews);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManger = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManger);
        myRecyclerView.setAdapter(myAdapter);


        String apiKey = "?api_key=957c988676c0d274a6d1cc76dd5c8a93";
        String siteUrl = "https://api.themoviedb.org/3/movie/";
        String REVIEWS = "/reviews";
        String VIDEOS = "/videos";

        int movieID = getIntent()
                .getExtras()
                .getInt("MOVIE_ID");

        String url = siteUrl + movieID + apiKey;
        String videoURL = siteUrl + movieID + VIDEOS + apiKey;
        String reviewsULR = siteUrl + movieID + REVIEWS + apiKey;


        makeHttpRequest(url, new MoviesRequestInterface() {
            @Override
            public void onDataAvailable() {
                updateDisplay();
            }

            @Override
            public void parseData(String jsonData) throws JSONException {
                mMovieDetails = getMovieReviewJson(jsonData);
            }
        });


//         for videoURL
        makeHttpRequest(videoURL, new MoviesRequestInterface() {
            @Override
            public void onDataAvailable() {
            }

            @Override
            public void parseData(String jsonData) throws JSONException{
                getVideoJson(jsonData);
            }
        });

//         for reviews URL
        makeHttpRequest(reviewsULR, new MoviesRequestInterface() {
            @Override
            public void onDataAvailable() {
            }

            @Override
            public void parseData(String jsonData) throws JSONException {
                getReviewJson(jsonData);
            }
        });
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
                            callback.parseData(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onDataAvailable();
                                    myAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
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

    private void updateDisplay(){
        mOriginalTitle.setText(mMovieDetails.getOriginal_title());
        mOverView.setText(mMovieDetails.getOverview());

        if (mMovieDetails.getDate() != null){
            String formattedDate = DateFormat.getDateInstance().format(mMovieDetails.getDate());
            mReleaseDate.setText(formattedDate);
        }

        Picasso.with(mPoster.getContext())
                .load("https://image.tmdb.org/t/p/w185" + mMovieDetails.getPoster_path())
                .transform(new RoundedTransformation(5, 0))
                .error(R.mipmap.ic_launcher)
                .into(mPoster);

        mRatingBar.setRating((Integer) mMovieDetails.getVote_average());
    }

    private MovieDetails getMovieReviewJson(String jsonData) throws JSONException {
        JSONObject reviewJson = new JSONObject(jsonData);

        MovieDetails aMovie = new MovieDetails();

        aMovie.setOriginal_title(reviewJson.getString("original_title"));
        aMovie.setPoster_path(reviewJson.getString("poster_path"));
        aMovie.setOverview(reviewJson.getString("overview"));
        aMovie.setVote_average(reviewJson.getInt("vote_average"));
        aMovie.setDate(reviewJson.getString("release_date"));

        return aMovie;
    }


    private void getReviewJson(String jsonData) throws JSONException {
        JSONObject data = new JSONObject(jsonData);
        JSONArray reviews = data.getJSONArray("results");

        for (int i = 0; i < reviews.length(); i++){
            JSONObject review = reviews.getJSONObject(i);

            myReviews.add(new Review(
                    review.getString("author").toString(),
                    review.getString("content").toString()
            ));
        }
    }

    private void getVideoJson(String jsonData) throws JSONException {
        JSONObject data = new JSONObject(jsonData);
        JSONArray videos = data.getJSONArray("results");

        for (int i = 0; i < videos.length(); i++){
            JSONObject video = videos.getJSONObject(i);
            miVideos.add(new Video(
                    video.getString("site").toString(),
                    video.getString("key").toString()
            ));

        }
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


//    sending intent to watch Trailer on Youtube

//    @OnClick (R.id.mrFavB)
//    public void addToFavs(View view){
//        Intent intent = new Intent(this, YouTube);
//        intent.putExtra();
//        startActivity(intent);
//    }

}












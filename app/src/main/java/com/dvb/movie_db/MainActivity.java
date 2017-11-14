package com.dvb.movie_db;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private String TAG = MainActivity.class.getSimpleName();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    // ArrayList, I'm using it for adapter
    private ArrayList<Movie> mPopMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MovieAdapter(mPopMovies);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        new GetMovies().execute();
    }




    private class GetMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,
                    "Json Data is downloading", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String apiKey = "?api_key=957c988676c0d274a6d1cc76dd5c8a93";
            String siteUrl = "https://api.themoviedb.org/3/movie/";
            String sortBy = "popular";

            final String url = siteUrl + sortBy + apiKey;

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray movies = jsonObj.getJSONArray("results");

                    // looping through All Movies
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject jsonMovie = movies.getJSONObject(i);

                        String title = jsonMovie.getString("title");
                        String poster_path = jsonMovie.getString("poster_path");


//                         hash map for single movie
                        HashMap<String, String> movie = new HashMap<>();
                        // adding each child node to HashMap key => value
                        movie.put("title", title);
                        movie.put("poster_path", poster_path);

//                        // adding movie to movie list
//                        movieList.add(movie);
                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            //What shall we do after the data is here?
        }

    }
}


package com.dvb.movie_db.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dvb.movie_db.Adapters.MovieAdapter;
import com.dvb.movie_db.HttpHandler;
import com.dvb.movie_db.Model.Movie;
import com.dvb.movie_db.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String apiKey = "?api_key=957c988676c0d274a6d1cc76dd5c8a93";
    String siteUrl = "https://api.themoviedb.org/3/movie/";
    String popular = "popular";
    String upcoming = "upcoming";
    String topRated = "top_rated";



    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private String TAG = MainActivity.class.getSimpleName();
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

        new FetchMovies().execute(popular);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.popular_movies:
                // Clear before fetch
                this.mPopMovies.clear();
                this.mAdapter.notifyDataSetChanged();

                // Pass the part of the url to the async task

                new FetchMovies().execute(popular);
                return true;

            case R.id.upcoming_movies:
                this.mPopMovies.clear();
                this.mAdapter.notifyDataSetChanged();
       // Pass the part of the url to the async task

                new FetchMovies().execute(upcoming);
                return true;

            case R.id.top_rated:
                this.mPopMovies.clear();
                this.mAdapter.notifyDataSetChanged();

                new FetchMovies().execute(topRated);
                return true;

            case R.id.my_movies:
                this.mPopMovies.clear();
                this.mAdapter.notifyDataSetChanged();
                // Pass the part of the url to the async task
                new FetchMovies().execute(topRated);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchMovies extends AsyncTask<String, Object, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,
                    "Json Data is downloading", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... arg0) {
            HttpHandler sh = new HttpHandler();


            final String url = siteUrl + arg0[0] + apiKey;

            String jsonStr = sh.makeServiceCall(url);

            // Local list of movies
            ArrayList<Movie> popMovies = new ArrayList<>();

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
                        int id = jsonMovie.getInt("id");

                        // populate the local list in order to be pushed to post execute method
                        popMovies.add(new Movie(id, title, poster_path));

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

            return popMovies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result){
            super.onPostExecute(result);

            MainActivity.this.mPopMovies.addAll(result);
            MainActivity.this.mAdapter.notifyDataSetChanged();
        }

    }
}


package com.dvb.movie_db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dmitrybondarenko on 14.11.17.
 */

class MovieAdapter extends RecyclerView.Adapter
        <MovieAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Movie> mPopMovies = new ArrayList<Movie>();


    public MovieAdapter(ArrayList<Movie> arrayList){
        this.mPopMovies = arrayList;
    }


    // Is something missing here?

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Movie popMovie = mPopMovies.get(position);

        holder.title.setText(popMovie.getTitle());
        Picasso.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185" + popMovie.getPoster_path())
                .transform(new RoundedTransformation(5, 0))
                .error(R.mipmap.ic_launcher)
                .into(holder.poster_path);
    }



    @Override
    public int getItemCount() {
        if (mPopMovies == null){
            return 0;
        }
        return mPopMovies.size();
    }




    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView poster_path;
        TextView title;

        public RecyclerViewHolder(View view){
            super(view);

            poster_path = (ImageView)view.findViewById(R.id.img);
            title = (TextView)view.findViewById(R.id.f_name);

        }

        // Added OnClick method. But the toast doesn't show
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "You've clicked position: " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}

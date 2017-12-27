package com.dvb.movie_db.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvb.movie_db.Model.Review;
import com.dvb.movie_db.R;

import java.util.ArrayList;

/**
 * Created by dmitrybondarenko on 27.12.17.
 */

public class ReviewAdapter extends RecyclerView.Adapter
        <ReviewAdapter.RecyclerViewHolder>  {

    private Context context;
    private ArrayList<Review> myReviews = new ArrayList<>();


    public ReviewAdapter(ArrayList<Review> reviews) {
        this.myReviews = reviews;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Review aReview = myReviews.get(position);

        holder.author.setText(aReview.getAuthor());
        holder.content.setText(aReview.getContent());
    }


    @Override
    public int getItemCount() {
        if (myReviews == null){
            return 0;
        }
        return myReviews.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView author;
        TextView content;

        public RecyclerViewHolder(View view){
            super(view);

            author = (TextView)view.findViewById(R.id.idAuthor);
            content = (TextView)view.findViewById(R.id.idReview);
        }
    }
}

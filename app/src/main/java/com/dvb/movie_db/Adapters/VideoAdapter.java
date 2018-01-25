package com.dvb.movie_db.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvb.movie_db.Models.Video;
import com.dvb.movie_db.R;

import java.util.ArrayList;

import static android.R.attr.key;

/**
 * Created by dmitrybondarenko on 29.12.17.
 */

public class VideoAdapter extends RecyclerView.Adapter
        <VideoAdapter.RecyclerViewHolder>{

    private Context context;
    private ArrayList<Video> miVideos = new ArrayList<>();


    public VideoAdapter(ArrayList<Video> videos){
        this.miVideos = videos;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_video_item, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Video aVideo = miVideos.get(position);

        holder.site.setText(aVideo.getSite());
        holder.key.setText(aVideo.getKey());

        holder.site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + key));
                context.startActivity(webIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (miVideos == null){
            return 0;
        }
        return miVideos.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView site;
        TextView key;

        public RecyclerViewHolder(View view){
            super(view);

            site = (TextView)view.findViewById(R.id.idSite);
            key = (TextView)view.findViewById(R.id.idKey);
        }
    }
}




















































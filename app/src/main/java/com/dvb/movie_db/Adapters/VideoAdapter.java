package com.dvb.movie_db.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvb.movie_db.Models.Video;
import com.dvb.movie_db.R;

import java.util.ArrayList;

import static android.R.attr.content;
import static android.R.attr.id;
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
        final Video aVideo = miVideos.get(position);

        holder.site.setText(aVideo.getSite());
        holder.videoKey.setText(aVideo.getKey());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String videoKey = aVideo.getKey();
                String youtube = "https://www.youtube.com/watch?v=";


                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse(youtube + videoKey));
                view.getContext().startActivity(intent);

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

        private TextView site;
        private TextView videoKey;

        public RecyclerViewHolder(View view){
            super(view);

            site = (TextView)view.findViewById(R.id.idSite);
            videoKey = (TextView)view.findViewById(R.id.idKey);
        }
    }
}




















































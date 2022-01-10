package com.example.androidtrackserviceconsumer;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import datamodels.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Track> values;
        private Context context;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView txtHeader;
            public TextView txtFooter;
            public View layout;
            public ImageView trashImageView;


            public ViewHolder(View v) {
                super(v);
                layout = v;
                txtHeader = (TextView) v.findViewById(R.id.firstLine);
                txtFooter = (TextView) v.findViewById(R.id.secondLine);
                trashImageView = (ImageView) v.findViewById(R.id.erase);
            }
        }

        public void add(int position, Track item) {
            values.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            values.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, List<Track> myDataset) {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v =
                    inflater.inflate(R.layout.row_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final Track track = values.get(position);
            holder.txtHeader.setText(track.getTitle());
            holder.txtHeader.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TrackActivity.class);
                    intent.putExtra("track", track);
                    context.startActivity(intent);
                    Log.d("DEBUG", "plusButtonClick");
                }
            });

            holder.trashImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DEBUG", "Delete + " + track.getId());

                    Call<Void> call = MainActivity.trackService.deleteTrack(track.getId());
                    call.enqueue(new Callback<Void>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            Log.d("DEBUG", "onResponse");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                            Log.d("DEBUG", t.getMessage());
                        }
                    });
                }
            });

            holder.txtFooter.setText(track.getSinger());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return values.size();
        }


    }

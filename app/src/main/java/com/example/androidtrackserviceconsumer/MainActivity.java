package com.example.androidtrackserviceconsumer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import datamodels.Track;
import retrofit.TrackService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static Retrofit retrofit;
    public static TrackService trackService;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        retrofit= new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        trackService = retrofit.create(TrackService.class);



        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getTracks();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

      getTracks();


    }


    public void plusButtonClick(View view) {
        Intent intent = new Intent(context, TrackActivity.class);
        intent.putExtra("track", new Track());
        startActivity(intent);
        Log.d("DEBUG", "plusButtonClick");
    }

    public void getTracks(){
        List<String> input = new ArrayList<>();
        Call<List<Track>> call = trackService.listTracks();
        call.enqueue(new Callback<List<Track>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                mAdapter = new MyAdapter(context, response.body());
                recyclerView.setAdapter(mAdapter);
                Log.d("DEBUG", "onResponse");
            }



            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {



                Log.d("DEBUG", t.getMessage());
            }
        });
    }
}
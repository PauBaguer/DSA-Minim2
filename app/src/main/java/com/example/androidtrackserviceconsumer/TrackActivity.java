package com.example.androidtrackserviceconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;

import datamodels.GitHubUser;

public class TrackActivity extends AppCompatActivity {

    private TextView userNameText;
    private TextView followersText;
    private TextView followingText;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private GitHubUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        userNameText = findViewById(R.id.userNameText);
        followersText = findViewById(R.id.followersText);
        followingText = findViewById(R.id.followingText);


        user = (GitHubUser) getIntent().getSerializableExtra("user");

        if(user.getName() == null){
            userNameText.setText(user.getLogin());
        }else{
            userNameText.setText(user.getName());
        }


        followersText.setText(followersText.getText() + user.getFollowers().toString());
        followingText.setText(followingText.getText() + user.getFollowing().toString());


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

       swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

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


        //posar data a recycler

        mAdapter = new MyAdapter(user.getRepoHashmap());
        recyclerView.setAdapter(mAdapter);

    }


}
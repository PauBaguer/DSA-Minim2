package com.example.androidtrackserviceconsumer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import datamodels.Track;
import retrofit.TrackService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrackActivity extends AppCompatActivity {

    Track track;
    TextInputLayout trackNameInput;
    TextInputLayout authorInput;
    Button button;
    Boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        trackNameInput = findViewById(R.id.TrackNameInput);
        authorInput = findViewById(R.id.AuthorInput);
        button = findViewById(R.id.button);

        track = (Track) getIntent().getSerializableExtra("track");

        if(track.getTitle()!= null && track.getSinger() != null){
            authorInput.getEditText().setText(track.getSinger());
            trackNameInput.getEditText().setText(track.getTitle());

            button.setText("Edit Track");
            edit = true;
        }
    }

    public void addTrackBtnClick(View view) {

        if(edit){
            this.track.setTitle(trackNameInput.getEditText().getText().toString());
            this.track.setSinger(authorInput.getEditText().getText().toString());
            Call<Void> call = MainActivity.trackService.updateTrack(this.track);
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

        }else {
            Track track = new Track(trackNameInput.getEditText().getText().toString(), authorInput.getEditText().getText().toString());
            Call<Void> call = MainActivity.trackService.createTrack(track);
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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
package com.example.androidtrackserviceconsumer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import datamodels.GitHubUser;
import datamodels.Track;
import retrofit.GitHubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    TextInputLayout userInput;
    GitHubUser gitHubUser;
    ProgressBar progressBar;
    TextView errorText;




    public static Retrofit retrofit;
    public static GitHubService gitHubService;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.userInput);
        progressBar= findViewById(R.id.progressBar);
        errorText = findViewById(R.id.errorText);


        context = this;

        retrofit= new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubService = retrofit.create(GitHubService.class);



      //  authorInput.getEditText().setText(track.getSinger());



    }




    public void getFromAPI(){
        progressBar.setVisibility(View.VISIBLE);

        Call<Object> call = gitHubService.getUser(userInput.getEditText().getText().toString());
        call.enqueue(new Callback<Object>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                LinkedTreeMap<String, Object> body = (LinkedTreeMap) response.body();

                if(response.isSuccessful()){


                    gitHubUser = new GitHubUser((String) body.get("login"),
                            (String) body.get("repos_url"), (String) body.get("name"),
                            (int)  Math.round((Double) body.get("followers")),
                            (int) Math.round((Double) body.get("following")));


                    getRepos();
                    Log.d("DEBUG", "onResponse");
                }else {
                    progressBar.setVisibility(View.GONE);

                    try {
                        errorText.setText(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    errorText.setVisibility(View.VISIBLE);
                }

            }



            @Override
            public void onFailure(Call<Object> call, Throwable t) {


                progressBar.setVisibility(View.GONE);
                errorText.setText(t.getMessage());
                errorText.setVisibility(View.VISIBLE);
                Log.d("DEBUG", t.getMessage());
            }
        });
    }

    public void getRepos(){

        Call<List<Object>> call = gitHubService.getRepos(userInput.getEditText().getText().toString());
        call.enqueue(new Callback<List<Object>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {

                List<LinkedTreeMap<String, Object>> body = new LinkedList<>();

                if(response.isSuccessful()){
                    response.body().forEach(el -> body.add((LinkedTreeMap) el));

                    HashMap<String, String> hashMap = new HashMap<>();
                    body.forEach(el -> hashMap.put((String) el.get("name"), (String) el.get("language")));

                    gitHubUser.setRepoHashmap(hashMap);
                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent(context, TrackActivity.class);
                    intent.putExtra("user", gitHubUser);
                    startActivity(intent);

                }else {
                    progressBar.setVisibility(View.GONE);

                    try {
                        errorText.setText(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    errorText.setVisibility(View.VISIBLE);
                }

                Log.d("DEBUG", "onResponse");
            }



            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                errorText.setText(t.getMessage());
                errorText.setVisibility(View.VISIBLE);

                Log.d("DEBUG", t.getMessage());
            }
        });
    }

    public void sendBtnClick(View view) {
        getFromAPI();
    }
}
package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etMovieShow;
    private TextView tvDisplayMessage;
    private List<Search> movies;
    private String searchResult;

    private final String BASE_URL = "http://www.omdbapi.com/?s=";
    private final String API_KEY = "&apikey=76baea73";
    String completeURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.rvListView);
        Button btnSearch = findViewById(R.id.btnSeacrh);
        tvDisplayMessage = findViewById(R.id.tvDisplayMessage);
        etMovieShow = findViewById(R.id.etSearch);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        btnSearch.setOnClickListener(v -> {
            searchResult = etMovieShow.getText().toString().trim();
            completeURL = BASE_URL.concat(searchResult).concat(API_KEY);
            retrofitMethod(mRecyclerView);
        });
    }

    /**
     * method to get the url details
     */
    private void retrofitMethod(RecyclerView mRecyclerView) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(completeURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface request = retrofit.create(APIInterface.class);
        Call<Movies> call = request.getJson(completeURL);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                tvDisplayMessage.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                if (response.body().getSearch() != null) {
                    movies = new ArrayList<>(response.body().getSearch());
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), movies);
                    mRecyclerView.setAdapter(recyclerViewAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "No movies/show to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
            }
        });
    }
}
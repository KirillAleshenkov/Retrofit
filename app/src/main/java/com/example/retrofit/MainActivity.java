package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    String location = "орск";
    String apikey = "3c534d66146fef4ce809fdb84abfb225";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://engine.hotellook.com/api/v2/static/locations.json?token=3c534d66146fef4ce809fdb84abfb225")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getCities();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> cities = response.body();
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> cities = response.body();
                for (int i = 0; i < 10; i++){
                    for (Post post: cities){
                    String content = "";
                    content+="Name: "+post.getName() + "\n";
                    content+="Longitude: "+post.getLongitude() + "\n";
                    content+="Latitude: "+post.getLatitude() +"\n";
                    textViewResult.append(content);
                }
            }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
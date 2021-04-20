package com.example.scrollablestarwarsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.scrollablestarwarsapi.model.Result;
import com.example.scrollablestarwarsapi.model.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClickListener {
    PersonAdapter adapter;
    /*This is a hack and definitely isn't best practice.  This gives the context to the
    * toasts I have setup for errors as well as where I create the adapter and click listener.
    * */
    Context context;
    List<Person> persons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //retrofit setup with base url

        //Ordinarily this would be done through a service layer but

        //I'm not demonstrating the most correct architecture.

        /*I followed documentation from several youtube videos and stack Overflow
         *This seems to be the run-of-the-mill pattern for a demo recyclerview
         *I chose retrofit because it was dead simple and seems to be the most popular
         *method of interfacing with REST services in the android ecosystem.
         *Also abstracts away thread management for network calls
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StarWarsApi starWarsApi = retrofit.create(StarWarsApi.class);

        Call<Result> call = starWarsApi.getPersons();
        //Send call to network thread, uses retrofit's enqueue method to accomplish this.
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Code" + response.code() , Toast.LENGTH_SHORT).show();
                }
                Result result = response.body();
                //create adapter, pass in context and our data
                adapter = new PersonAdapter(context, result.getPersons());
                //connect click listener
                adapter.setClickListener((PersonAdapter.ItemClickListener) context);
                //connect adapter
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(context, t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Eye Color: " + adapter.getItem(position).getEyeColor(), Toast.LENGTH_SHORT).show();
    }

}
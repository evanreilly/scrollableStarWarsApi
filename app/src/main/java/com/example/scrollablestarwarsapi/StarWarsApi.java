package com.example.scrollablestarwarsapi;

import com.example.scrollablestarwarsapi.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StarWarsApi {

    @GET("people")
    Call<Result> getPersons();
}

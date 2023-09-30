package com.example.lab3_iot;

import com.example.lab3_iot.dto.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomUser {
    @GET("/api/")
    Call<List<Result>> getResults();

}

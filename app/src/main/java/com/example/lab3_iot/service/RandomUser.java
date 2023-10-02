package com.example.lab3_iot.service;

import com.example.lab3_iot.dto.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomUser {
    @GET("/api/")
    Call<com.example.lab3_iot.dto.RandomUser> getResults();

}

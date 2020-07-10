package com.example.covid19status.Interfaces;

import com.example.covid19status.Entidades.Provincia;
import com.example.covid19status.Responses.ProvinciaResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPeticiones {

    @GET("status/latest/provincias/{id}")
    Call<ProvinciaResponse> getProvinciaId(@Path("id") String groupId);




}

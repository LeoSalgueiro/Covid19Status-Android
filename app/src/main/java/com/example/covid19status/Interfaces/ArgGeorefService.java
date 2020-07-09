package com.example.covid19status.Interfaces;

import com.example.covid19status.Responses.UbicacionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArgGeorefService {
    @GET("georef/api/ubicacion")
    Call<UbicacionResponse> enriquecerUbicacion(@Query("lat") double lat, @Query("lon") double lon);
}

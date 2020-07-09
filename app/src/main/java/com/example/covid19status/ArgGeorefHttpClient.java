package com.example.covid19status;

import com.example.covid19status.Interfaces.ArgGeorefService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//ESTE ES EL ADAPTER
public class ArgGeorefHttpClient {
    private static ArgGeorefService API_SERVICE;

    public static ArgGeorefService getGeorefService() {

        // Creamos un interceptor y le indicamos el log level a usar //SIRRVE PARA DEBUG
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseUrl = "https://apis.datos.gob.ar/";

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(ArgGeorefService.class);
        }

        return API_SERVICE;
    }
}

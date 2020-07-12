package com.example.covid19status.JobServices;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.covid19status.ApiClient;
import com.example.covid19status.Database.UbicacionUsuarioDatabase;
import com.example.covid19status.EntidadesDB.UbicacionUsuario;
import com.example.covid19status.R;
import com.example.covid19status.Responses.ProvinciaResponse;

import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionJobService extends JobService {
    private static final String TAG = "ConsultaProv JobService";
    public static final String CHANNEL_ID = "Notificaciones";
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob" );
        getUltimaUbicacionDelUsuario();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob" );

        return false;
    }



    // implementar lo siguente en el lugar donde se quiera recuperar la ultima ubicacion del usuario
    public void getUltimaUbicacionDelUsuario(){
         new Thread(new Runnable() {
            @Override
            public void run() {
                UbicacionUsuario ultimaUbicacionUsuario = UbicacionUsuarioDatabase.getInstance(getApplicationContext()).ubicacionUsuarioDao().selectUltimaUbicacionDelUsuario();
                if(ultimaUbicacionUsuario != null){
                    Log.d(TAG, "run: " + ultimaUbicacionUsuario.toString());
                    // consultar api con provincia dada
                    Call<ProvinciaResponse> call = ApiClient.getApiService().getProvinciaId(ultimaUbicacionUsuario.getProvinciaId());
                    call.enqueue(new  Callback<ProvinciaResponse>() {
                        @Override
                        public void onResponse(Call call, @NonNull Response response) {
                            if(response.isSuccessful()){
                                ProvinciaResponse respuesta = (ProvinciaResponse) response.body();
                                Log.v("detalle "," "+ respuesta.toString());

                                // push notif
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication().getBaseContext(), CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_menu_world_simple)
                                        .setContentTitle("COVID19 Status")
                                        .setContentText(respuesta.toString())
                                        .setStyle(new NotificationCompat.BigTextStyle()
                                                .bigText(respuesta.toString()))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                                // notificationId is a unique int for each notification that you must define
                                notificationManager.notify(ThreadLocalRandom.current().nextInt(1, 9999 + 1), builder.build());
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.v("toastnot ", "falle");
                        }
                    });

                } else {
                    Log.d(TAG, "run: returned null");
                }
            }
        }).start();
    }
}

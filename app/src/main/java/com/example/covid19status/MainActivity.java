package com.example.covid19status;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.covid19status.Database.UbicacionUsuarioDatabase;
import com.example.covid19status.EntidadesDB.UbicacionUsuario;
import com.example.covid19status.Interfaces.IComunicaFragment;
import com.example.covid19status.JobServices.NotificacionJobService;
import com.example.covid19status.Responses.ProvinciaResponse;
import com.example.covid19status.Responses.UbicacionResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.covid19status.JobServices.NotificacionJobService.CHANNEL_ID;

public class MainActivity extends AppCompatActivity implements IComunicaFragment{

    //master detail
    InfoActualFragment master;
    InfoActualDetalleFragment detalle;

    // log tag
    public static final String TAG = "MainActivity";

    // ubicacion
    private FusedLocationProviderClient fusedLocationClient;
    int LOCATION_REQUEST_CODE = 1337;
    public static final int ID_SERVICIO = 123;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);//esto es para que los iconos se vean bien
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.inicioApp,
                 R.id.nav_info_actual, R.id.nav_instrucciones, R.id.nav_internacional, R.id.nav_sobre_nosotros)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createNotificationChannel();
        registerJob();
        //master = new InfoActualFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout,master).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLastLocation();
        } else {
            askLocationPermission();
        }
   }

    private void getLastLocation(){
        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    // tenemos la posicion
                    Log.d(TAG, "onSuccess" + location.toString());
                    Log.d(TAG, "onSuccess latitude" + location.getLatitude());//-27.2741;-66.7529
                    Log.d(TAG, "onSuccess longitude" + location.getLongitude());

                    Call<UbicacionResponse> call = ArgGeorefHttpClient.getGeorefService().enriquecerUbicacion(-27.8059487, -64.3357153); // catamarca // location.getLatitude(), location.getLongitude()
                    call.enqueue(new Callback<UbicacionResponse>() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if(response.isSuccessful()){
                                UbicacionResponse ubicacionResponse = (UbicacionResponse) response.body();
                                Log.d(TAG, "Body: " + ubicacionResponse.getUbicacion().getProvincia().getNombre());

                                // escribir en BD
                                UbicacionUsuario ubicacionUsuario = new UbicacionUsuario(ubicacionResponse.getUbicacion().getProvincia().getId(),
                                        ubicacionResponse.getUbicacion().getProvincia().getNombre(),
                                        ubicacionResponse.getUbicacion().getDepartamento().getId(),
                                        ubicacionResponse.getUbicacion().getDepartamento().getNombre());

                                InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
                                insertAsyncTask.execute(ubicacionUsuario);
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d(TAG, "Error: " +  t.getLocalizedMessage());
                        }
                    });
                } else {
                    Log.d(TAG, "onSuccess location was null");
                }
            }
        });
    }

    private void askLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "mostrar dialog");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // permission granted
                getLastLocation();
            } else {
                // permision dont granted
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        Log.v("boton","Es este id: "+ id);
        if(id == R.id.share){
            Intent intent = new Intent (Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, Uri.parse(getString(R.string.shareapp_message)+"http://play.google.com/store/apps/details?id="+ getPackageName()).toString());
            startActivity(Intent.createChooser(intent, "CompartiendoApp"));
        }
        if(id==16908332){// este es el id del submenu, si no esta esto, no funca el boton
            return item.hasSubMenu();
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void enviarProvincia(ProvinciaResponse provincia) {
        InfoActualDetalleFragment detalleFragment = new InfoActualDetalleFragment();
        Bundle bundleEnviar = new Bundle();
        bundleEnviar.putSerializable("detalleProvincia", provincia);
        detalleFragment.setArguments(bundleEnviar);

        //cargo el detalle

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer_layout,detalleFragment)
                .addToBackStack(null)
                .commit();
    }

    class InsertAsyncTask extends AsyncTask<UbicacionUsuario, Void, Void> {

        @Override
        protected Void doInBackground(UbicacionUsuario... ubicacionUsuarios) {

            UbicacionUsuarioDatabase.getInstance(getApplicationContext()).ubicacionUsuarioDao().insertUbicacionUsuario(ubicacionUsuarios[0]);

            return null;
        }
    }

    // implementar lo siguente en el lugar donde se quiera recuperar la ultima ubicacion del usuario
    public void getUltimaUbicacionDelUsuario(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UbicacionUsuario ultimaUbicacionUsuario = UbicacionUsuarioDatabase.getInstance(getApplicationContext()).ubicacionUsuarioDao().selectUltimaUbicacionDelUsuario();
                if(ultimaUbicacionUsuario != null){
                    Log.d(TAG, "run: " + ultimaUbicacionUsuario.toString());
                } else {
                    Log.d(TAG, "run: returned null");
                }
            }
        });
        thread.start();
    }

    private void registerJob() {
        ComponentName serviceName = new ComponentName(getApplicationContext(), NotificacionJobService.class);
        JobInfo jobInfo;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            jobInfo = new JobInfo.Builder(ID_SERVICIO, serviceName)
                    .setPeriodic(900000)
                    .build();
        }else{
            jobInfo = new JobInfo.Builder(ID_SERVICIO, serviceName)
                    .setPeriodic(1000)
                    .build();
        }
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultado = jobScheduler.schedule(jobInfo);
        if(resultado == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job schedule result: success");
        } else {
            Log.d(TAG, "Job schedule result: error");
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

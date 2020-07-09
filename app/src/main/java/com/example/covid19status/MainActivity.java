package com.example.covid19status;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

import com.example.covid19status.Entidades.Provincia;
import com.example.covid19status.Interfaces.IComunicaFragment;
import com.example.covid19status.Responses.UbicacionResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IComunicaFragment {
    // log tag
    public static final String TAG = "MainActivity";

    // ubicacion
    private FusedLocationProviderClient fusedLocationClient;
    int LOCATION_REQUEST_CODE = 1337;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);//esto es para que los iconos se vean bien
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_info_actual, R.id.nav_instrucciones, R.id.nav_internacional, R.id.nav_sobre_nosotros)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
                    Log.d(TAG, "onSuccess latitude" + location.getLatitude());
                    Log.d(TAG, "onSuccess longitude" + location.getLongitude());

                    Call<UbicacionResponse> call = ArgGeorefHttpClient.getGeorefService().enriquecerUbicacion(-27.2741, -66.7529); // catamarca
                    call.enqueue(new Callback<UbicacionResponse>() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if(response.isSuccessful()){
                                UbicacionResponse ubicacionResponse = (UbicacionResponse) response.body();
                                Log.d(TAG, "Body: " + ubicacionResponse.getUbicacion().getProvincia().getNombre());
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
            intent.putExtra(Intent.EXTRA_TEXT, Uri.parse("Comparte Covid-19 Status con tu amigos con el siguiente link: "+"http://play.google.com/store/apps/details?id="+ getPackageName()).toString());
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
    public void enviarProvincia(Provincia provincia) {
        //DetalleFragment = new DEtalleProvincia
    }
}

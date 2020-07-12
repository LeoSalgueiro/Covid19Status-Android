package com.example.covid19status;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.util.Log;

import com.example.covid19status.Database.UsuarioDatabase;
import com.example.covid19status.EntidadesDB.Usuario;

public class IntermedioInicioActivity extends AppCompatActivity {
    private boolean existe = true;
    public static FragmentManager fragmentManager;
    public static UsuarioDatabase usuarioDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        fragmentManager = getSupportFragmentManager();
        usuarioDatabase = Room.databaseBuilder(getApplicationContext(),UsuarioDatabase.class,"userdb").allowMainThreadQueries().build();

        setContentView(R.layout.activity_intermedio_inicio);

        if(findViewById(R.id.fragment_container_intermedio)!=null){

            Usuario [] lista = usuarioDatabase.mi_user_dao().cargarTodosLosUsuarios();
            if(lista.length > 0){
                String nom = lista[0].getNombre();
                Log.v("tagtag", nom);
                fragmentManager.beginTransaction().add(R.id.fragment_container_intermedio,new IntermedioOtraVezFragment()).commit();
            }else {
                fragmentManager.beginTransaction().add(R.id.fragment_container_intermedio,new IntermedioPrimeraVezFragment()).commit();
            }

            /*
            if(savedInstanceState!= null){

                return;
            }*/
            //aca deberia de elegir que fragment poner, primero debo consultar a la db




        }


    }
}

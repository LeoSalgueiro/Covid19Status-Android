package com.example.covid19status.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.covid19status.EntidadesDB.UbicacionUsuario;

@Database(entities = {UbicacionUsuario.class}, version = 1)
public abstract class UbicacionUsuarioDatabase extends RoomDatabase {
    public abstract UbicacionUsuarioDao ubicacionUsuarioDao();

    private static volatile UbicacionUsuarioDatabase INSTANCE;

    public static UbicacionUsuarioDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (UbicacionUsuarioDatabase.class){
                if(INSTANCE ==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UbicacionUsuarioDatabase.class, "ubicacion_db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}

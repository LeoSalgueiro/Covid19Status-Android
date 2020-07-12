package com.example.covid19status.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.covid19status.EntidadesDB.Usuario;

@Database(entities = {Usuario.class}, version = 1)
public abstract class UsuarioDatabase extends RoomDatabase {

    public abstract UsuarioDao mi_user_dao();
}

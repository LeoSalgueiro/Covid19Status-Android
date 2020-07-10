package com.example.covid19status.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.covid19status.EntidadesDB.UbicacionUsuario;

@Dao
public interface UbicacionUsuarioDao {
    @Insert
    void insertUbicacionUsuario(UbicacionUsuario ubicacionUsuario);

    @Query("SELECT * FROM ubicacion_table ORDER BY createdAt DESC LIMIT 1")
    UbicacionUsuario selectUltimaUbicacionDelUsuario();
}

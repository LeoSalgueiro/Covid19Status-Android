package com.example.covid19status.EntidadesDB;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "datos_usuario_table")
public class Usuario {

    @PrimaryKey @NonNull
    @ColumnInfo(name = "usuario_nombre")
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}

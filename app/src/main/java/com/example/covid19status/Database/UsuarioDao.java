package com.example.covid19status.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.covid19status.EntidadesDB.Usuario;

import retrofit2.http.GET;

@Dao
public interface UsuarioDao {

    @Insert
    public void agregarUsuario(Usuario usuario);

    @Query("SELECT * FROM datos_usuario_table")
    public Usuario[] cargarTodosLosUsuarios();



}

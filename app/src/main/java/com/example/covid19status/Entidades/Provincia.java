package com.example.covid19status.Entidades;

import java.io.Serializable;

public class Provincia implements Serializable {

    private String nombre;
    private String descripcion;
    private int idProvincia;
    private int imagenid;

    //atributos para el detalle (para no modificar los constructores le hardcodeo la info, total es de prueba nomas)
    private String detalle = "Soy un detalle de prueba, soy un detalle de prueba";
    private int imagenDetalle = imagenid;


    public Provincia(){}

    public Provincia(String nombre, String descripcion, int idProvincia, int imagenid){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idProvincia = idProvincia;
        this.imagenid = imagenid;

    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public int getImagenid() {
        return imagenid;
    }

}

package com.example.covid19status.Entidades;

import java.io.Serializable;

public class Provincia implements Serializable {

    private String nombre;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String descripcion;
    private String idProvincia;
    private int imagenid;

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setImagenDetalle(int imagenDetalle) {
        this.imagenDetalle = imagenDetalle;
    }

    //atributos para el detalle (para no modificar los constructores le hardcodeo la info, total es de prueba nomas)
    private String detalle = "Soy un detalle de prueba, soy un detalle de prueba";
    private int imagenDetalle = imagenid;


    public Provincia(){}

    public Provincia(String nombre, String descripcion, String idProvincia, int imagenid){
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

    public String getIdProvincia() {
        return idProvincia;
    }

    public int getImagenid() {
        return imagenid;
    }

}

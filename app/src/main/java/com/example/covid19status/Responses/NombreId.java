package com.example.covid19status.Responses;

public class NombreId {
    private String id;
    private String nombre;

    public String getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "NombreId{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

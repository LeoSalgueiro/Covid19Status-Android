package com.example.covid19status.Responses;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProvinciaResponse implements Serializable {

    private String TerritorioID;
    private String TerritorioNombre;
    private String TerritorioTipo;
    private String Fecha;
    private Object Confirmados;
    private Object Muertes;

    public String getTerritorioID() {
        return TerritorioID;
    }

    public void setTerritorioID(String TerritorioID) {
        this.TerritorioID = TerritorioID;
    }

    public String getTerritorioNombre() {
        return TerritorioNombre;
    }

    public void setTerritorioNombre(String TerritorioNombre) {
        this.TerritorioNombre = TerritorioNombre;
    }

    public String getTerritorioTipo() {
        return TerritorioTipo;
    }

    public void setTerritorioTipo(String TerritorioTipo) {
        this.TerritorioTipo = TerritorioTipo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public Object getConfirmados() {
        return Confirmados;
    }

    public void setConfirmados(Object Confirmados) {
        this.Confirmados = Confirmados;
    }

    public Object getMuertes() {
        return Muertes;
    }

    public void setMuertes(Object Muertes) {
        this.Muertes = Muertes;
    }
}

package com.example.covid19status.Responses;

public class Ubicacion {
    private NombreId departamento;
    private double lat;
    private double lon;
    private NombreId provincia;

    public double getLat() {
        return lat;
    }
    public double getLon() {
        return lon;
    }
    public NombreId getDepartamento() {
        return departamento;
    }
    public NombreId getProvincia() {
        return provincia;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "departamento=" + departamento.toString() +
                ", lat=" + lat +
                ", lon=" + lon +
                ", provincia=" + provincia.toString() +
                '}';
    }
}

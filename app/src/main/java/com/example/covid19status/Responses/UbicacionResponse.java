package com.example.covid19status.Responses;

public class UbicacionResponse {
    private Ubicacion ubicacion;

    @Override
    public String toString() {
        return "UbicacionResponse{" +
                "ubicacion=" + ubicacion.toString() +
                '}';
    }

    public Ubicacion getUbicacion() {
        return ubicacion;

    }
}


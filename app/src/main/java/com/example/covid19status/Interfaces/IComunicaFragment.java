package com.example.covid19status.Interfaces;

import com.example.covid19status.Entidades.Provincia;
import com.example.covid19status.Responses.ProvinciaResponse;

public interface IComunicaFragment {

    public void enviarProvincia(ProvinciaResponse provincia);
}

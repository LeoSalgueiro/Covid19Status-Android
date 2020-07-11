package com.example.covid19status;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid19status.Responses.ProvinciaResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoActualDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoActualDetalleFragment extends Fragment {

    TextView nombreProvincia;
    TextView confirmadosNuevos;
    TextView confirmadosTotal;
    TextView muertosNuevos;
    TextView muertosTotal;
    TextView fecha;



    public InfoActualDetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoActualDetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoActualDetalleFragment newInstance(String param1, String param2) {
        InfoActualDetalleFragment fragment = new InfoActualDetalleFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_info_actual_detalle, container, false);
        nombreProvincia = vista.findViewById(R.id.detalleNombreProvincia);
        confirmadosNuevos = vista.findViewById(R.id.detalleConfirmadosNuevos);
        confirmadosTotal = vista.findViewById(R.id.detalleConfirmadosTotal);
        muertosNuevos = vista.findViewById(R.id.detalleMuertesNuevos);
        muertosTotal = vista.findViewById(R.id.detalleMuertesTotal);
        fecha = vista.findViewById(R.id.ultimaActualizacion);
        
        Bundle objetoRecibido = getArguments();
        ProvinciaResponse provinciaRecibido = null;

        if(objetoRecibido != null){

            provinciaRecibido = (ProvinciaResponse) objetoRecibido.getSerializable("detalleProvincia");
            Map confirmadosverdadero = (Map)provinciaRecibido.getConfirmados();
            Map muertesverdadero = (Map)provinciaRecibido.getMuertes();

            String confNuevos =  confirmadosverdadero.get("Nuevos").toString().split("\\.")[0];
            String confTotal = confirmadosverdadero.get("Total").toString().split("\\.")[0];
            String muerNuevos = muertesverdadero.get("Nuevos").toString().split("\\.")[0];
            String muerTotal =  muertesverdadero.get("Total").toString().split("\\.")[0];


            String [] date = provinciaRecibido.getFecha().split("-");
            String fechaNueva = date[2]+"/"+date[1]+"/"+date[0];
            Log.v("tagtag",date[2]+"/"+date[1]+"/"+date[0]);

            nombreProvincia.setText(provinciaRecibido.getTerritorioNombre());
            confirmadosNuevos.setText("Nuevos: "+confNuevos);
            confirmadosTotal.setText("Total: "+confTotal);
            muertosNuevos.setText("Nuevas: "+muerNuevos);
            muertosTotal.setText("Total: "+muerTotal);
            fecha.setText(fechaNueva);

        }
        return vista;
    }
}

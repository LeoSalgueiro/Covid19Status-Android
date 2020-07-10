package com.example.covid19status;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid19status.Responses.ProvinciaResponse;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoActualDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoActualDetalleFragment extends Fragment {

    TextView nombreProvincia;
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
        nombreProvincia = vista.findViewById(R.id.textViewDetalle);
        Bundle objetoRecibido = getArguments();
        ProvinciaResponse provinciaRecibido = null;

        if(objetoRecibido != null){

            provinciaRecibido = (ProvinciaResponse) objetoRecibido.getSerializable("detalleProvincia");
            Log.v("hayobjeto", ""+provinciaRecibido.getTerritorioNombre());
            nombreProvincia.setText(provinciaRecibido.getTerritorioNombre());

        }
        return vista;
    }
}

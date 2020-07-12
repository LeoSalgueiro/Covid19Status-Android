package com.example.covid19status;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covid19status.EntidadesDB.Usuario;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntermedioPrimeraVezFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntermedioPrimeraVezFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextView nombreUsuario;
    private Button btnGuardar;

    public IntermedioPrimeraVezFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntermedioPrimeraVezFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntermedioPrimeraVezFragment newInstance(String param1, String param2) {
        IntermedioPrimeraVezFragment fragment = new IntermedioPrimeraVezFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_intermedio_primera_vez, container, false);


        nombreUsuario = view.findViewById(R.id.idInicioNombreInput);
        btnGuardar = view.findViewById(R.id.inicioPrimeraVezBtnGuardarNombre);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreFinal = nombreUsuario.getText().toString();
                Usuario usuario = new Usuario();
                usuario.setNombre(nombreFinal);

                //guardo el usuario
                IntermedioInicioActivity.usuarioDatabase.mi_user_dao().agregarUsuario(usuario);

                //realizar intent para cambiar de activity

                IntermedioInicioActivity inter = (IntermedioInicioActivity) getActivity();
                Intent intent = new Intent(inter, MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}

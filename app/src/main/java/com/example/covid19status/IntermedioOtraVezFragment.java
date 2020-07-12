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
 * Use the {@link IntermedioOtraVezFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntermedioOtraVezFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button siguiente;
    TextView nombre;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntermedioOtraVezFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntermedioOtraVezFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntermedioOtraVezFragment newInstance(String param1, String param2) {
        IntermedioOtraVezFragment fragment = new IntermedioOtraVezFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_intermedio_otra_vez, container, false);
        nombre = view.findViewById(R.id.inicioNombreOtraVez);

        //hago la conulta de nuevo porque no se como pasar el nobre por parametro
        Usuario[] lista = IntermedioInicioActivity.usuarioDatabase.mi_user_dao().cargarTodosLosUsuarios();
        String nom = lista[0].getNombre();
        nombre.setText(nom);


        siguiente = view.findViewById(R.id.inicio_btn_siguiente);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntermedioInicioActivity inter = (IntermedioInicioActivity) getActivity();
                Intent intent = new Intent(inter, MainActivity.class);
                startActivity(intent);
                inter.finish();//no se si me finaliza la bd, pero para una primera instancia esta bien


            }
        });
        return view;
    }
}

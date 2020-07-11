package com.example.covid19status;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.covid19status.Entidades.Provincia;
import com.example.covid19status.Interfaces.IComunicaFragment;
import com.example.covid19status.Interfaces.IPeticiones;
import com.example.covid19status.Responses.ProvinciaResponse;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoActualFragment extends Fragment {

    private ArrayList<Provincia> listaProvincias;
    private AdapterProvincias adapter;
    private RecyclerView recycler;
    private View vista;

    private InfoActualDetalleFragment detalleFragment;


    private Activity activity;
    private IComunicaFragment interfaceComunicacionEntreFragment;

    public InfoActualFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_info_actual, container, false);

        recycler=vista.findViewById(R.id.recyclerId);
        listaProvincias = new ArrayList<>();
        //cargar lista
        cargarLista();
        //mostrar data
        mostrarData();


        return vista;
    }

    private void cargarLista() {
        listaProvincias.add(new Provincia("Buenos Aires", "","06", R.drawable.logo_buenosaires));
        listaProvincias.add(new Provincia("CABA", "","02", R.drawable.logo_caba));
        listaProvincias.add(new Provincia("Catamarca", "","10", R.drawable.logo_catamarca));
        listaProvincias.add(new Provincia("Córdoba", "","14", R.drawable.logo_cordoba));
        listaProvincias.add(new Provincia("Corrientes", "","18", R.drawable.logo_corrientes));
        listaProvincias.add(new Provincia("Chaco", "","22", R.drawable.logo_chaco));
        listaProvincias.add(new Provincia("Chubut", "","26", R.drawable.logo_chubut));
        listaProvincias.add(new Provincia("Entre Rios", "","30", R.drawable.logo_entrerios));
        listaProvincias.add(new Provincia("Formosa", "","34", R.drawable.logo_formosa));
        listaProvincias.add(new Provincia("Jujuy", "","38", R.drawable.logo_jujuy));
        listaProvincias.add(new Provincia("La Pampa", "","42", R.drawable.logo_lapampa));
        listaProvincias.add(new Provincia("La Rioja", "","46", R.drawable.logo_larioja));
        listaProvincias.add(new Provincia("Mendoza", "","50", R.drawable.logo_mendoza));
        listaProvincias.add(new Provincia("Misiones", "","54", R.drawable.logo_misiones));
        listaProvincias.add(new Provincia("Neuquén", "","58", R.drawable.logo_neuquen));
        listaProvincias.add(new Provincia("Rio Negro", "","62", R.drawable.logo_rionegro));
        listaProvincias.add(new Provincia("Salta", "","66", R.drawable.logo_salta));
        listaProvincias.add(new Provincia("San Juan", "","70", R.drawable.logo_sanjuan));
        listaProvincias.add(new Provincia("San Luis", "","74", R.drawable.logo_sanluis));
        listaProvincias.add(new Provincia("Santa Cruz", "","78", R.drawable.logo_santacruz));
        listaProvincias.add(new Provincia("Santa Fe", "","82", R.drawable.logo_santafe));
        listaProvincias.add(new Provincia("Santiago del Estero", "","86", R.drawable.logo_santiago));
        listaProvincias.add(new Provincia("Tierra del Fuego", "","94", R.drawable.logo_tierradelfuego));
        listaProvincias.add(new Provincia("Tucumán", "","90", R.drawable.logo_tucuman));
    }

    public void mostrarData(){
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterProvincias(getContext(),listaProvincias);
        recycler.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre;
                nombre = listaProvincias.get(recycler.getChildAdapterPosition(v)).getNombre();
                String idProvin = listaProvincias.get(recycler.getChildAdapterPosition(v)).getIdProvincia();
                Log.v("Provincia: ", nombre);
                //Toast.makeText(getContext(),"Seleccionó: "+ nombre,Toast.LENGTH_SHORT).show();
                //interfaceComunicacionEntreFragment.enviarProvincia(listaProvincias.get(recycler.getChildAdapterPosition(v)));


                Call <ProvinciaResponse> call = ApiClient.getApiService().getProvinciaId(idProvin);
                call.enqueue(new  Callback <ProvinciaResponse>() {
                    @Override
                    public void onResponse(Call call, @NonNull Response response) {
                        if(response.isSuccessful()){
                            ProvinciaResponse respuesta = (ProvinciaResponse) response.body();

                            Toast.makeText(getContext(),"Seleccionó: "+ respuesta.getTerritorioNombre(),Toast.LENGTH_LONG).show();
                    Log.v("detalle"," "+respuesta.getConfirmados());

                            interfaceComunicacionEntreFragment.enviarProvincia(respuesta);
                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.v("toastnot", " falle");
                    }
                });

            }
        });
    }


    //para la comunicacion
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        if(context instanceof Activity){
            this.activity = (Activity) context;
            interfaceComunicacionEntreFragment = (IComunicaFragment) this.activity;
        }

    }



}

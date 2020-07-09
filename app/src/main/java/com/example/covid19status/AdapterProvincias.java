package com.example.covid19status;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19status.Entidades.Provincia;

import java.util.ArrayList;

public class AdapterProvincias extends RecyclerView.Adapter<AdapterProvincias.ViewHolderDatos> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Provincia> listaProvincias;

    private View.OnClickListener listener;

    public AdapterProvincias(Context context, ArrayList<Provincia> listaProvincias){

        this.inflater = LayoutInflater.from(context);
        this.listaProvincias = listaProvincias;

    }

    public AdapterProvincias(ArrayList<Provincia> listaProvincias) {
        this.listaProvincias = listaProvincias;
    }

    @NonNull
    @Override
    //Este metodo, enlaza este adaptador, con la lista de items del recycler
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    //se encarga de hacer el bindeo entre el adaptador y el view holder datos de mas abajo
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        String nombre = listaProvincias.get(position).getNombre();
        String descrip = listaProvincias.get(position).getDescripcion();
        int imag = listaProvincias.get(position).getImagenid();
        holder.provincia.setText(nombre);
        holder.descripcion.setText(descrip);
        holder.imagen.setImageResource(imag);
    }

    @Override
    //devuelve el tamaño de la lista
    public int getItemCount() {
        return listaProvincias.size();
    }

    @Override
    public void onClick(View v) {

        if(listener!= null){
            listener.onClick(v);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView provincia, descripcion, algunaOtraInformacion;
        ImageView imagen;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            provincia = itemView.findViewById(R.id.idProvDato);
            descripcion = itemView.findViewById(R.id.idDescription);
            imagen = itemView.findViewById(R.id.IdImagen);
        }

    }
}

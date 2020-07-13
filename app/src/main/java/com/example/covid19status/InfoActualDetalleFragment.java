package com.example.covid19status;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19status.Responses.ProvinciaResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static androidx.core.content.FileProvider.getUriForFile;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoActualDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoActualDetalleFragment extends Fragment {


    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0 ;
    TextView nombreProvincia;
    TextView confirmadosNuevos;
    TextView confirmadosTotal;
    TextView muertosNuevos;
    TextView muertosTotal;
    TextView fecha;
    Button btnDetalleAGuardar;
    Button btnDetalleACompartir;



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
        btnDetalleAGuardar = vista.findViewById(R.id.detalleBtnGuardar);
        btnDetalleACompartir = vista.findViewById(R.id.detalleBtnCompartir);

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

            final ArrayList datosParaPdf = new ArrayList();
            datosParaPdf.add("Provincia:");                                 //0
            datosParaPdf.add(provinciaRecibido.getTerritorioNombre());      //1
            datosParaPdf.add("Confirmados: ");                              //2
            datosParaPdf.add("Nuevos: "+ confNuevos);                       //3
            datosParaPdf.add("Total: "+confTotal);                          //4
            datosParaPdf.add("Muertes: ");                                  //5
            datosParaPdf.add("Nuevas: "+muerNuevos);                        //6
            datosParaPdf.add("Total: "+muerTotal);                          //7
            datosParaPdf.add("Última Actualizacion: ");                     //8
            datosParaPdf.add(fechaNueva);                                   //9
            //detalle Compartir


            btnDetalleAGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertaGuardarPdf(datosParaPdf);

                    //crearPDF();
                }
            });

            //para compartir con amigos por wp
/*
            btnDetalleACompartir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compartirInformacion(datosParaPdf);

                    //crearPDF();
                }
            });
*/
        }
        return vista;
    }


private void compartirInformacion(ArrayList datosPdf){


    PdfDocument miDocumentoPdf = new PdfDocument();
    Paint myPaint = new Paint();

    PdfDocument.PageInfo miPaginaConfig1 = new PdfDocument.PageInfo.Builder(400,600,1).create();
    PdfDocument.Page miPagina = miDocumentoPdf.startPage(miPaginaConfig1);
    Canvas canvas = miPagina.getCanvas();
    canvas.drawText("COVID-19 status Información", 40, 50, myPaint);
    canvas.drawText(datosPdf.get(0).toString(), 40, 80, myPaint);
    canvas.drawText(datosPdf.get(1).toString(), 170, 80, myPaint);
    canvas.drawText(datosPdf.get(2).toString(), 40, 120, myPaint);
    canvas.drawText(datosPdf.get(3).toString(), 40, 150, myPaint);
    canvas.drawText(datosPdf.get(4).toString(), 40, 180, myPaint);
    canvas.drawText(datosPdf.get(5).toString(), 40, 220, myPaint);
    canvas.drawText(datosPdf.get(6).toString(), 40, 250, myPaint);
    canvas.drawText(datosPdf.get(7).toString(), 40, 280, myPaint);
    canvas.drawText(datosPdf.get(8).toString(), 40, 320, myPaint);
    canvas.drawText(datosPdf.get(9).toString(), 170, 320, myPaint);


    miDocumentoPdf.finishPage(miPagina);

    miDocumentoPdf.close();

    File file = new File(Environment.getExternalStorageDirectory(),"/Covid19-InformacionCompartir.pdf");

    //File imagePath = new File(getContext().getFilesDir(), "pdf");
   // File newFile = new File(imagePath, "default_image.jpg");
    Uri contentUri = getUriForFile(getContext(), "com.mydomain.fileprovider", file);

    Intent intent = new Intent (Intent.ACTION_SEND);
    intent.putExtra(Intent.EXTRA_STREAM, contentUri);
    intent.setType("application/pdf");
    //startActivity(Intent.createChooser(intent, "CompartiendoApp"));
    startActivity(Intent.createChooser(intent, "Compartiendo..."));
}
    
    private void crearPDF(ArrayList datosParaPdf){

        askSaveInformationLocal();
        PdfDocument miDocumentoPdf = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo miPaginaConfig1 = new PdfDocument.PageInfo.Builder(400,600,1).create();
        PdfDocument.Page miPagina = miDocumentoPdf.startPage(miPaginaConfig1);
        Canvas canvas = miPagina.getCanvas();
        canvas.drawText("COVID-19 status Información", 40, 50, myPaint);
        canvas.drawText(datosParaPdf.get(0).toString(), 40, 80, myPaint);
        canvas.drawText(datosParaPdf.get(1).toString(), 170, 80, myPaint);
        canvas.drawText(datosParaPdf.get(2).toString(), 40, 120, myPaint);
        canvas.drawText(datosParaPdf.get(3).toString(), 40, 150, myPaint);
        canvas.drawText(datosParaPdf.get(4).toString(), 40, 180, myPaint);
        canvas.drawText(datosParaPdf.get(5).toString(), 40, 220, myPaint);
        canvas.drawText(datosParaPdf.get(6).toString(), 40, 250, myPaint);
        canvas.drawText(datosParaPdf.get(7).toString(), 40, 280, myPaint);
        canvas.drawText(datosParaPdf.get(8).toString(), 40, 320, myPaint);
        canvas.drawText(datosParaPdf.get(9).toString(), 170, 320, myPaint);


        miDocumentoPdf.finishPage(miPagina);

        File file = new File(Environment.getExternalStorageDirectory(),"/Covid19-Informacion.pdf");

        try {
            miDocumentoPdf.writeTo(new FileOutputStream(file));
            Toast.makeText(getContext(),"PDF guardado en memoria interna/Covid19-Informacion.pdf", Toast.LENGTH_LONG).show();

        } catch (IOException e){
            e.printStackTrace();
        }

        miDocumentoPdf.close();



    }


    private void askSaveInformationLocal(){
        if(ContextCompat.checkSelfPermission(getContext() ,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        }

    }


    private void AlertaGuardarPdf(final ArrayList datosParaPdf){
         final ArrayList datos = datosParaPdf;
        AlertDialog dialogo = new AlertDialog
                .Builder(getContext()) // NombreDeTuActividad.this, o getActivity() si es dentro de un fragmento
                .setPositiveButton("Sí, guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hicieron click en el botón positivo, así que la acción está confirmada
                        crearPDF(datos);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hicieron click en el botón negativo, no confirmaron
                        // Simplemente descartamos el diálogo
                        dialog.dismiss();
                    }
                })
                .setTitle("Guardar PDF") // El título
                .setMessage("¿Deseas guardar la información?") // El mensaje
                .create();// No olvides llamar a Create, ¡pues eso crea el AlertDialog!

        dialogo.show();
    }

}

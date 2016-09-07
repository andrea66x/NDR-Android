package fiec.ndr;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {
    String user;
    Integer rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        SharedPreferences prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
        user = prefs.getString("usuario", "desconocido");
        rol = prefs.getInt("rol", 100);
    }

    public void nuevoFormulario(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void configuracion(View view){
        if(rol!=0){
            niveldeAcceso();
        }
        else {
            Intent intent = new Intent(this, Configuracion.class);
            startActivity(intent);
        }
    }

    public void sincronizarBase(View view){
        if(rol!=0){
            niveldeAcceso();
        }
        else {
            Intent intent = new Intent(this, SincronizarEncuestas.class);
            startActivity(intent);
        }
    }

    public void informacion(View view){
        Intent intent = new Intent(this, InfoContacto.class);
        startActivity(intent);
    }

    public void salirSistema(View view){
        System.exit(0);
    }

    /*Metodo para prevenir salir del formulario al presionar el boton atras*/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Salir")
                .setMessage("¿Quieres salir del sistema?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MenuPrincipal.super.onBackPressed();
                    }
                }).create().show();
    }

    public void niveldeAcceso() {

        final Dialog codigoDialog = new Dialog(this);
        codigoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        codigoDialog.setCancelable(false);
        codigoDialog.setContentView(R.layout.acceso_dialog);

        TextView titulo = (TextView) codigoDialog.findViewById(R.id.titulo);
        titulo.setText("Usted no tiene permiso para ingresar a esta sección");

        // Init button of login GUI
        Button btnAceptar = (Button) codigoDialog.findViewById(R.id.btnAceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoDialog.dismiss();
            }
        });
        codigoDialog.show();
    }
}

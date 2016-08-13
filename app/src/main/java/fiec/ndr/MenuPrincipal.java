package fiec.ndr;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void nuevoFormulario(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void buscarEncuesta(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void formularios(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void sincronizarBase(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void informacion(View view){
        Intent intent = new Intent(this, Secciones.class);
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
}

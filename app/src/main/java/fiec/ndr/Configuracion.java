package fiec.ndr;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity {

    String nombres, apellidos, cedula, direccion, puerto;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    EditText txtNombres, txtApellidos, txtCedula, txtDireccion,txtPuerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
        editor = prefs.edit();

        nombres = prefs.getString("nombre_encuestador", "");
        apellidos =  prefs.getString("apellido_encuestador", "");
        cedula =  prefs.getString("cedula_encuestador", "");
        direccion =  prefs.getString("direccion_servidor", "");
        puerto = prefs.getString("puerto_servidor", "");

        txtNombres = (EditText) findViewById(R.id.conf_nombre);
        txtApellidos = (EditText) findViewById(R.id.conf_apellidos);
        txtCedula = (EditText) findViewById(R.id.conf_cedula);
        txtDireccion = (EditText) findViewById(R.id.conf_direccion);
        txtPuerto = (EditText) findViewById(R.id.conf_puerto);

        assert txtNombres != null;
        assert txtApellidos != null;
        assert txtCedula != null;
        assert txtDireccion != null;
        assert txtPuerto != null;

        txtNombres.setText(nombres);
        txtApellidos.setText(apellidos);
        txtCedula.setText(cedula);
        txtDireccion.setText(direccion);
        txtPuerto.setText(puerto);

        txtNombres.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    nombres = txtNombres.getText().toString();
                    editor.putString("nombre_encuestador", nombres);
                    editor.apply();
                }
            }
        });


        txtApellidos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    apellidos = txtApellidos.getText().toString();
                    editor.putString("apellido_encuestador", apellidos);
                    editor.apply();
                }
            }
        });


        txtCedula.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    cedula = txtCedula.getText().toString();
                    editor.putString("cedula_encuestador", cedula);
                    editor.apply();
                }
            }
        });


        txtDireccion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    direccion = txtDireccion.getText().toString();
                    editor.putString("direccion_servidor", direccion);
                    editor.apply();
                }
            }
        });


        txtPuerto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    puerto = txtPuerto.getText().toString();
                    editor.putString("puerto_servidor", puerto);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guardar_conf, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_guardar_conf:

                nombres = txtNombres.getText().toString();
                apellidos = txtApellidos.getText().toString();
                cedula = txtCedula.getText().toString();
                direccion = txtDireccion.getText().toString();
                puerto = txtPuerto.getText().toString();

                editor.putString("nombre_encuestador", nombres);
                editor.putString("apellido_encuestador", apellidos);
                editor.putString("cedula_encuestador", cedula);
                editor.putString("direccion_servidor", direccion);
                editor.putString("puerto_servidor", puerto);
                editor.putString("ruta_servidor", "http://"+direccion+":"+puerto+"/recibirjson");
                editor.apply();

                final Dialog codigoDialog = new Dialog(this);
                codigoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                codigoDialog.setCancelable(false);
                codigoDialog.setContentView(R.layout.inf_dialog);

                TextView titulo = (TextView) codigoDialog.findViewById(R.id.titulo);
                titulo.setText("Hemos guardado correctamente todas las configuraciones!");

                // Init button of login GUI
                Button btnAceptar = (Button) codigoDialog.findViewById(R.id.btnAceptar);

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        codigoDialog.dismiss();
                    }
                });
                codigoDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}


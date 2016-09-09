package fiec.ndr;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Secciones extends AppCompatActivity {
    Button btnGeneral;
    String user;
    Integer rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secciones);
        SharedPreferences prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
        user = prefs.getString("usuario", "desconocido");
        rol = prefs.getInt("rol", 100);
    }

    public void onClickBtnSecciones(View v) {
        nuevoFormulario(v);
    }

    public void nuevoFormulario(View view) {

        final Dialog codigoDialog = new Dialog(this);
        Directorios dir = new Directorios(true);
        btnGeneral = (Button) view;
        codigoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        codigoDialog.setCancelable(false);
        codigoDialog.setContentView(R.layout.codigo_dialog);

        TextView titulo = (TextView) codigoDialog.findViewById(R.id.titulo);
        titulo.setText("Ingresa el Código del Paciente:");

        // Init button of login GUI
        Button btnEntrar = (Button) codigoDialog.findViewById(R.id.btnEntrar);
        final Button btnCancelar = (Button) codigoDialog.findViewById(R.id.btnCancelar);
        final EditText txtCodigo = (EditText) codigoDialog.findViewById(R.id.codigo_encuesta);

        // Attached listener for login GUI button
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo;
                if (codigoCorrecto(txtCodigo.getText().toString().trim())) {
                    switch (btnGeneral.getId()) {
                        case R.id.btn_inf_general:
                            if(rol!=2){
                                niveldeAcceso();
                            }
                            else {
                                Intent int_info_general = new Intent(getApplicationContext(), InformacionGeneral.class);
                                codigo = txtCodigo.getText().toString();
                                int_info_general.putExtra("CODIGO",codigo);
                                startActivity(int_info_general);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                codigoDialog.dismiss();

                            }
                            break;
                        case R.id.btn_medidas:
                            if(rol!=3){
                                niveldeAcceso();
                            }
                            else {
                                Intent int_medidas = new Intent(getApplicationContext(), Medidas.class);
                                codigo = txtCodigo.getText().toString();
                                int_medidas.putExtra("CODIGO", codigo);
                                startActivity(int_medidas);
                                codigoDialog.dismiss();
                            }
                            break;
                        case R.id.btn_presion:
                            if(rol!=4){
                                niveldeAcceso();
                            }
                            else {
                                Intent int_presion = new Intent(getApplicationContext(), Presion_arterial.class);
                                codigo = txtCodigo.getText().toString();
                                int_presion.putExtra("CODIGO", codigo);
                                startActivity(int_presion);
                                codigoDialog.dismiss();
                            }
                            break;
                        case R.id.btn_laboratorio:
                            if(rol!=5){
                                niveldeAcceso();
                            }
                            else {
                                Intent int_laboratorio = new Intent(getApplicationContext(), Laboratorio.class);
                                codigo = txtCodigo.getText().toString();
                                int_laboratorio.putExtra("CODIGO", codigo);
                                startActivity(int_laboratorio);
                                codigoDialog.dismiss();
                            }
                            break;

                        default:
                            break;
                    }
                } else {
                    Toast.makeText(Secciones.this,
                            "Por favor ingresa un código válido!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoDialog.dismiss();
            }
        });

        // Make dialog box visible.
        if (btnGeneral.getId() != R.id.btn_encuesta){
            switch (btnGeneral.getId()) {
                case R.id.btn_inf_general:
                    if(rol!=2){
                        niveldeAcceso();
                    }
                    else {
                        codigoDialog.show();
                    }
                    break;
                case R.id.btn_medidas:
                    if(rol!=3){
                        niveldeAcceso();
                    }
                    else {
                        codigoDialog.show();
                    }
                    break;
                case R.id.btn_presion:
                    if(rol!=4){
                        niveldeAcceso();
                    }
                    else {
                        codigoDialog.show();
                    }
                    break;
                case R.id.btn_laboratorio:
                    if(rol!=5){
                        niveldeAcceso();
                    }
                    else {
                        codigoDialog.show();
                    }
                    break;
                default:
                    codigoDialog.show();
                    break;
            }
        }
        else {
            if(rol!=1){
                niveldeAcceso();
            }
            else {
                Intent int_encuesta = new Intent(this, Preparacion.class);
                startActivity(int_encuesta);
            }
        }
    }

    public boolean codigoCorrecto(final String value) {
        int index = 0;
        boolean checkeralph = false;
        boolean checkernum = false;
        if (value.length() == 7) {
            if (Character.isLetter(value.charAt(0)) && Character.isLetter(value.charAt(1)))
                checkeralph = true;
            if (Character.isDigit(value.charAt(2))
                    && Character.isDigit(value.charAt(3))
                    && Character.isDigit(value.charAt(4))
                    && Character.isDigit(value.charAt(5))
                    && Character.isDigit(value.charAt(6)))
                checkernum = true;
        }
        if (checkeralph && checkernum)
            return true;
        else
            return false;
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

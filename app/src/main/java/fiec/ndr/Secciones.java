package fiec.ndr;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Secciones extends AppCompatActivity implements View.OnClickListener{
    Button btnNuevaEncuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secciones);
        btnNuevaEncuesta = (Button) findViewById(R.id.btn_inf_general);
        btnNuevaEncuesta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_encuesta:
                Intent int_encuesta = new Intent(this,InformacionGeneral.class);
                startActivity(int_encuesta);
                break;
            case R.id.btn_inf_general:
                nuevoFormulario(v);
                break;
            case R.id.btn_medidas:
                Intent int_medidas = new Intent(this,InformacionGeneral.class);
                startActivity(int_medidas);
                break;
            case R.id.btn_presion:
                Intent int_presion = new Intent(this,InformacionGeneral.class);
                startActivity(int_presion);
                break;
            case R.id.btn_laboratorio:
                Intent int_laboratorio = new Intent(this,InformacionGeneral.class);
                startActivity(int_laboratorio);
                break;

            default:
                break;
        }
    }

    public void nuevoFormulario(View view){
        if (view == btnNuevaEncuesta) {

            // Create Object of Dialog class
            final Dialog codigoDialog = new Dialog(this);
            // Set GUI of login screen
            codigoDialog.setContentView(R.layout.codigo_dialog);
            codigoDialog.setTitle("Ingresa el Código del Paciente:");

            // Init button of login GUI
            Button btnEntrar = (Button) codigoDialog.findViewById(R.id.btnEntrar);
            Button btnCancelar = (Button) codigoDialog.findViewById(R.id.btnCancelar);
            final EditText txtCodigo = (EditText)codigoDialog.findViewById(R.id.codigo_encuesta);

            // Attached listener for login GUI button
            btnEntrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtCodigo.getText().toString().trim().length() > 0)
                    {
                        Intent intent = new Intent(getApplicationContext(), InformacionGeneral.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Secciones.this,
                                "Por favor ingresa un código!", Toast.LENGTH_LONG).show();

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
            codigoDialog.show();
        }

    }

}

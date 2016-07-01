package fiec.ndr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Secciones extends AppCompatActivity implements View.OnClickListener{
    Button btn_infgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secciones);

        btn_infgen = (Button) findViewById(R.id.btn_inf_general);
        btn_infgen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_encuesta:
                Intent int_encuesta = new Intent(this,InformacionGeneral.class);
                startActivity(int_encuesta);
                break;
            case R.id.btn_inf_general:
                Intent int_infgen = new Intent(this,InformacionGeneral.class);
                startActivity(int_infgen);
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
}

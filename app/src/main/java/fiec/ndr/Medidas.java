package fiec.ndr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class Medidas extends AppCompatActivity {

    private static String codigo;

    private EditText peso, estatura, imc, cintura, cadera, icc, peso_sal;

    private int med_peso, med_estatura, med_cintura, med_cadera;
    private double med_imc, med_icc, med_peso_sal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Todos los Datos Guardados.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                med_peso = Integer.parseInt(peso.getText().toString());
                med_estatura = Integer.parseInt(estatura.getText().toString());
                med_cadera = Integer.parseInt(cadera.getText().toString());
                med_cintura = Integer.parseInt(cintura.getText().toString());

                double temp = (double)med_estatura/100;
                med_imc = (double)med_peso / Math.pow(temp, temp);
                med_icc = (double)med_cintura / (double)med_cadera;
                med_peso_sal = Math.pow(temp, temp) * 23;

                DecimalFormat format = new DecimalFormat("##.##");
                String formated_imc = format.format(med_imc);
                String formated_icc = format.format(med_icc);
                String formated_peso_sal = format.format(med_peso_sal);

                imc.setText(formated_imc);
                icc.setText(formated_icc);
                peso_sal.setText(formated_peso_sal);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            codigo = (String) bundle.get("CODIGO");
        }
        setTitle("Medidas: " + codigo);

        this.peso = (EditText) findViewById(R.id.medidas_peso);
        this.estatura = (EditText) findViewById(R.id.medidas_estatura);
        this.imc = (EditText) findViewById(R.id.medidas_imc);
        this.cintura = (EditText) findViewById(R.id.medidas_cintura);
        this.cadera = (EditText) findViewById(R.id.medidas_caderas);
        this.icc = (EditText) findViewById(R.id.medidas_icc);
        this.peso_sal = (EditText) findViewById(R.id.medidas_peso_salud);





    }

}

package fiec.ndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Medidas extends AppCompatActivity {

    String codigo, UUID, hora_encuesta;
    String peso, estatura, med_cintura, med_cadera;

    private EditText et_peso, et_estatura, et_cintura, et_cadera;
    private Map<String, String> hm_medidas = new HashMap<String, String>();


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
                Snackbar.make(view, guardarJson(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            codigo = (String) bundle.get("CODIGO");
        }
        setTitle("Medidas: " + codigo);

        et_peso = (EditText) findViewById(R.id.data_peso_kg);
        et_estatura = (EditText) findViewById(R.id.data_estatura);
        et_cintura = (EditText) findViewById(R.id.data_cintura);
        et_cadera = (EditText) findViewById(R.id.data_cadera);

    }

    private String guardarJson(){

        JSONArray jarray_datos;
        JSONObject temp1;

        //Obtenemos el UUID del dispositivo desde que ha sido creado el JSON.
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        UUID = tManager.getDeviceId();

        TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
        Calendar calendar = Calendar.getInstance();
        hora_encuesta = calendar.getTime().toString();

        hm_medidas.clear();


        peso = et_peso.getText().toString();
        if (!peso.isEmpty())
            hm_medidas.put("peso", peso);
        else
            return "No has ingresado el peso, en ninguna medida.";


        estatura = et_estatura.getText().toString();
        if (!estatura.isEmpty())
            hm_medidas.put("estatura", estatura);
        else
            return "No has ingresado la estatura aún.";

        med_cadera = et_cadera.getText().toString();
        if (!med_cadera.isEmpty())
            hm_medidas.put("med_cadera", med_cadera);
        else
            return "No has ingresado el diametro de la cadera aún.";

        med_cintura = et_cintura.getText().toString();
        if (!med_cintura.isEmpty())
            hm_medidas.put("med_cintura", med_cintura);
        else
            return "No has ingresado el diametro de la cintura aún.";


        JSONObject json_medidas = new JSONObject();
        jarray_datos = new JSONArray();
        temp1 = new JSONObject(hm_medidas);
        jarray_datos.put(temp1);

        try {
            json_medidas.put("id_formulario", codigo);
            json_medidas.put("tipo_formulario", "Medidas");
            json_medidas.put("uuid_creado", UUID);
            json_medidas.put("hora_creacion", hora_encuesta);
            json_medidas.put("medidas", jarray_datos);
            Directorios dir = new Directorios(false);
            String retro = dir.guardarAchivo(json_medidas.toString(),codigo,3);
            return retro;

        } catch (JSONException e) {
            e.printStackTrace();
            return "Excepcion del sistema, construyendo del json medidas.";
        }


    }


}

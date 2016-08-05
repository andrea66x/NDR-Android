package fiec.ndr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Presion_arterial extends AppCompatActivity {

    String codigo, UUID, hora_encuesta;
    String data_min_1, data_min_2, data_min_3, data_max_1, data_max_2, data_max_3;

    private EditText et_min_1, et_min_2, et_min_3, et_max_1, et_max_2, et_max_3;

    private Map<String, String> hm_presion = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presion_arterial);
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
        setTitle("Presión Arterial: " + codigo);

        et_min_1 = (EditText) findViewById(R.id.data_prs_min_1);
        et_min_2 = (EditText) findViewById(R.id.data_prs_min_2);
        et_min_3 = (EditText) findViewById(R.id.data_prs_min_3);
        et_max_1 = (EditText) findViewById(R.id.data_prs_max_1);
        et_max_2 = (EditText) findViewById(R.id.data_prs_max_2);
        et_max_3 = (EditText) findViewById(R.id.data_prs_max_3);
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

        hm_presion.clear();

        data_min_1 = et_min_1.getText().toString();
        if (!data_min_1.isEmpty())
            hm_presion.put("presion_min_1", data_min_1);
        else
            return "No has ingresado todas las medidas de presión.";

        data_min_2 = et_min_2.getText().toString();
        if (!data_min_2.isEmpty())
            hm_presion.put("presion_min_2", data_min_2);
        else
            return "No has ingresado todas las medidas de presión.";

        data_min_3 = et_min_3.getText().toString();
        if (!data_min_3.isEmpty())
            hm_presion.put("presion_min_3", data_min_3);
        else
            return "No has ingresado todas las medidas de presión.";

        data_max_1 = et_max_1.getText().toString();
        if (!data_max_1.isEmpty())
            hm_presion.put("presion_max_1", data_max_1);
        else
            return "No has ingresado todas las medidas de presión.";

        data_max_2 = et_max_2.getText().toString();
        if (!data_max_2.isEmpty())
            hm_presion.put("presion_max_2", data_max_2);
        else
            return "No has ingresado todas las medidas de presión.";

        data_max_3 = et_max_3.getText().toString();
        if (!data_max_3.isEmpty())
            hm_presion.put("presion_max_3", data_max_3);
        else
            return "No has ingresado todas las medidas de presión.";


        JSONObject json_presion = new JSONObject();
        jarray_datos = new JSONArray();
        temp1 = new JSONObject(hm_presion);
        jarray_datos.put(temp1);

        try {
            json_presion.put("id_formulario", codigo);
            json_presion.put("tipo_formulario", "Presion");
            json_presion.put("uuid_creado", UUID);
            json_presion.put("hora_creacion", hora_encuesta);
            json_presion.put("presion", jarray_datos);
            Directorios dir = new Directorios(false);
            String retro = dir.guardarAchivo(json_presion.toString(),codigo,4);
            return retro;

        } catch (JSONException e) {
            e.printStackTrace();
            return "Excepcion del sistema, construyendo del json presion.";
        }

    }

}

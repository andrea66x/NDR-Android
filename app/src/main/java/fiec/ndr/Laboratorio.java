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

public class Laboratorio extends AppCompatActivity {

    private static String codigo, UUID, hora_encuesta;
    String data_glucosa_min, data_glucosa_max, data_hba1c, data_ualbum, data_creatinina, data_filtrado;
    EditText et_glucosa_min, et_glucosa_max, et_hba1c, et_ualbum, et_creatinina, et_filtrado;

    private Map<String, String> hm_laboratorio = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio);
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
        setTitle("Laboratorio: " + codigo);

        et_glucosa_min = (EditText) findViewById(R.id.data_glucosa_min);
        et_glucosa_max = (EditText) findViewById(R.id.data_glucosa_max);
        et_creatinina = (EditText) findViewById(R.id.data_creatinina);
        et_hba1c = (EditText) findViewById(R.id.data_hba1c);
        et_ualbum = (EditText) findViewById(R.id.data_ualbum);
        et_filtrado = (EditText) findViewById(R.id.data_filtrado);
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

        hm_laboratorio.clear();

        data_glucosa_min = et_glucosa_min.getText().toString();
        if (!data_glucosa_min.isEmpty())
            hm_laboratorio.put("glucosa_min", data_glucosa_min);
        else
            return "No has ingresado la glucemina minima.";

        data_glucosa_max = et_glucosa_max.getText().toString();
        if (!data_glucosa_max.isEmpty())
            hm_laboratorio.put("glucosa_max", data_glucosa_max);
        else
            return "No has ingresado la glucemia máxima.";

        data_hba1c = et_hba1c.getText().toString();
        if (!data_hba1c.isEmpty())
            hm_laboratorio.put("hba1c", data_hba1c);
        else
            return "No has ingresado la hemoglobina glicosilada.";

        data_ualbum = et_ualbum.getText().toString();
        if (!data_ualbum.isEmpty())
            hm_laboratorio.put("microalbuminuria", data_ualbum);
        else
            return "No has ingresado la microalbuminuria.";

        data_creatinina = et_creatinina.getText().toString();
        if (!data_creatinina.isEmpty())
            hm_laboratorio.put("creatinina", data_creatinina);
        else
            return "No has ingresado la creatinina.";

        data_filtrado = et_filtrado.getText().toString();
        if (!data_filtrado.isEmpty())
            hm_laboratorio.put("filtrado", data_filtrado);
        else
            return "No has ingresado el filtrado glomerular.";


        JSONObject json_laboratorio = new JSONObject();
        jarray_datos = new JSONArray();
        temp1 = new JSONObject(hm_laboratorio);
        jarray_datos.put(temp1);

        try {
            json_laboratorio.put("id_formulario", codigo);
            json_laboratorio.put("tipo_formulario", "Laboratorio");
            json_laboratorio.put("uuid_creado", UUID);
            json_laboratorio.put("hora_creacion", hora_encuesta);
            json_laboratorio.put("Resultados", jarray_datos);
            Directorios dir = new Directorios(false);
            String retro = dir.guardarAchivo(json_laboratorio.toString(),codigo,5);
            return retro;

        } catch (JSONException e) {
            e.printStackTrace();
            return "Excepcion del sistema, construyendo del json laboratorio.";
        }

    }

}

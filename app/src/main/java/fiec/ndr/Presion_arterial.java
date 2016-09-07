package fiec.ndr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Presion_arterial extends AppCompatActivity {

    String codigo, UUID, hora_encuesta;
    String data_min_1, data_min_2, data_min_3, data_max_1, data_max_2, data_max_3, data_nombres;
    int result;
    String nombres_encuestador, cedula_encuestador;
    SharedPreferences prefs;

    private EditText et_min_1, et_min_2, et_min_3, et_max_1, et_max_2, et_max_3, et_nombres;

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
                Snackbar.make(view, guardarJson(), Snackbar.LENGTH_LONG).setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if ((event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_SWIPE) && result == 1)
                            finish();
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                }).show();
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
        et_nombres = (EditText) findViewById(R.id.data_nombres);

        prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
        nombres_encuestador = prefs.getString("nombre_encuestador", "") + "" +  prefs.getString("apellido_encuestador", "");
        cedula_encuestador =  prefs.getString("cedula_encuestador", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ayuda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ayuda:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Presion_arterial.this);

                alertDialog.setTitle("Ayuda Presión Arterial:");

                alertDialog.setMessage(
                        "- Recuerda ingresar los nombres y apellidos completos del encuestado. \n\n" +
                        "- Recuerda realizar las 3 tomas de la presión, una máxima y una miníma por cada intento. \n\n" +
                        "- Las unidades de los datos de la presión esta dada den mm/Hg. \n\n" +
                        "- Por cada toma se debe esperar 60 segundos. \n\n" +
                        "- Trata de ser amigable con el paciente, si se siente nervioso sesgarás la toma. \n\n");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.mipmap.ayuda_b);

                // Setting Netural "Cancel" Button
                alertDialog.setPositiveButton("Entendido!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private String guardarJson(){

        JSONArray jarray_datos;
        JSONObject temp1;
        result=2;

        //Obtenemos el UUID del dispositivo desde que ha sido creado el JSON.
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        UUID = tManager.getDeviceId();

        TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d yyyy, h:mm a", Locale.US);
        hora_encuesta = format.format(calendar.getTime());

        hm_presion.clear();

        data_nombres = et_nombres.getText().toString();
        if (data_nombres.matches(".*\\w.*") && !data_nombres.isEmpty())
            hm_presion.put("nombres", data_nombres);
        else
            return "No has ingresado los nombres del encuestado.";

        data_min_1 = et_min_1.getText().toString();
        if (!data_min_1.isEmpty())
            hm_presion.put("presion_min_1", data_min_1);
        else
            return "No has ingresado la primera toma mínima.";

        data_min_2 = et_min_2.getText().toString();
        if (!data_min_2.isEmpty())
            hm_presion.put("presion_min_2", data_min_2);
        else
            return "No has ingresado la segunda toma mínima.";

        data_min_3 = et_min_3.getText().toString();
        if (!data_min_3.isEmpty())
            hm_presion.put("presion_min_3", data_min_3);
        else
            return "No has ingresado la tercera toma mínima.";

        data_max_1 = et_max_1.getText().toString();
        if (!data_max_1.isEmpty())
            hm_presion.put("presion_max_1", data_max_1);
        else
            return "No has ingresado la primera toma máxima.";

        data_max_2 = et_max_2.getText().toString();
        if (!data_max_2.isEmpty())
            hm_presion.put("presion_max_2", data_max_2);
        else
            return "No has ingresado la segunda toma máxima.";

        data_max_3 = et_max_3.getText().toString();
        if (!data_max_3.isEmpty())
            hm_presion.put("presion_max_3", data_max_3);
        else
            return "No has ingresado la tercera toma máxima.";


        JSONObject json_presion = new JSONObject();
        jarray_datos = new JSONArray();
        temp1 = new JSONObject(hm_presion);
        jarray_datos.put(temp1);

        try {
            json_presion.put("id_formulario", codigo);
            json_presion.put("tipo_formulario", "Presion");
            json_presion.put("uuid_creado", UUID);
            json_presion.put("nombres_encuestador", nombres_encuestador);
            json_presion.put("cedula_encuestador", cedula_encuestador);
            json_presion.put("hora_creacion", hora_encuesta);
            json_presion.put("presion", jarray_datos);
            Directorios dir = new Directorios(false);
            result= dir.guardarAchivo(json_presion.toString(),codigo,4);
            if (result == 1)
                return "El formulario "+ codigo + " ha sido guardado exitosamente.";
            else if (result == 0)
                return "El formulario asociado a este codigo: " + codigo +" ya existe";
            else if (result == -1)
                return "Existe un problema con tu sistemas de archivos, llama a sistemas ahora.";
            else
                return "Algo raro ha pasado, intenta de nuevo por favor.";

        } catch (JSONException e) {
            e.printStackTrace();
            return "Excepcion del sistema, construyendo del json presion.";
        }

    }

}

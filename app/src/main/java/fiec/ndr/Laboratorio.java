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

public class Laboratorio extends AppCompatActivity {

    private static String codigo, UUID, hora_encuesta;
    String data_glucosa_min, data_glucosa_max, data_hba1c, data_ualbum, data_creatinina, data_nombres;
    EditText et_glucosa_min, et_glucosa_max, et_hba1c, et_ualbum, et_creatinina, et_nombres;
    int result;
    String nombres_encuestador, cedula_encuestador;
    SharedPreferences prefs;

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
        setTitle("Laboratorio: " + codigo);

        et_glucosa_min = (EditText) findViewById(R.id.data_glucosa_min);
        et_glucosa_max = (EditText) findViewById(R.id.data_glucosa_max);
        et_creatinina = (EditText) findViewById(R.id.data_creatinina);
        et_hba1c = (EditText) findViewById(R.id.data_hba1c);
        et_ualbum = (EditText) findViewById(R.id.data_ualbum);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Laboratorio.this);

                alertDialog.setTitle("Ayuda Laboratorio:");

                alertDialog.setMessage(
                        "- Recuerda ingresar los nombres y apellidos completos del encuestado. \n\n" +
                        "- La glucemia indica los niveles de glucosa en la sangre, esta dada en mg/dl. \n\n" +
                        "- Hemoglobina glicosilada es tambien conocida como promedio de glucosa o HbA1c. \n\n" +
                        "- La microalbuminuria debe ser ingresada en unidades mg/gr. \n\n" +
                        "- La creatinina debe estar en unidades mg/dl. \n\n");

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
        result = 2;

        //Obtenemos el UUID del dispositivo desde que ha sido creado el JSON.
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        UUID = tManager.getDeviceId();

        TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d yyyy, h:mm a", Locale.US);
        hora_encuesta = format.format(calendar.getTime());

        hm_laboratorio.clear();

        data_nombres = et_nombres.getText().toString();
        if (data_nombres.matches(".*\\w.*") && !data_nombres.isEmpty())
            hm_laboratorio.put("nombres", data_nombres);
        else
            return "No has ingresado los nombres del encuestado.";

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


        JSONObject json_laboratorio = new JSONObject();
        jarray_datos = new JSONArray();
        temp1 = new JSONObject(hm_laboratorio);
        jarray_datos.put(temp1);

        try {
            json_laboratorio.put("id_formulario", codigo);
            json_laboratorio.put("tipo_formulario", "Laboratorio");
            json_laboratorio.put("uuid_creado", UUID);
            json_laboratorio.put("nombres_encuestador", nombres_encuestador);
            json_laboratorio.put("cedula_encuestador", cedula_encuestador);
            json_laboratorio.put("hora_creacion", hora_encuesta);
            json_laboratorio.put("resultados", jarray_datos);
            Directorios dir = new Directorios(false);

            result= dir.guardarAchivo(json_laboratorio.toString(),codigo,5);
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
            return "Excepcion del sistema, construyendo del json laboratorio.";
        }

    }

}

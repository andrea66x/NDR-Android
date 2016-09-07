package fiec.ndr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Medidas extends AppCompatActivity {

    String codigo, UUID, hora_encuesta;
    String peso, estatura, med_cintura, med_cadera, data_nombres;
    int result;
    String nombres_encuestador, cedula_encuestador;
    SharedPreferences prefs;

    private EditText et_peso, et_estatura, et_cintura, et_cadera, et_nombres;
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
        setTitle("Medidas: " + codigo);

        et_peso = (EditText) findViewById(R.id.data_peso);
        et_estatura = (EditText) findViewById(R.id.data_estatura);
        et_cintura = (EditText) findViewById(R.id.data_cintura);
        et_cadera = (EditText) findViewById(R.id.data_cadera);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Medidas.this);

                alertDialog.setTitle("Ayuda Medidas:");

                alertDialog.setMessage("" +
                        "- Recuerda ingresar los nombres y apellidos completos del encuestado. \n\n" +
                        "- El peso esta dado en kilogramos y admite 2 decimales. \n\n" +
                        "- El estandar para la delimitacion de decimales es el punto y no la coma. \n\n" +
                        "- La estatura debe estar en cms, no admite decimales. \n\n" +
                        "- La medida de la cintura debe estar en cms, no admite decimales. \n\n" +
                        "- La medida de las caderas debe estar en cms, no admite decimales. \n\n");

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

        hm_medidas.clear();

        data_nombres = et_nombres.getText().toString();
        if (data_nombres.matches(".*\\w.*") && !data_nombres.isEmpty())
            hm_medidas.put("nombres", data_nombres);
        else
            return "No has ingresado los nombres del encuestado.";


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
            json_medidas.put("nombres_encuestador", nombres_encuestador);
            json_medidas.put("cedula_encuestador", cedula_encuestador);
            json_medidas.put("hora_creacion", hora_encuesta);
            json_medidas.put("medidas", jarray_datos);
            Directorios dir = new Directorios(false);
            result= dir.guardarAchivo(json_medidas.toString(),codigo,3);
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
            return "Excepcion del sistema, construyendo del json medidas.";
        }


    }


}

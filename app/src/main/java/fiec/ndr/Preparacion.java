package fiec.ndr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class Preparacion extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap bitmap_foto;
    String ruta_foto, codigo, dia, mes, anio, ayunas, lugar, strDt, data_nombres;
    String UUID, hora_encuesta;
    int result;
    String nombres_encuestador, cedula_encuestador;
    SharedPreferences prefs;


    EditText et_cod_encuesta, et_lugar, et_nombres;
    RadioGroup rg_ayunas;
    ImageView img_consent;
    Directorios dir = new Directorios(false);
    private Map<String, String> hm_preparacion = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparacion);
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

        et_cod_encuesta = (EditText) findViewById(R.id.codigo_encuesta);
        et_lugar = (EditText)findViewById(R.id.lugar_encuesta);
        et_nombres = (EditText)findViewById(R.id.data_nombres);
        img_consent = (ImageView)findViewById(R.id.fotoConsentimiento);
        rg_ayunas = (RadioGroup) findViewById(R.id.rg_ayunas);

        Calendar c = Calendar.getInstance();
        anio = Integer.toString(c.get(Calendar.YEAR));
        mes = Integer.toString(c.get(Calendar.MONTH)+1);
        dia = Integer.toString(c.get(Calendar.DAY_OF_MONTH));


        et_cod_encuesta.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!s.toString().isEmpty() && s.toString().matches("\\w{2}\\d{5}")){
                    et_cod_encuesta.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                }
                else {
                    et_cod_encuesta.requestFocus();
                }
            }
        });




        img_consent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            Log.i("PREPARACION", "IOException");
                        }
                        if (photoFile != null) {
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                }
            });

        //Obtengo el id del radio button que ha sido seleccionado en el radio group.
        rg_ayunas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 Para no, 1 para femenino.
                ayunas = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
        nombres_encuestador = prefs.getString("nombre_encuestador", "") + "" +  prefs.getString("apellido_encuestador", "");
        cedula_encuestador =  prefs.getString("cedula_encuestador", "");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                bitmap_foto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(ruta_foto));
                img_consent.setImageBitmap(bitmap_foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        codigo = et_cod_encuesta.getText().toString();
        String imageFileName = "cons_"+ codigo + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory(), dir.forms_fotos);
        File image = new File (storageDir, imageFileName);
        ruta_foto = "file:" + image.getAbsolutePath();
        return image;
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Preparacion.this);

                alertDialog.setTitle("Ayuda Preparación:");

                alertDialog.setMessage("" +
                        "- Recuerda ingresar los nombres y apellidos completos del encuestado. \n\n" +
                        "- Debes tratar de que el encuestado conteste si realmente esta en ayunas. \n\n" +
                        "- El codigo asignado al paciente consta de 2 letras seguidas de 5 números. \n\n" +
                        "- El lugar de la encuesta, sera determinado en la charla de preparación. \n\n" +
                        "- La foto es un requerimiento vital en la encuesta, por favor asegurate que sea de calidad. \n\n");

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

        hm_preparacion.clear();

        data_nombres = et_nombres.getText().toString();
        if (data_nombres.matches(".*\\w.*") && !data_nombres.isEmpty())
            hm_preparacion.put("nombres", data_nombres);
        else
            return "No has ingresado los nombres del encuestado.";

        if (ayunas != null && !ayunas.isEmpty()) {
            if (ayunas.equals("0") ||ayunas.equals("1"))
                hm_preparacion.put("ayunas", ayunas);
            else
                return "No sabemos si esta en ayunas o no, selecciona por favor.";
        }
        else {
            return "No sabemos si esta en ayunas o no, selecciona por favor.";
        }

        codigo = et_cod_encuesta.getText().toString();
        if (!codigo.isEmpty() && codigo.matches("\\w{2}\\d{5}"))
            hm_preparacion.put("codigo", codigo);
        else
            return "No has ingresado el código o tiene un formato erróneo.";


        lugar = et_lugar.getText().toString();
        if (lugar.matches(".*\\w.*") && !lugar.isEmpty())
            hm_preparacion.put("lugar_encuesta", lugar);
        else
            return "No has ingresado el lugar de la encuesta aun.";

        if (!hora_encuesta.isEmpty())
            hm_preparacion.put("fecha_encuesta", hora_encuesta);
        else
            return "Algo salió mal con la fecha, intentalo de nuevo.";


        if (ruta_foto!=null && !ruta_foto.isEmpty())
            hm_preparacion.put("foto_consentimiento", ruta_foto);
        else
            return "Algo salió mal con la imagen o no la has tomado aun, intentalo de nuevo.";

        JSONObject json_preparacion = new JSONObject();
        jarray_datos = new JSONArray();
        temp1 = new JSONObject(hm_preparacion);
        jarray_datos.put(temp1);

        try {
            json_preparacion.put("id_formulario", codigo);
            json_preparacion.put("tipo_formulario", "Preparacion");
            json_preparacion.put("uuid_creado", UUID);
            json_preparacion.put("nombres_encuestador", nombres_encuestador);
            json_preparacion.put("cedula_encuestador", cedula_encuestador);
            json_preparacion.put("hora_creacion", hora_encuesta);
            json_preparacion.put("preparacion", jarray_datos);
            result= dir.guardarAchivo(json_preparacion.toString(),codigo,1);
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
            return "Excepcion del sistema, construyendo del json preparacion.";
        }
    }

}

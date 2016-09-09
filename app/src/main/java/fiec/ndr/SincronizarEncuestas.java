package fiec.ndr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;


public class SincronizarEncuestas extends AppCompatActivity {

    Directorios dir_ndr = new Directorios();
    static String direccion_post = "";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.activity_sincronizar_encuestas, null);
        prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
        direccion_post =  prefs.getString("ruta_servidor", "http://192.168.1.200:8000/recibirjson");

        List<File> encuestas_pre = getListFiles(new File("/storage/emulated/0" + dir_ndr.forms));
        List<File> encuestas_pre_img = getListFiles(new File("/storage/emulated/0" + dir_ndr.forms_fotos));
        List<File> encuestas_informacion = getListFiles(new File("/storage/emulated/0" + dir_ndr.forms_general));
        List<File> encuestas_medidas = getListFiles(new File("/storage/emulated/0" + dir_ndr.forms_medidas));
        List<File> encuestas_presion = getListFiles(new File("/storage/emulated/0" + dir_ndr.forms_presion));
        List<File> encuestas_laboratorios = getListFiles(new File("/storage/emulated/0" + dir_ndr.forms_laboratorio));

        //Lista de archivos de preparacion.
        if (!encuestas_pre.isEmpty()) {
            for (int i = 0; i < encuestas_pre.size(); i++) {
                View child = inflater.inflate(R.layout.entrada_archivo, null);
                TextView tv = (TextView) child.findViewById(R.id.txt_nombre);
                tv.setText(encuestas_pre.get(i).getName());
                LinearLayout principal = (LinearLayout) parent.findViewById(R.id.lyt_preparacion);
                principal.addView(child);

                final Button button = (Button) child.findViewById(R.id.btn_sincronizar);
                final File temp_file = encuestas_pre.get(i);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        subirachivos(temp_file,button);
                    }
                });
            }
        }
        else
            parent.findViewById(R.id.lyt_preparacion).setVisibility(View.GONE);

        //Lista de consentimientos informados.
        if (!encuestas_pre_img.isEmpty()) {
            for (int i = 0; i < encuestas_pre_img.size(); i++) {
                View child = inflater.inflate(R.layout.entrada_archivo, null);
                TextView tv = (TextView) child.findViewById(R.id.txt_nombre);
                tv.setText(encuestas_pre_img.get(i).getName());
                LinearLayout principal = (LinearLayout) parent.findViewById(R.id.lyt_consentimientos);
                principal.addView(child);

                final Button button = (Button) child.findViewById(R.id.btn_sincronizar);
                final File temp_file = encuestas_pre_img.get(i);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        subirachivos(temp_file,button);
                    }
                });
            }
        }
        else
            parent.findViewById(R.id.lyt_consentimientos).setVisibility(View.GONE);

        //Lista de archivos de informacion general
        if (!encuestas_informacion.isEmpty()) {
            for (int i = 0; i < encuestas_informacion.size(); i++) {
                View child = inflater.inflate(R.layout.entrada_archivo, null);
                TextView tv = (TextView) child.findViewById(R.id.txt_nombre);
                tv.setText(encuestas_informacion.get(i).getName());
                LinearLayout principal = (LinearLayout) parent.findViewById(R.id.lyt_inf_general);
                principal.addView(child);

                final Button button = (Button) child.findViewById(R.id.btn_sincronizar);
                final File temp_file = encuestas_informacion.get(i);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        subirachivos(temp_file,button);
                    }
                });
            }
        }
        else
            parent.findViewById(R.id.lyt_inf_general).setVisibility(View.GONE);

        //Lista de archivos de medidas.
        if (!encuestas_medidas.isEmpty()) {
            for (int i = 0; i < encuestas_medidas.size(); i++) {
                View child = inflater.inflate(R.layout.entrada_archivo, null);
                TextView tv = (TextView) child.findViewById(R.id.txt_nombre);
                tv.setText(encuestas_medidas.get(i).getName());
                LinearLayout principal = (LinearLayout) parent.findViewById(R.id.lyt_medidas);
                principal.addView(child);

                final Button button = (Button) child.findViewById(R.id.btn_sincronizar);
                final File temp_file = encuestas_medidas.get(i);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        subirachivos(temp_file,button);
                    }
                });
            }
        }
        else
            parent.findViewById(R.id.lyt_medidas).setVisibility(View.GONE);

        //Lista de archivos de presion arterial.
        if (!encuestas_presion.isEmpty()) {
            for (int i = 0; i < encuestas_presion.size(); i++) {
                View child = inflater.inflate(R.layout.entrada_archivo, null);
                TextView tv = (TextView) child.findViewById(R.id.txt_nombre);
                tv.setText(encuestas_presion.get(i).getName());
                LinearLayout principal = (LinearLayout) parent.findViewById(R.id.lyt_presion);
                principal.addView(child);

                final Button button = (Button) child.findViewById(R.id.btn_sincronizar);
                final File temp_file = encuestas_presion.get(i);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        subirachivos(temp_file,button);
                    }
                });
            }
        }
        else
            parent.findViewById(R.id.lyt_presion).setVisibility(View.GONE);

        //Lista de archivos de laboratorio.
        if (!encuestas_laboratorios.isEmpty()) {
            for (int i = 0; i < encuestas_laboratorios.size(); i++) {
                View child = inflater.inflate(R.layout.entrada_archivo, null);
                TextView tv = (TextView) child.findViewById(R.id.txt_nombre);
                tv.setText(encuestas_laboratorios.get(i).getName());
                LinearLayout principal = (LinearLayout) parent.findViewById(R.id.lyt_laboratorio);
                principal.addView(child);

                final Button button = (Button) child.findViewById(R.id.btn_sincronizar);
                final File temp_file = encuestas_laboratorios.get(i);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        subirachivos(temp_file,button);
                    }
                });
            }
        }
        else
            parent.findViewById(R.id.lyt_laboratorio).setVisibility(View.GONE);

        setContentView(parent);
        Toast.makeText(getApplicationContext(),direccion_post,Toast.LENGTH_LONG).show();
    }


    public void subirachivos(File archivo, final Button boton_sinc){

        RequestParams params = new RequestParams();
        try {
            params.put("archivo",archivo);
        } catch(FileNotFoundException e) {

        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(direccion_post, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "Empezando", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {

                AlertDialog.Builder alertDialog =new AlertDialog.Builder(SincronizarEncuestas.this);
                alertDialog.setTitle("Envío de Archios");
                alertDialog.setMessage("La encuesta ha sido correctamente recibida por el servidor, procederemos a borrar el archivo.");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.setIcon(android.R.drawable.ic_menu_upload).show();
                boton_sinc.setBackgroundResource(R.drawable.btn_rojo);
                boton_sinc.setText("SUBIDO");
                boton_sinc.setEnabled(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Bronza", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                Toast.makeText(getApplicationContext(), "Reintentando", Toast.LENGTH_LONG).show();
            }
        });

    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }
}

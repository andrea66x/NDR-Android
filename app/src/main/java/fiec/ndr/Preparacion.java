package fiec.ndr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class Preparacion extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageViewFoto;
    private TextView diaText, mesText, anioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Todos los datos han sido guardados!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.imageViewFoto = (ImageView)this.findViewById(R.id.fotoConsentimiento);

        imageViewFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            });

        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int anio = c.get(Calendar.YEAR);

        this.diaText = (TextView)this.findViewById(R.id.form_fecha_dia);
        this.mesText = (TextView)findViewById(R.id.form_fecha_mes);
        this.anioText = (TextView)findViewById(R.id.form_fecha_anio);

        this.diaText.setText(Integer.toString(dia));
        this.mesText.setText(Integer.toString(mes));
        this.anioText.setText(Integer.toString(anio));

    }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageViewFoto.setImageBitmap(photo);
            }
        }


}

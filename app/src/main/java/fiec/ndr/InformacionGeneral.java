package fiec.ndr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import fiec.ndr.Formularios.Frm_General;
import fiec.ndr.inf_general.AntecedentesFragment;
import fiec.ndr.inf_general.DatosFragment;
import fiec.ndr.inf_general.EconomiaFragment;
import fiec.ndr.inf_general.MedicamentosFragment;
import fiec.ndr.inf_general.SaludFragment;
import fiec.ndr.inf_general.SectionsPagerAdapter;


public class InformacionGeneral extends AppCompatActivity
        implements DatosFragment.changeTabDatos, EconomiaFragment.changeTabEconomia, SaludFragment.changeTabSalud,
        MedicamentosFragment.changeTabMedicamentos, AntecedentesFragment.changeTabAntecedentes{

    private SectionsPagerAdapter mSectionsPagerAdapter;//Adapter para las secciones
    private ViewPager mViewPager;   //Variable para el pageviewer
    private static Frm_General miFormulario; //Objeto para guardar la instancia del formulario
    private static String codigo;   //Variable para guardar el codigo del formulario


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_general);

        // Creamos el adapter que retornara un fragmento por cada una de las secciones de la actividad.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Configuramos el ViewPager con las secciones del adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Configuramos el TabLayout con el ViewPager.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Instanciamos el formulario.
        miFormulario = new Frm_General();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            codigo = (String) bundle.get("CODIGO");
        }
        setTitle("Información General: " + codigo);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No has completado aun todos los campos.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    /*Metodo para prevenir salir del formulario al presionar el boton atras*/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Salir")
                .setMessage("¿Estás seguro de querer salir?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        InformacionGeneral.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    public void onChangeTabDatos(Map<String, String> datos_inf_gen) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde datos personales.");
    }

    @Override
    public void onChangeTabEconomia(Map<String, String> datos_economia) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde economia familiar.");
    }

    @Override
    public void onChangeTabSalud(Map<String, String> datos_salud) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde salud.");
    }

    @Override
    public void onChangeTabMedicamentos(Map<String, String> datos_medicamentos) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde medicamentos.");
    }

    @Override
    public void onChangeTabAntecedentes(Map<String, String> datos_antecedentes) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde antecedentes.");
    }
}



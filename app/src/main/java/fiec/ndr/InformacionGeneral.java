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
import fiec.ndr.inf_general.HabitosFragment;
import fiec.ndr.inf_general.MedicamentosFragment;
import fiec.ndr.inf_general.SaludFragment;
import fiec.ndr.inf_general.SectionsPagerAdapter;
import fiec.ndr.inf_general.ViviendaFragment;


public class InformacionGeneral extends AppCompatActivity
        implements DatosFragment.changeTabDatos, EconomiaFragment.changeTabEconomia, SaludFragment.changeTabSalud,
        MedicamentosFragment.changeTabMedicamentos, AntecedentesFragment.changeTabAntecedentes,
        HabitosFragment.changeTabHabitos, ViviendaFragment.changeTabVivienda{

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
        /* Descripcion del Hashmap:
        *  hashmap:         "personales"
        *  nombres:         Nombres del encuestado.
        *  apellidos:       Apellidos del encuestado.
        *  sexo:            0 para masculino, 1 para femenino.
        *  fecha_nac:       mm-dd-aaaa.
        *  edad:            Edad en años.
        *  telefono:        Telefono del encuestado.
        *  estado_civil:    Estado Civil del encuestado.
        *  etnia:           Etnia del encuestado.
        */
    }

    @Override
    public void onChangeTabVivienda(Map<String, String> datos_habitos) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde Vivienda.");
        /* Descripcion del Hashmap:
        *  hashmap:         "vivienda"
        *  provincia:       Provincia del encuestado.
        *  canton:          Canton del encuestado.
        *  vivienda:        Donde se ubica la vivienda del encuestado.
        *  direccion:       Direccion del encuestado.
        *  personas:        Numero de personas que viven junto al encuestado.
        *  agua:            Como llega el agua.
        *  cloacas:         0 si no tiene, 1 si tiene.
        */

    }

    @Override
    public void onChangeTabEconomia(Map<String, String> datos_economia) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde economia familiar.");
        /* Descripcion del Hashmap:
        *  hashmap:         "economia"
        *  cbzfam:          Cabeza de familia, 0 si no es, 1 si lo es.
        *  ingresos:        Ingresos Propios, 0 si no tiene, 1 si tiene.
        *  llegafin:        Si llega a fin de mes, 0 si no llega, 1 si llega.
        *  ocupacion:       Ocupacion del encuestado.
        *  trabajo:         Trabajo del encuestado.
        *  estudios:        Nivel de estudios del encuestado.
        */

    }

    @Override
    public void onChangeTabSalud(Map<String, String> datos_salud) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde salud.");
        /* Descripcion del Hashmap:
        *  hashmap:         "salud"
        *  seguro:              Posee seguro, 0 si no posee, 1 si posee.
        *  det_seguro:          En caso de seleccionar que si, cual seguro. (No Requerido)
        *  chequeos:            Se realiza chequeos, 0 si no, 1 si los realiza.
        *  det_vec_chequeo:     Frecuencia de chequeos cada "n" meses. (No Requerido)
        *  det_mes_chequeo:     "n" meses. (No Requerido)
        *  diabetes:            Tiene diabetes, 0 si no tiene, 1 si tiene.
        *  det_tmp_diabetes:    Años que tiene diabetes. (No Requerido)
        *  presion:             Tiene presion alta, 0 si no tiene, 1 si tiene.
        *  renal:               Tiene una enfermedad renal, 0 si no, 1 si la tiene.
        *  det_renal:           En caso de seleccionar que si, cual enfermedad tiene. (No Requerido)
        *  enfermedad:          Tiene otra enfermedad, 0 si no, 1 si la tiene.
        *  det_enfermedad:      En caso de seleccionar que si, cual enfermedad tiene. (No Requerido)
        */

    }

    @Override
    public void onChangeTabMedicamentos(Map<String, String> datos_medicamentos) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde medicamentos.");
        /* Descripcion del Hashmap:
        *  hashmap:                 "medicamentos"
        *  insulina:                Toma insulina, 0 si no toma, 1 si toma.
        *  hipoglucemias:           Toma hipoglucemias orales, 0 si no toma, 1 si toma.
        *  det_hipoglucemias:       En caso de seleccionar que si, cual hipoglucemias. (No Requerido)
        *
        *                           *Razones que tiene el encuestado si no toma medicinas teniendo diabetes.
        *  razon_1:                 Desconfianza, 1 si cree que es una razon, 0 si no.
        *  razon_2:                 Economicos, 1 si cree que es una razon, 0 si no.
        *  razon_3:                 Conocimiento, 1 si cree que es una razon, 0 si no.
        *  razon_4:                 Difícil acceso al tratamiento, 1 si cree que es una razon, 0 si no.
        *
        *  medicina_presion:        Toma medicinas para la presion, 0 si no toma, 1 si toma.
        *  det_medicina_presion:    En caso de seleccionar que si, cual medicina. (No Requerido)
        *  analgesicos:             Toma analgesicos, 0 si no toma, 1 si toma.
        *  det_analgesicos:         En caso de seleccionar que si, cuales analgesicos. (No Requerido)
        *  medicinas_otros:         Toma otras medicinas, 0 si no toma, 1 si toma.
        *  det_medicinas_otros:     En caso de seleccionar que si, cuales otras medicinas. (No Requerido)
        *
        *                           *Opciones de medicinas que deseamos saber si toma.
        *  med_1:                   Iberstan, 0 si no toma, 1 si toma.
        *  med_2:                   Enalapril, 0 si no toma, 1 si toma.
        *  med_3:                   Captopril, 0 si no toma, 1 si toma.
        */

    }

    @Override
    public void onChangeTabAntecedentes(Map<String, String> datos_antecedentes) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde antecedentes.");
        /* Descripcion del Hashmap:
        *  hashmap:                 "antecedentes"
        *  ant_glucosa:             Ha tenido glucosa en la sangre, 0 si no, 1 si ha tenido.
        *  ant_familia:             Familiares directos tienen diabetes, 0 si no, 1 si tienen.
        *  ant_parientes:           Familiares indirectos tienen diabetes, 0 si no, 1 si tienen.
        *  ant_embarazo:            Tuvo diabetes en el emabarazo, 0 si no, 1 si lo tuvo. (No Requerido)
        *  ant_pso_hijos:           Tuvo hijos  de mas de 4kg al nacer, 0 si no, 1 si lo tuvo. (No Requerido)
        *  ant_presion:             Tiene familiares con presion alta, 0 si no, 1 si tienen.
        *  ant_renal:               Tiene familiares con enfermedades renales, 0 si no, 1 si los tienen.
        *  det_ant_enf_renal:       Cual enfermedad renal.
        */

    }

    @Override
    public void onChangeTabHabitos(Map<String, String> datos_habitos) {
        Log.d("INTERFACE CALL","LLame correctamente la funcion desde habitos.");
         /* Descripcion del Hashmap:
        *  hashmap:                 "habitos"
        *  tabaco:                  Es fumador, 0 si no, 1 si.
        *  det_frc_tabaco:          Cuantas veces al dia fuma.
        *  alcohol:                 Es bebedor, 0 si no, 1 si.
        *  det_frc_alcohol:         Cuantas veces a la semana toma.
        *  otros:                   Tiene otro vicio, 0 si no, 1 si.
        *  det_frc_otros:           Que otro vicio.
        *  ejercicios:              Que ejercicios realiza el encuestado.
        */
    }
}



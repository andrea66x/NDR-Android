package fiec.ndr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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

    SectionsPagerAdapter mSectionsPagerAdapter;//Adapter para las secciones
    ViewPager mViewPager;   //Variable para el pageviewer
    private String codigo, UUID, hora_encuesta;   //Variable para guardar el codigo del formulario

    private Map<String, String> hm_datos, hm_vivienda, hm_economia, hm_salud, hm_medicamentos, hm_antecedentes, hm_habitos;
    private boolean validador_json, faltan_campos;

    int result;

    public int sexo=-1, tiene_diabetes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_general);

        // Creamos el adapter que retornara un fragmento por cada una de las secciones de la actividad.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Configuramos el ViewPager con las secciones del adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Configuramos el TabLayout con el ViewPager.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            codigo = (String) bundle.get("CODIGO");
        }
        setTitle("Información General: " + codigo);

        validador_json = false;

    }

    public void constructJSON()
    {
        JSONArray jarray_datos, jarray_vivienda, jarray_economia, jarray_salud, jarray_medicamentos, jarray_antecedentes, jarray_habitos;
        JSONObject temp1, temp2, temp3, temp4, temp5, temp6, temp7;
        jarray_datos = new JSONArray();
        jarray_vivienda = new JSONArray();
        jarray_economia = new JSONArray();
        jarray_salud = new JSONArray();
        jarray_medicamentos = new JSONArray();
        jarray_antecedentes = new JSONArray();
        jarray_habitos = new JSONArray();

        //Obtenemos el UUID del dispositivo desde que ha sido creado el JSON.
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        UUID = tManager.getDeviceId();

        TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d yyyy, h:mm a", Locale.US);
        hora_encuesta = format.format(calendar.getTime());

        JSONObject JSON_Formulario = new JSONObject();
        if(codigo!=null && !codigo.isEmpty()) {
            if (UUID != null && !UUID.isEmpty()){
                if (hora_encuesta != null && !hora_encuesta.isEmpty()) {
                    if (hm_datos!= null) {
                        temp1 = new JSONObject(hm_datos);
                        jarray_datos.put(temp1);
                        if (hm_vivienda!= null) {
                            temp2 = new JSONObject(hm_vivienda);
                            jarray_vivienda.put(temp2);
                            if (hm_economia!= null) {
                                temp3 = new JSONObject(hm_economia);
                                jarray_economia.put(temp3);
                                if (hm_salud!= null) {
                                    temp4 = new JSONObject(hm_salud);
                                    jarray_salud.put(temp4);
                                    if (hm_medicamentos!= null) {
                                        temp5 = new JSONObject(hm_medicamentos);
                                        jarray_medicamentos.put(temp5);
                                        if (hm_antecedentes!= null) {
                                            temp6 = new JSONObject(hm_antecedentes);
                                            jarray_antecedentes.put(temp6);
                                            if (hm_habitos!= null) {
                                                temp7 = new JSONObject(hm_habitos);
                                                jarray_habitos.put(temp7);
                                                validador_json = true;
                                            }
                                            else
                                                validador_json= false;
                                        }
                                        else
                                            validador_json= false;
                                    }
                                    else
                                        validador_json= false;
                                }
                                else
                                    validador_json= false;
                            }
                            else
                                validador_json= false;
                        }
                        else
                            validador_json= false;
                    }
                    else
                        validador_json= false;
                }
                else
                    validador_json= false;
            }
            else
                validador_json= false;
        }
        else
            validador_json= false;

        String campos = revisarCampos();
        if (!faltan_campos){
            if(validador_json){
                try {
                    JSON_Formulario.put("id_formulario", codigo);
                    JSON_Formulario.put("tipo_formulario", "Informacion_General");
                    JSON_Formulario.put("uuid_creado", UUID);
                    JSON_Formulario.put("hora_creacion", hora_encuesta);
                    JSON_Formulario.put("informacion_general", jarray_datos);
                    JSON_Formulario.put("vivienda", jarray_vivienda);
                    JSON_Formulario.put("economia_familiar", jarray_economia);
                    JSON_Formulario.put("salud", jarray_salud);
                    JSON_Formulario.put("medicamentos", jarray_medicamentos);
                    JSON_Formulario.put("antecedentes", jarray_antecedentes);
                    JSON_Formulario.put("habitos", jarray_habitos);
                    Directorios dir = new Directorios(false);
                    result= dir.guardarAchivo(JSON_Formulario.toString(),codigo,2);
                    if (result == 1)
                        Toast.makeText(getApplicationContext(), "El formulario "+ codigo + " ha sido guardado exitosamente.", Toast.LENGTH_SHORT).show();
                    else if (result == 0)
                        Toast.makeText(getApplicationContext(), "El formulario asociado a este codigo: " + codigo +" ya existe", Toast.LENGTH_SHORT).show();
                    else if (result == -1)
                        Toast.makeText(getApplicationContext(), "Existe un problema con tu sistemas de archivos, llama a sistemas ahora.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Algo raro ha pasado, intenta de nuevo por favor.", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Toast.makeText(getApplicationContext(),
                        "Aun no estamos listos para guardar, revisa nuevamente", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),
                    "Faltan los campos: \n" + campos, Toast.LENGTH_LONG).show();



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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(InformacionGeneral.this);

                switch (mViewPager.getCurrentItem()){
                    case 0:
                        alertDialog.setTitle("Ayuda Datos Personales:");
                        alertDialog.setMessage(
                                "- Debes ingresar los nombres y apellidos completos del encuestado. \n\n" +
                                        "- El sexo determinara algunas opciones mas adelante, no olvides seleccionarlo. \n\n" +
                                        "- La fecha de nacimiento tiene el orden: año, mes y dia. \n\n" +
                                        "- Ingresa solamente un telefono, el mas importante. \n\n" +
                                        "- El estado civil es obligatorio, se prudente al solicitarlo. \n\n" +
                                        "- Exponle los tipos de etnia que estan disponibles para que el encuestado se decida por uno. \n");
                        break;
                    case 1:
                        alertDialog.setTitle("Ayuda Vivienda:");
                        alertDialog.setMessage(
                                "- La provincia y el cantón son los de residencia actual. \n\n" +
                                        "- La provincia, el cantón, donde se ubica la vivienda y la dirección,son los de residencia actual. \n\n" +
                                        "- Debes ingresar el numero total de personas con las que vive incluyendolo. \n\n" +
                                        "- La manera que llega el agua al hogar se refiere al suministro de agua potable.\n\n" +
                                        "- Cloacas se refiere al servicio higienico.\n" );
                        break;
                    case 2:
                        alertDialog.setTitle("Ayuda Economía Familiar:");
                        alertDialog.setMessage(
                                "- Cabeza de familia se refiere a si sostiene el hogar. \n\n" +
                                        "- Ingresos propios, percibe sueldo. \n\n" +
                                        "- Llega a fin de mes, si le alcanza sus ingresos para sus necesidades mes a mes. \n\n" +
                                        "- Si seleccion que no trabaja o alguna opcion similar, entonces no necesita llenar de que trabaja. \n\n" +
                                        "- Estudios, se refiere al nivel que estudios que posee actualmente. \n");
                        break;
                    case 3:
                        alertDialog.setTitle("Ayuda Salud:");
                        alertDialog.setMessage(
                                "- Debes ingresar solamente un seguro medio, el principal. \n\n" +
                                        "- El formato de chequeos de salud es Cuantas veces cada cuantos meses.\nEj: 2 veces cada 5 meses. \n\n" +
                                        "- Si desconoce algun dato solicitado, colocar no. \n\n" +
                                        "- En algunas opciones seleccionar si, despliega un nuevo cuadro de texto para dar información detallada. \n");
                        break;
                    case 4:
                        alertDialog.setTitle("Ayuda Medicamentos:");
                        alertDialog.setMessage(
                                "- Si tiene diabetes y no toma ni insulina, ni hipoglucemias, seleccione la razon por la que no las toma. \n\n" +
                                        "- Las opciones de Ibertasan, Enalapril y Captopril son medicamentos para la presión arterial. \n" +
                                        "Estan presentadas en sus nombres tecnicos, pueden tener otros nombres genericos.\n\n" +
                                        "- La opcion de otro medicamento es ideal para colocar un medicamento que no sepa ubicar en ninguna categoria expuesta. \n");
                        break;
                    case 5:
                        alertDialog.setTitle("Ayuda Antecedentes:");
                        alertDialog.setMessage(
                                "- Los familiares directos se refiere al nucleo familiar, esposo o esposa e hijos. \n\n" +
                                        "- Los demas familiares son: tios, primos, abuelos, etc. \n");
                        break;
                    case 6:
                        alertDialog.setTitle("Ayuda Hábitos:");
                        alertDialog.setMessage(
                                "- La frecuencia de consumo de tabaco es diaria, no incluye el uso de drogas como la marihuana. \n Ej: 4 tabacos al día.\n\n" +
                                        "- La frecuencia de consumo de alcohol es semanal, en cualquier presentación. \n Ej: 5 veces a la semana.\n\n" +
                                        "- Otros, se refiere a otros tipos de estupefacientes, sea prudente al consultar esto. \n");
                        break;
                    default:
                        alertDialog.setTitle("Ayuda Informacion General:");
                        alertDialog.setMessage(
                                "- Estas en el activity de información general, HACKER!");
                        break;
                }

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

    public String revisarCampos(){
        Iterator it;
        String campos_faltantes = "";
        String campos_datos = "Datos: ", campos_vivienda = "Vivienda: ";
        String campos_economia = "Economia Familiar: ", campos_salud = "Salud: ";
        String campos_medicamentos = "Medicamentos: ", campos_antecedentes="Antecedentes: ", campos_habitos = "Habitos: ";
        faltan_campos = false;
        result = 2;

        it = hm_datos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                campos_datos = campos_datos + pair.getKey().toString() + " - ";
                faltan_campos = true;
            }
        }
        if (campos_datos.endsWith("- ")) {
            campos_datos = campos_datos.substring(0, campos_datos.length() - 2);
        }

        it = hm_vivienda.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getValue().equals("-1")){
                campos_vivienda =  campos_vivienda + pair.getKey().toString() + " - ";
                faltan_campos = true;
            }
        }
        if (campos_vivienda.endsWith("- ")) {
            campos_vivienda = campos_vivienda.substring(0, campos_vivienda.length() - 2);
        }

        it = hm_economia.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                campos_economia = campos_economia + pair.getKey().toString() + " - ";
                faltan_campos = true;
            }
        }
        if (campos_economia.endsWith("- ")) {
            campos_economia = campos_economia.substring(0, campos_economia.length() - 2);
        }

        it = hm_salud.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                campos_salud = campos_salud + pair.getKey().toString() + " - ";
                faltan_campos = true;
            }
        }
        if (campos_salud.endsWith("- ")) {
            campos_salud = campos_salud.substring(0, campos_salud.length() - 2);
        }

        it = hm_medicamentos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                campos_medicamentos = campos_medicamentos + pair.getKey().toString() + " - ";
                faltan_campos = true;
            }
        }
        if (campos_medicamentos.endsWith("- ")) {
            campos_medicamentos = campos_medicamentos.substring(0, campos_medicamentos.length() - 2);
        }

        it = hm_antecedentes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                campos_antecedentes = campos_antecedentes + pair.getKey().toString() + " - ";
                faltan_campos = true;
            }
        }
        if (campos_antecedentes.endsWith("- ")) {
            campos_antecedentes = campos_antecedentes.substring(0, campos_antecedentes.length() - 2);
        }

        it = hm_habitos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                campos_habitos = campos_habitos + pair.getKey().toString() + " - ";
                faltan_campos = true;
            }
        }
        if (campos_habitos.endsWith("- ")) {
            campos_habitos = campos_habitos.substring(0, campos_habitos.length() - 2);
        }


        if(faltan_campos){
            if (!campos_datos.endsWith("Datos: ")) {
                campos_faltantes = campos_faltantes + campos_datos + "\n\n";
            }
            if (!campos_vivienda.endsWith("Vivienda: ")) {
                campos_faltantes = campos_faltantes + campos_vivienda + "\n\n";
            }
            if (!campos_economia.endsWith("Economia Familiar: ")) {
                campos_faltantes = campos_faltantes + campos_economia + "\n\n";
            }
            if (!campos_salud.endsWith("Salud: ")) {
                campos_faltantes = campos_faltantes + campos_salud + "\n\n";
            }
            if (!campos_medicamentos.endsWith("Medicamentos: ")) {
                campos_faltantes = campos_faltantes + campos_medicamentos + "\n\n";
            }
            if (!campos_antecedentes.endsWith("Antecedentes: ")) {
                campos_faltantes = campos_faltantes + campos_antecedentes + "\n\n";
            }
            if (!campos_habitos.endsWith("Habitos: ")) {
                campos_faltantes = campos_faltantes + campos_habitos;
            }

        }
        else
            campos_faltantes = "";
        return campos_faltantes;
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
        hm_datos = new HashMap<String, String>();
        hm_datos = datos_inf_gen;
        sexo = Integer.valueOf(hm_datos.get("sexo"));
    }

    @Override
    public void onChangeTabVivienda(Map<String, String> datos_vivienda) {
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
        hm_vivienda = new HashMap<String, String>();
        hm_vivienda = datos_vivienda;
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
        hm_economia = new HashMap<String, String>();
        hm_economia = datos_economia;

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
        hm_salud = new HashMap<String, String>();
        hm_salud = datos_salud;
        tiene_diabetes = Integer.valueOf(hm_salud.get("diabetes"));

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
        hm_medicamentos = new HashMap<String, String>();
        hm_medicamentos = datos_medicamentos;

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
        hm_antecedentes = new HashMap<String, String>();
        hm_antecedentes = datos_antecedentes;
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
        hm_habitos = new HashMap<String, String>();
        hm_habitos = datos_habitos;
        constructJSON();
    }
}



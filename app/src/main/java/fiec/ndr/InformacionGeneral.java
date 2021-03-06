package fiec.ndr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    String nombres_encuestador, cedula_encuestador;
    SharedPreferences prefs;
    int result, tabFaltante;
    public int sexo=-1, tiene_diabetes;
    FloatingActionButton fab;


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

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (faltan_campos){
                    Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + mViewPager.getCurrentItem());
                    if (page != null) {
                        switch (mViewPager.getCurrentItem()){
                            case 0:
                                ((DatosFragment)page).setearHash();
                                break;
                            case 1:
                                ((ViviendaFragment)page).setearHash();
                                break;
                            case 2:
                                ((EconomiaFragment)page).setearHash();
                                break;
                            case 3:
                                ((SaludFragment)page).setearHash();
                                break;
                            case 4:
                                ((MedicamentosFragment)page).setearHash();
                                break;
                            case 5:
                                ((AntecedentesFragment)page).setearHash();
                                break;
                            case 6:
                                ((HabitosFragment)page).setearHash();
                                break;
                            default:
                                break;
                        }
                    }
                }
                Snackbar.make(view, constructJSON(), Snackbar.LENGTH_LONG).setCallback(new Snackbar.Callback() {
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


        validador_json = false;

        prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
        nombres_encuestador = prefs.getString("nombre_encuestador", "") + "" +  prefs.getString("apellido_encuestador", "");
        cedula_encuestador =  prefs.getString("cedula_encuestador", "");

        AlertDialog.Builder alertDialog =new AlertDialog.Builder(InformacionGeneral.this);
        alertDialog.setTitle("Ayuda Inicial");
        alertDialog.setMessage("Usa el gesto de deslizar tus dedos sobre la pantalla para desplazarte por el formulario.");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setIcon(android.R.drawable.ic_menu_upload).show();

    }


    public String constructJSON()
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
                    JSON_Formulario.put("nombres_encuestador", nombres_encuestador);
                    JSON_Formulario.put("cedula_encuestador", cedula_encuestador);
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
                        return "El formulario "+ codigo + " ha sido guardado exitosamente.";
                    else if (result == 0)
                        return "El formulario asociado a este codigo: " + codigo +" ya existe";
                    else if (result == -1)
                        return  "Existe un problema con tu sistemas de archivos, llama a sistemas ahora.";
                    else
                        return  "Algo raro ha pasado, intenta de nuevo por favor.";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                return "Aun no estamos listos para guardar, revisa nuevamente";
        }
        else{
            mViewPager.setCurrentItem(tabFaltante);
            return "Falta el campo: \n" + camposStyle(campos);
        }

        return "";
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
        faltan_campos = false;
        result = 2;
        tabFaltante = 0;
        String campo= "";

        it = hm_datos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                tabFaltante =0;
                faltan_campos = true;
                return pair.getKey().toString();
            }
        }
        it = hm_vivienda.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getValue().equals("-1")){
                tabFaltante =1;
                faltan_campos = true;
                return pair.getKey().toString();
            }
        }

        it = hm_economia.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                tabFaltante =2;
                faltan_campos = true;
                return pair.getKey().toString();
            }
        }


        it = hm_salud.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                tabFaltante =3;
                faltan_campos = true;
                return pair.getKey().toString();
            }
        }

        it = hm_medicamentos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                tabFaltante =4;
                faltan_campos = true;
                return pair.getKey().toString();
            }
        }


        it = hm_antecedentes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                tabFaltante =5;
                faltan_campos = true;
                return pair.getKey().toString();
            }
        }

        it = hm_habitos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("-1")) {
                tabFaltante =6;
                faltan_campos = true;
                return pair.getKey().toString();
            }
        }
        return campo;
    }

    public String camposStyle(String campo){
        String descripcion= "";
        switch (campo){
            case "nombres":
                descripcion = "Nombres del Encuestado.";
                break;
            case "apellidos":
                descripcion = "Apellidos del Encuestado.";
                break;
            case "sexo":
                descripcion = "Sexo del Encuestado.";
                break;
            case "fecha_nac":
                descripcion = "Fecha de Nacimiento del Encuestado.";
                break;
            case "telefono":
                descripcion = "Telefono del Encuestado.";
                break;
            case "estado_civil":
                descripcion = "Estado Civil del Encuestado.";
                break;
            case "provincia":
                descripcion = "Provincia de residencia actual.";
                break;
            case "canton":
                descripcion = "Cantón de residencia actual.";
                break;
            case "vivienda":
                descripcion = "¿Donde esta su vivienda?.";
                break;
            case "direccion":
                descripcion = "Dirección del domicilio del encuestado.";
                break;
            case "personas":
                descripcion = "¿Con cuántas personas vive?";
                break;
            case "agua":
                descripcion = "¿Cómo llega el agua a su hogar?";
                break;
            case "cloacas":
                descripcion = "¿Posee Cloacas? (Servicios Sanitarios)";
                break;
            case "cbzfam":
                descripcion = "¿Es usted cabeza de familia?";
                break;
            case "ingresos":
                descripcion = "¿Tiene ingresos propios?";
                break;
            case "llegafin":
                descripcion = "¿Llega a fin de mes?";
                break;
            case "ocupacion":
                descripcion = "Ocupación del encuestado";
                break;
            case "trabajo":
                descripcion = "¿De qué trabaja?";
                break;
            case "estudios":
                descripcion = "Estudios del encuestado";
                break;
            case "seguro":
                descripcion = "¿Tiene seguro médico?";
                break;
            case "det_seguro":
                descripcion = "Descripción del seguro medico que posee.";
                break;
            case "chequeos":
                descripcion = "¿Hace chequeos de su salud?";
                break;
            case "det_mes_chequeo":
                descripcion = "¿Cada cuantos meses se chequea?";
                break;
            case "det_vec_chequeo":
                descripcion = "¿Cuantas veces se chequea en su periodo?";
                break;
            case "diabetes":
                descripcion = "¿Tiene diabetes?";
                break;
            case "det_tmp_diabetes":
                descripcion = "¿Hace cuántos años?";
                break;
            case "presion":
                descripcion = "¿Tiene presión alta?";
                break;
            case "renal":
                descripcion = "¿Tiene enfermedad renal?";
                break;
            case "det_renal":
                descripcion = "¿Que enfermedad renal tiene?";
                break;
            case "enfermedad":
                descripcion = "¿Tiene otra enfermedad?";
                break;
            case "det_enfermedad":
                descripcion = "¿Qué otra enfermedad tiene?";
                break;
            case "insulina":
                descripcion = "¿Toma insulina?";
                break;
            case "hipoglucemias":
                descripcion = "¿Toma hipoglucemias orales?";
                break;
            case "det_hipoglucemias":
                descripcion = "¿Cuáles hipoglucemias toma?";
                break;
            case "medicina_presion":
                descripcion = "¿Toma medicinas para la presión arterial?";
                break;
            case "det_medicina_presion":
                descripcion = "¿Cuál medicina para la presión toma?";
                break;
            case "analgesicos":
                descripcion = "¿Toma analgesicos o aspirinas?";
                break;
            case "det_analgesicos":
                descripcion = "¿Cuál analgesico toma?";
                break;
            case "medicinas_otros":
                descripcion = "¿Toma otro medicamento?";
                break;
            case "det_medicinas_otros":
                descripcion = "¿Cuál otro medicamento toma?";
                break;
            case "ant_glucosa":
                descripcion = "¿Ha tenido glucosa alta en la sangre?";
                break;
            case "ant_familia":
                descripcion = "¿Sus familiares directos tienen diabetes?";
                break;
            case "ant_parientes":
                descripcion = "¿Sus demás familiares tienen diabetes?";
                break;
            case "ant_embarazo":
                descripcion = "¿Tuvo diabetes en el embarazo?";
                break;
            case "ant_pso_hijos":
                descripcion = "¿Tuvo hijos de mas de 4kg al nacer?";
                break;
            case "ant_presion":
                descripcion = "¿Tiene familiares con presión arterial alta?";
                break;
            case "ant_renal":
                descripcion = "¿Sus familiares directos tienen alguna enfermedad renal?";
                break;
            case "det_ant_enf_renal":
                descripcion = "¿Cuál enfermedad renal tienen?";
                break;
            case "tabaco":
                descripcion = "¿Consume Tabaco?";
                break;
            case "det_frc_tabaco":
                descripcion = "¿Cuántos tabacos al dia consume?";
                break;
            case "alcohol":
                descripcion = "¿Alcohol?";
                break;
            case "det_frc_alcohol":
                descripcion = "¿Cuántas veces en la semana lo ingiere?";
                break;
            case "otros":
                descripcion = "¿Otros?";
                break;
            case "det_frc_otros":
                descripcion = "¿Cuál otro hábito?";
                break;
            case "ejercicios":
                descripcion = "¿Ejercicios?";
                break;
            default:
                descripcion = campo;
                break;
        }
        return descripcion;
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
        if(hm_datos!=null && hm_vivienda!=null && hm_economia!=null && hm_salud!=null && hm_medicamentos!=null && hm_antecedentes!=null && hm_habitos!=null)
            fab.show();
        else
            fab.hide();
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
        if(hm_datos!=null && hm_vivienda!=null && hm_economia!=null && hm_salud!=null && hm_medicamentos!=null && hm_antecedentes!=null && hm_habitos!=null)
            fab.show();
        else
            fab.hide();
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
        if(hm_datos!=null && hm_vivienda!=null && hm_economia!=null && hm_salud!=null && hm_medicamentos!=null && hm_antecedentes!=null && hm_habitos!=null)
            fab.show();
        else
            fab.hide();
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
        if(hm_datos!=null && hm_vivienda!=null && hm_economia!=null && hm_salud!=null && hm_medicamentos!=null && hm_antecedentes!=null && hm_habitos!=null)
            fab.show();
        else
            fab.hide();

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
        if(hm_datos!=null && hm_vivienda!=null && hm_economia!=null && hm_salud!=null && hm_medicamentos!=null && hm_antecedentes!=null && hm_habitos!=null)
            fab.show();
        else
            fab.hide();

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
        if(hm_datos!=null && hm_vivienda!=null && hm_economia!=null && hm_salud!=null && hm_medicamentos!=null && hm_antecedentes!=null && hm_habitos!=null)
            fab.show();
        else
            fab.hide();
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
        if(hm_datos!=null && hm_vivienda!=null && hm_economia!=null && hm_salud!=null && hm_medicamentos!=null && hm_antecedentes!=null && hm_habitos!=null)
            fab.show();
        else
            fab.hide();
    }
}



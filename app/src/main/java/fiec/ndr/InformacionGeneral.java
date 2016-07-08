package fiec.ndr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class InformacionGeneral extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static Formulario miFormulario;
    private static String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_general);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        miFormulario = new Formulario();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
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

    /* Fragmento Datos*/
    public static class DatosFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private ImageButton btn_calendario;
        private Calendar cal;
        private int day;
        private int month;
        private int year;
        private EditText dato_fecha_nac;
        static final int DATE_DIALOG_ID = 0;

        static EditText et_nombres;
        static EditText et_apellidos;
        static Button btn_guardar;
        TextView tv_codigo;


        public DatosFragment() {
        }

        public static DatosFragment newInstance(int sectionNumber) {
            DatosFragment fragment = new DatosFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_datos_personales, container, false);

            /*Datos Personales*/
            et_nombres = (EditText) rootView.findViewById(R.id.datos_nombres);
            et_apellidos = (EditText) rootView.findViewById(R.id.datos_apellidos);

            btn_calendario=(ImageButton) rootView.findViewById(R.id.btn_dato_fecha);
            cal = Calendar.getInstance();
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
            dato_fecha_nac = (EditText)rootView.findViewById(R.id.datos_fecha);
            btn_calendario.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Show the DatePickerDialog
                    Toast.makeText(v.getContext(),"Pronto Tendremos el Calendario",Toast.LENGTH_SHORT).show();
                }
            });

            btn_guardar = (Button) rootView.findViewById(R.id.btn_GuardarDP);
            btn_guardar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String json = convertToJson();
                    Toast.makeText(v.getContext(),json,Toast.LENGTH_SHORT).show();
                }
            });

            // Spinner element
            Spinner spinner_estado_civil = (Spinner) rootView.findViewById(R.id.datos_estado_civil);
            // Spinner Drop down elements
            List<String> estados = new ArrayList<>();
            estados.add("Soltero");
            estados.add("Casado");
            estados.add("Divorciado");
            estados.add("Unión Libre");
            estados.add("Viudo");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, estados);

            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(R.layout.spinners);

            // attaching data adapter to spinner
            spinner_estado_civil.setAdapter(dataAdapter1);

            // Spinner element
            Spinner spinner_etnia = (Spinner) rootView.findViewById(R.id.datos_etnia);
            // Spinner Drop down elements
            List<String> etnias = new ArrayList<>();
            etnias.add("Afroecuatoriano");
            etnias.add("Blanco");
            etnias.add("Indio");
            etnias.add("Mestizo");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, etnias);

            // Drop down layout style - list view with radio button
            dataAdapter2.setDropDownViewResource(R.layout.spinners);

            // attaching data adapter to spinner
            spinner_etnia.setAdapter(dataAdapter2);

            return rootView;
        }

        @Override
        public void onPause(){
            super.onPause();
            miFormulario.setNombres(et_nombres.getText().toString());
            miFormulario.setApellidos(et_apellidos.getText().toString());

        }

        public String convertToJson(){
            JSONObject miJson = new JSONObject();
            try{
                miJson.put("tipo","datos_personales");
                miJson.put("nombres",et_nombres.getText());
                miJson.put("apellidos",et_apellidos.getText());
            } catch (JSONException e){
                e.printStackTrace();
            }

            String json = miJson.toString();
            return json;
        }

    }

    /* Fragmento Vivienda*/
    public static class ViviendaFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public ViviendaFragment() {
        }

        public static ViviendaFragment newInstance(int sectionNumber) {
            ViviendaFragment fragment = new ViviendaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_vivienda, container, false);

            //Spinner para la provincia:
            Spinner spinner_provincia = (Spinner) rootView.findViewById(R.id.datos_provincia);
            List<String> provincias = new ArrayList<>();
            provincias.add("Guayas");
            provincias.add("Esmeraldas");
            provincias.add("Los Ríos");
            provincias.add("El Oro");
            provincias.add("Manabí");
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, provincias);
            dataAdapter1.setDropDownViewResource(R.layout.spinners);
            spinner_provincia.setAdapter(dataAdapter1);

            //Spinner para el canton:
            Spinner spinner_canton = (Spinner) rootView.findViewById(R.id.datos_canton);
            List<String> cantones = new ArrayList<>();
            cantones.add("Balzar");
            cantones.add("Guayaquil");
            cantones.add("Manta");
            cantones.add("Portoviejo");
            cantones.add("Esmeraldas");
            cantones.add("Balzar");
            cantones.add("Guayaquil");
            cantones.add("Manta");
            cantones.add("Portoviejo");
            cantones.add("Esmeraldas");
            cantones.add("Balzar");
            cantones.add("Guayaquil");
            cantones.add("Manta");
            cantones.add("Portoviejo");
            cantones.add("Esmeraldas");
            cantones.add("Balzar");
            cantones.add("Guayaquil");
            cantones.add("Manta");
            cantones.add("Portoviejo");
            cantones.add("Esmeraldas");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, cantones);
            dataAdapter2.setDropDownViewResource(R.layout.spinners);
            spinner_canton.setAdapter(dataAdapter2);

            //Spinner para la vivienda:
            Spinner spinner_vivienda = (Spinner) rootView.findViewById(R.id.datos_vivienda);
            List<String> vivienda = new ArrayList<>();
            vivienda.add("Ciudad");
            vivienda.add("Campo");
            vivienda.add("Alrededor de la Ciudad");
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, vivienda);
            dataAdapter3.setDropDownViewResource(R.layout.spinners);
            spinner_vivienda.setAdapter(dataAdapter3);

            //Spinner para el agua:
            Spinner spinner_agua = (Spinner) rootView.findViewById(R.id.datos_agua);
            List<String> agua = new ArrayList<>();
            agua.add("Intubada");
            agua.add("Cisterna");
            agua.add("Tanquero");
            ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, agua);
            dataAdapter4.setDropDownViewResource(R.layout.spinners);
            spinner_agua.setAdapter(dataAdapter4);

            return rootView;
        }
    }

    /* Fragmento Economia Familiar*/
    public static class EconomiaFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public EconomiaFragment() {
        }

        public static EconomiaFragment newInstance(int sectionNumber) {
            EconomiaFragment fragment = new EconomiaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_economia_familiar, container, false);

            //Spinner para la ocupación:
            Spinner spinner_ocupacion = (Spinner) rootView.findViewById(R.id.datos_ocupacion);
            List<String> ocupacion = new ArrayList<>();
            ocupacion.add("Jubilado");
            ocupacion.add("No Trabaja");
            ocupacion.add("Estudia");
            ocupacion.add("Ama de Casa");
            ocupacion.add("Empleado");
            ocupacion.add("Cuenta Propia");
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, ocupacion);
            dataAdapter1.setDropDownViewResource(R.layout.spinners);
            spinner_ocupacion.setAdapter(dataAdapter1);

            //Spinner para los estudios:
            Spinner spinner_estudio = (Spinner) rootView.findViewById(R.id.datos_estudios);
            List<String> estudios = new ArrayList<>();
            estudios.add("Analfabeto");
            estudios.add("Primarios Completos");
            estudios.add("Primarios Incompletos");
            estudios.add("Secundarios Completos");
            estudios.add("Secundarios Incompletos");
            estudios.add("Terciarios Completos");
            estudios.add("Terciarios Incompletos");
            estudios.add("Universitarios Completos");
            estudios.add("Universitarios Incompletos");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, estudios);
            dataAdapter2.setDropDownViewResource(R.layout.spinners);
            spinner_estudio.setAdapter(dataAdapter2);

            return rootView;
        }
    }

    /* Fragmento Salud*/
    public static class SaludFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public SaludFragment() {
        }

        public static SaludFragment newInstance(int sectionNumber) {
            SaludFragment fragment = new SaludFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_salud, container, false);
            final LinearLayout lyt_seguro = (LinearLayout) rootView.findViewById(R.id.lyt_seguro);
            final LinearLayout lyt_chequeo = (LinearLayout) rootView.findViewById(R.id.lyt_chequeo);
            final LinearLayout lyt_tiempo_diabetes = (LinearLayout) rootView.findViewById(R.id.lyt_tiempo_diabetes);
            final LinearLayout lyt_renal = (LinearLayout) rootView.findViewById(R.id.lyt_renal);
            final LinearLayout lyt_enfermedad = (LinearLayout) rootView.findViewById(R.id.lyt_enfermedad);

            RadioGroup rg_seguro = (RadioGroup) rootView.findViewById(R.id.rg_seguro);
            rg_seguro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_seguro_si:
                            lyt_seguro.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_seguro_no:
                            lyt_seguro.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_chequeo = (RadioGroup) rootView.findViewById(R.id.rg_chequeo);
            rg_chequeo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_chequeo_si:
                            lyt_chequeo.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_chequeo_no:
                            lyt_chequeo.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_tmp_diabetes = (RadioGroup) rootView.findViewById(R.id.rg_tmp_diabetes);
            rg_tmp_diabetes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_diabetes_si:
                            lyt_tiempo_diabetes.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_diabetes_no:
                            lyt_tiempo_diabetes.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_renal = (RadioGroup) rootView.findViewById(R.id.rg_renal);
            rg_renal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_renal_si:
                            lyt_renal.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_renal_no:
                            lyt_renal.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_enfermedad = (RadioGroup) rootView.findViewById(R.id.rg_enfermedad);
            rg_enfermedad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_enfermedad_si:
                            lyt_enfermedad.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_enfermedad_no:
                            lyt_enfermedad.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });
            return rootView;
        }
    }

    /* Fragmento Salud*/
    public static class MedicamentosFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public MedicamentosFragment() {
        }

        public static MedicamentosFragment newInstance(int sectionNumber) {
            MedicamentosFragment fragment = new MedicamentosFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_medicamentos, container, false);
            final LinearLayout lyt_hipogluc = (LinearLayout) rootView.findViewById(R.id.lyt_hipoglucemias);
            final LinearLayout lyt_medicinas_presion = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_presion);
            final LinearLayout lyt_medicinas_analgesicos = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_analgesicos);
            final LinearLayout lyt_medicinas_otros = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_otros);

            RadioGroup rg_hipoglucemias = (RadioGroup) rootView.findViewById(R.id.rg_hipoglucemias);
            rg_hipoglucemias.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_hipoglucemias_si:
                            lyt_hipogluc.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_hipoglucemias_no:
                            lyt_hipogluc.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_medicinas_presion = (RadioGroup) rootView.findViewById(R.id.rg_medicinas_presion);
            rg_medicinas_presion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_medicinas_presion_si:
                            lyt_medicinas_presion.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_medicinas_presion_no:
                            lyt_medicinas_presion.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_analgesicos = (RadioGroup) rootView.findViewById(R.id.rg_analgesicos);
            rg_analgesicos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_analgesicos_si:
                            lyt_medicinas_analgesicos.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_analgesicos_no:
                            lyt_medicinas_analgesicos.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_medicinas_otros = (RadioGroup) rootView.findViewById(R.id.rg_medicinas_otros);
            rg_medicinas_otros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_medicinas_otros_si:
                            lyt_medicinas_otros.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_medicinas_otros_no:
                            lyt_medicinas_otros.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            Spinner spinner_medicinas_presion_otros = (Spinner) rootView.findViewById(R.id.datos_medicinas_presion_otros);
            List<String> medicinas_presion_otros = new ArrayList<>();
            medicinas_presion_otros.add("Enalapril");
            medicinas_presion_otros.add("Captopril");
            medicinas_presion_otros.add("Ibersatan");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, medicinas_presion_otros);
            dataAdapter.setDropDownViewResource(R.layout.spinners);
            spinner_medicinas_presion_otros.setAdapter(dataAdapter);

            return rootView;
        }
    }

    /* Fragmento Antecedentes*/
    public static class AntecedentesFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public AntecedentesFragment() {
        }

        public static AntecedentesFragment newInstance(int sectionNumber) {
            AntecedentesFragment fragment = new AntecedentesFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_antecedentes, container, false);
            final LinearLayout lyt_ant_fam_renal = (LinearLayout) rootView.findViewById(R.id.lyt_ant_fam_renal);

            RadioGroup rg_ant_renal = (RadioGroup) rootView.findViewById(R.id.rg_ant_renal);
            rg_ant_renal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_ant_renal_si:
                            lyt_ant_fam_renal.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_ant_renal_no:
                            lyt_ant_fam_renal.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });
            return rootView;
        }
    }

    /* Fragmento Habitos*/
    public static class HabitosFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        static Button btn_guardar;
        static TextView tv;
        static EditText et1, et2, et3;
        public HabitosFragment() {
        }

        public static HabitosFragment newInstance(int sectionNumber) {
            HabitosFragment fragment = new HabitosFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_habitos, container, false);
            tv = (TextView) rootView.findViewById(R.id.salidaJson);
            btn_guardar = (Button) rootView.findViewById(R.id.btn_GuardarHb);
            btn_guardar.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //convertToJson();
                    Toast.makeText(v.getContext(),miFormulario.getNombres(),Toast.LENGTH_SHORT).show();
                }
            });


            final LinearLayout lyt_tabaco = (LinearLayout) rootView.findViewById(R.id.lyt_tabaco);
            final LinearLayout lyt_alcohol = (LinearLayout) rootView.findViewById(R.id.lyt_alcohol);
            final LinearLayout lyt_otros = (LinearLayout) rootView.findViewById(R.id.lyt_otros);

            RadioGroup rg_tabaco = (RadioGroup) rootView.findViewById(R.id.rg_tabaco);
            rg_tabaco.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_tabaco_si:
                            lyt_tabaco.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_tabaco_no:
                            lyt_tabaco.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_alcohol = (RadioGroup) rootView.findViewById(R.id.rg_alcohol);
            rg_alcohol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_alcohol_si:
                            lyt_alcohol.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_alcohol_no:
                            lyt_alcohol.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            RadioGroup rg_otros = (RadioGroup) rootView.findViewById(R.id.rg_otros);
            rg_otros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch(checkedId)
                    {
                        case R.id.datos_otros_si:
                            lyt_otros.setVisibility(View.VISIBLE);
                            break;
                        case R.id.datos_otros_no:
                            lyt_otros.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            Spinner spinner_act_fisica = (Spinner) rootView.findViewById(R.id.datos_ejercicios);
            List<String> act_fisica = new ArrayList<>();
            act_fisica.add("No hago actividad física");
            act_fisica.add("Solo hago ejercicios en el tiempo libre");
            act_fisica.add("Hago ejercicios mas de 3 veces por semana");
            act_fisica.add("Hago ejercicios todos los dias");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, act_fisica);
            dataAdapter.setDropDownViewResource(R.layout.spinners);
            spinner_act_fisica.setAdapter(dataAdapter);

            return rootView;
        }

        public void convertToJson(){
            JSONObject miJson = new JSONObject();
            try{
                miJson.put("tipo","habitos");
                miJson.put("habito1","Prueba 1");
                miJson.put("habito2","Prueba 2");
                miJson.put("habito3","Prueba 3");
            } catch (JSONException e){
                e.printStackTrace();
            }

            String json = miJson.toString();
            tv.setText(json);
        }
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {

                case 0: return DatosFragment.newInstance(position + 1);
                case 1: return ViviendaFragment.newInstance(position + 1);
                case 2: return EconomiaFragment.newInstance(position + 1);
                case 3: return SaludFragment.newInstance(position + 1);
                case 4: return MedicamentosFragment.newInstance(position + 1);
                case 5: return AntecedentesFragment.newInstance(position + 1);
                case 6: return HabitosFragment.newInstance(position + 1);
                default: return DatosFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages. A!
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Datos Personales";
                case 1:
                    return "Vivienda";
                case 2:
                    return "Economía Familiar";
                case 3:
                    return "Salud";
                case 4:
                    return "Medicamentos";
                case 5:
                    return "Antecedentes";
                case 6:
                    return "Hábitos";
            }
            return null;
        }
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
}

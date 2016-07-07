package fiec.ndr;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
            List<String> estados = new ArrayList<String>();
            estados.add("Soltero");
            estados.add("Casado");
            estados.add("Divorciado");
            estados.add("Unión Libre");
            estados.add("Viudo");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(rootView.getContext(), R.layout.spinners, estados);

            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(R.layout.spinners);

            // attaching data adapter to spinner
            spinner_estado_civil.setAdapter(dataAdapter1);

            // Spinner element
            Spinner spinner_etnia = (Spinner) rootView.findViewById(R.id.datos_etnia);
            // Spinner Drop down elements
            List<String> etnias = new ArrayList<String>();
            etnias.add("Afroecuatoriano");
            etnias.add("Blanco");
            etnias.add("Indio");
            etnias.add("Mestizo");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(rootView.getContext(), R.layout.spinners, etnias);

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
            et1 = (EditText) rootView.findViewById(R.id.et_dato1);
            et2 = (EditText) rootView.findViewById(R.id.et_dato2);
            et3 = (EditText) rootView.findViewById(R.id.et_dato3);
            btn_guardar = (Button) rootView.findViewById(R.id.btn_GuardarHb);
            btn_guardar.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //convertToJson();
                    Toast.makeText(v.getContext(),miFormulario.getNombres(),Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }

        public void convertToJson(){
            JSONObject miJson = new JSONObject();
            try{
                miJson.put("tipo","habitos");
                miJson.put("habito1",et1.getText());
                miJson.put("habito2",et2.getText());
                miJson.put("habito3",et3.getText());
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
}

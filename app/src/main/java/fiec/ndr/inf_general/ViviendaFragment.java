package fiec.ndr.inf_general;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiec.ndr.AdminSQLiteOpenHelper;
import fiec.ndr.R;

public class ViviendaFragment extends Fragment {

    static Spinner sp_canton, sp_provincia, sp_agua, sp_vivienda;
    static EditText et_direccion, et_personas;
    static RadioGroup rg_cloacas;

    private String data_provincia, data_canton, data_agua, data_vivienda, data_direccion, data_personas, data_cloacas;
    AdminSQLiteOpenHelper admin;
    private Map<String, String> datos_vivienda = new HashMap<String, String>();


    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabVivienda interface_Vivienda;

    public interface changeTabVivienda {
        void onChangeTabVivienda(Map<String, String> datos_vivienda);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Vivienda = (changeTabVivienda) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTab");
        }
    }

    @Override
    public void onDetach() {
        interface_Vivienda = null; // previene el "leaking"
        super.onDetach();
    }

    ////////////////////////////////////////////////////////////////////////
    ///////////////////FIN -- INTERFACES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////


    /*
        //Este metodo de instanciar el fragment, sirve para mantener variables
        //cuando se recree, evitando que se llame al constructor vacio.
        public static ViviendaFragment newInstance(int sectionNumber) {
            ViviendaFragment fragment = new ViviendaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vivienda, container, false);
        final Context fragmentContext = rootView.getContext();
        admin = new AdminSQLiteOpenHelper(fragmentContext);

        sp_canton = (Spinner) rootView.findViewById(R.id.datos_canton);
        sp_provincia = (Spinner) rootView.findViewById(R.id.datos_provincia);
        sp_vivienda = (Spinner) rootView.findViewById(R.id.datos_vivienda);
        sp_agua = (Spinner) rootView.findViewById(R.id.datos_agua);
        et_direccion = (EditText) rootView.findViewById(R.id.datos_direccion);
        et_personas = (EditText) rootView.findViewById(R.id.datos_personas);
        rg_cloacas = (RadioGroup) rootView.findViewById(R.id.rg_cloacas);

        //Spinner para las provincias
        //Obtenemos las provincias
        List<String> provincias = admin.getProvincias();
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(fragmentContext, R.layout.spinners, provincias);
        dataAdapter1.setDropDownViewResource(R.layout.spinners);
        sp_provincia.setAdapter(dataAdapter1);

        //Añadimos un listener si cambia de provincia.
        sp_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> cantones = admin.getCantones(position);
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(fragmentContext, R.layout.spinners, cantones);
                dataAdapter2.setDropDownViewResource(R.layout.spinners);
                sp_canton.setAdapter(dataAdapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner para la vivienda:
        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter3 = ArrayAdapter.createFromResource(fragmentContext,
                R.array.opcionesVivienda, R.layout.spinners);
        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter3.setDropDownViewResource(R.layout.spinners);
        // Coloco la data en el spinner deseado
        sp_vivienda.setAdapter(dataAdapter3);

        //Spinner para el agua:
        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter4 = ArrayAdapter.createFromResource(fragmentContext,
                R.array.opcionesAgua, R.layout.spinners);
        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter4.setDropDownViewResource(R.layout.spinners);
        // Coloco la data en el spinner deseado
        sp_agua.setAdapter(dataAdapter4);

        //Obtengo el id del radio button que ha sido seleccionado en el radio group.
        rg_cloacas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 Para NO, 1 para femenino.
                data_cloacas = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        return rootView;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - METODOS AUXILIARES /////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Nos aseguramos que el tab sea visible.
        if (this.isVisible())
            //Comprobamos si el fragment ya no es visible para el usuario.
            if (!isVisibleToUser) {
                //Rellenamos el hash con los datos obtenidos de los componentes.
                setearHash();
                //Llamamos al interface.
                interface_Vivienda.onChangeTabVivienda(datos_vivienda);
            }
    }

    public void setearHash(){

        datos_vivienda.clear();

        datos_vivienda.put("hashmap","vivienda");

        //Colectamos los datos de la provincia.
        data_provincia = sp_provincia.getSelectedItem().toString();
        if (data_provincia != null && !data_provincia.isEmpty())
            datos_vivienda.put("provincia", data_provincia);
        else
            datos_vivienda.put("provincia", "");

        //Colectamos los datos del canton.
        data_canton = sp_canton.getSelectedItem().toString();
        if (data_canton != null && !data_canton.isEmpty())
            datos_vivienda.put("canton", data_canton);
        else
            datos_vivienda.put("canton", "");

        //Colectamos los datos de la ubicacion de vivienda.
        data_vivienda = sp_vivienda.getSelectedItem().toString();
        if (data_vivienda != null && !data_vivienda.isEmpty())
            datos_vivienda.put("vivienda", data_vivienda);
        else
            datos_vivienda.put("vivienda", "");


        //Colectamos los datos de nombres.
        data_direccion = et_direccion.getText().toString();
        if (!data_direccion.isEmpty()&& data_direccion.matches(".*\\w.*"))
            datos_vivienda.put("direccion", data_direccion);
        else
            datos_vivienda.put("direccion", "");

        //Colectamos los datos de las personas con las que vive.
        data_personas = et_personas.getText().toString();
        if (!data_personas.isEmpty())
            if(Integer.valueOf(data_personas)>=0)
                datos_vivienda.put("personas", data_personas);
            else
                datos_vivienda.put("personas", "-1");
        else
            datos_vivienda.put("personas", "-1");

        //Colectamos los datos de como llega el agua.
        data_agua = sp_agua.getSelectedItem().toString();
        if (data_agua != null && !data_agua.isEmpty())
            datos_vivienda.put("agua", data_agua);
        else
            datos_vivienda.put("agua", "");


        //Colectamos los datos de si posee cloacas.
        if (data_cloacas != null && !data_cloacas.isEmpty()) {
            if (data_cloacas.equals("0")) {
                datos_vivienda.put("cloacas", data_cloacas);
            } else if (data_cloacas.equals("1")) {
                datos_vivienda.put("cloacas", data_cloacas);
            } else
                datos_vivienda.put("cloacas", "-1");
        }
        else {
            datos_vivienda.put("cloacas", "-1");
        }

    }
}

package fiec.ndr.inf_general;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiec.ndr.R;

public class EconomiaFragment extends Fragment {

    static RadioGroup rg_cbzfam, rg_ingresos, rg_llegafin;
    static Spinner sp_ocupacion,sp_estudios;
    static EditText et_trabajo;

    private String data_cbzfam, data_ingresos, data_llegafin, data_ocupacion, data_estudios, data_trabajo;
    private Map<String, String> datos_economia = new HashMap<String, String>();

    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabEconomia interface_Economia;

    public interface changeTabEconomia {
        void onChangeTabEconomia(Map<String, String> datos_economia);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Economia = (changeTabEconomia) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTabEconomia");
        }
    }

    @Override
    public void onDetach() {
        interface_Economia = null; // previene el "leaking"
        super.onDetach();
    }

    ////////////////////////////////////////////////////////////////////////
    ///////////////////FIN -- INTERFACES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /*
        //Este metodo de instanciar el fragment, sirve para mantener variables
        //cuando se recree, evitando que se llame al constructor vacio.
        public static EconomiaFragment newInstance(int sectionNumber) {
            EconomiaFragment fragment = new EconomiaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    */


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INICIALIZAR COMPONENTES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_economia_familiar, container, false);

        //Capturamos los componentes del formulario.

        rg_cbzfam = (RadioGroup) rootView.findViewById(R.id.rg_cbzfam);
        rg_ingresos = (RadioGroup) rootView.findViewById(R.id.rg_ingresos);
        rg_llegafin = (RadioGroup) rootView.findViewById(R.id.rg_llegafin);
        sp_estudios = (Spinner) rootView.findViewById(R.id.datos_estudios);
        sp_ocupacion = (Spinner) rootView.findViewById(R.id.datos_ocupacion);
        et_trabajo = (EditText) rootView.findViewById(R.id.datos_trabajo);

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter1 = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesOcupacion, R.layout.spinners);
        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter1.setDropDownViewResource(R.layout.spinners);

        // Coloco la data en el spinner deseado
        sp_ocupacion.setAdapter(dataAdapter1);

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter2 = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesEstudios, R.layout.spinners);
        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter2.setDropDownViewResource(R.layout.spinners);

        // Coloco la data en el spinner deseado
        sp_estudios.setAdapter(dataAdapter2);

        //Obtengo el id del radio button que ha sido seleccionado en el radio group.
        rg_cbzfam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_cbzfam = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        //Obtengo el id del radio button que ha sido seleccionado en el radio group.
        rg_ingresos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_ingresos = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        //Obtengo el id del radio button que ha sido seleccionado en el radio group.
        rg_llegafin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_llegafin = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        return rootView;
    }


    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////// FIN - INICIALIZAR COMPONENTES ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////


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
                interface_Economia.onChangeTabEconomia(datos_economia);
            }
    }

    public void setearHash(){

        datos_economia.clear();

        datos_economia.put("hashmap","economia");

        //Colectamos los datos de si es cabeza de familia.
        if (data_cbzfam != null && !data_cbzfam.isEmpty())
            if(data_cbzfam.equals("0") || data_cbzfam.equals("1"))
                datos_economia.put("cbzfam", data_cbzfam);
            else
                datos_economia.put("cbzfam", "-1");
        else
            datos_economia.put("cbzfam", "-1");

        //Colectamos los datos de si tiene ingresos propios.
        if (data_ingresos != null && !data_ingresos.isEmpty())
            if(data_ingresos.equals("0") || data_ingresos.equals("1"))
                datos_economia.put("ingresos", data_ingresos);
            else
                datos_economia.put("ingresos", "-1");
        else
            datos_economia.put("ingresos", "-1");

        //Colectamos los datos de si llega a fin de mes.
        if (data_llegafin != null && !data_llegafin.isEmpty())
            if(data_llegafin.equals("0") || data_llegafin.equals("1"))
                datos_economia.put("llegafin", data_llegafin);
            else
                datos_economia.put("llegafin", "-1");
        else
            datos_economia.put("llegafin", "-1");

        //Colectamos los datos de la ocupacion.
        data_ocupacion = sp_ocupacion.getSelectedItem().toString();
        if (data_ocupacion != null && !data_ocupacion.isEmpty())
            datos_economia.put("ocupacion", data_ocupacion);
        else
            datos_economia.put("ocupacion", "");

        //Colectamos los datos del trabajo.
        data_trabajo = et_trabajo.getText().toString();
        if (data_trabajo != null && !data_trabajo.isEmpty()&& data_trabajo.matches(".*\\w.*"))
            datos_economia.put("trabajo", data_trabajo);
        else
            datos_economia.put("trabajo", "");

        //Colectamos los datos de los estudios.
        data_estudios = sp_estudios.getSelectedItem().toString();
        if (data_estudios != null && !data_estudios.isEmpty())
            datos_economia.put("estudios", data_estudios);
        else
            datos_economia.put("estudios", "");

    }
}

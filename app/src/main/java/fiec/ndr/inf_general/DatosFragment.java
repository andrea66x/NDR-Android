package fiec.ndr.inf_general;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import fiec.ndr.R;

public class DatosFragment extends Fragment {


    static EditText et_nombres, et_apellidos, et_telefono,et_edad;
    static RadioGroup rg_sexo;
    static Spinner sp_EstCivil, sp_origen;
    static NumberPicker np_dia, np_mes, np_anio;
    final String[] values= {"Enero","Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    boolean bisiesto= false;
    int dia=1,mes=1,anio=1900;

    private GregorianCalendar calendar = new GregorianCalendar();
    String data_nombres, data_apellidos, data_fecha_nac, data_etnia, data_estado_civil, data_sexo, data_telefono;
    private Map<String, String> datos_inf_gen = new HashMap<String, String>();


    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabDatos interface_Datos;

    public interface changeTabDatos {
        void onChangeTabDatos(Map<String, String> datos_inf_gen);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Datos = (changeTabDatos) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTab");
        }
    }

    @Override
    public void onDetach() {
        interface_Datos = null; // previene el "leaking"
        super.onDetach();
    }

    ////////////////////////////////////////////////////////////////////////
    ///////////////////FIN -- INTERFACES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datos_personales, container, false);

        //Capturamos los componentes del formulario.
        et_nombres = (EditText) rootView.findViewById(R.id.datos_nombres);
        et_apellidos = (EditText) rootView.findViewById(R.id.datos_apellidos);
        rg_sexo = (RadioGroup) rootView.findViewById(R.id.rg_sexo);
        np_dia = (NumberPicker) rootView.findViewById(R.id.np_dia);
        np_mes = (NumberPicker) rootView.findViewById(R.id.np_mes);
        np_anio = (NumberPicker) rootView.findViewById(R.id.np_anio);
        et_telefono = (EditText) rootView.findViewById(R.id.datos_telefono);
        sp_EstCivil = (Spinner) rootView.findViewById(R.id.datos_estado_civil);
        sp_origen = (Spinner) rootView.findViewById(R.id.datos_etnia);

        fechaPicker(np_mes,np_dia,np_anio);

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter1 = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesEstadoCivil, R.layout.spinners);

        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter1.setDropDownViewResource(R.layout.spinners);

        // Coloco la data en el spinner deseado
        sp_EstCivil.setAdapter(dataAdapter1);

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter2=ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesEtnia, R.layout.spinners);

        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter2.setDropDownViewResource(R.layout.spinners);

        // Coloco la data en el spinner deseado
        sp_origen.setAdapter(dataAdapter2);

        //Obtengo el id del radio button que ha sido seleccionado en el radio group.
        rg_sexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 Para masculino, 1 para femenino.
                data_sexo = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        //Set a value change listener for NumberPicker
        np_mes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal){

                   switch (values[newVal])
                   {
                       case "Enero":
                           np_dia.setMaxValue(31);
                           break;
                       case "Febrero":
                           if(bisiesto)
                                np_dia.setMaxValue(29);
                           else
                               np_dia.setMaxValue(28);
                           break;
                       case "Marzo":
                           np_dia.setMaxValue(30);
                           break;
                       default:
                           np_dia.setMaxValue(31);
                           break;
                   }
                   mes= newVal;
               }

        });

        //Set a value change listener for NumberPicker
        np_anio.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){

                if (calendar.isLeapYear(newVal))
                    bisiesto=true;
                else
                    bisiesto=false;
                anio = newVal;
            }
        });

        np_dia.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                dia= newVal;
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
                interface_Datos.onChangeTabDatos(datos_inf_gen);
            }
    }


    public void fechaPicker(NumberPicker np_m, NumberPicker np_d, NumberPicker np_a){

        np_m.setMinValue(0);
        np_m.setMaxValue(values.length-1);
        np_m.setDisplayedValues(values);
        np_m.setWrapSelectorWheel(true);

        np_d.setMinValue(1);
        np_d.setMaxValue(31);
        np_d.setWrapSelectorWheel(true);

        np_a.setMinValue(1900);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        np_a.setMaxValue(year);
        np_a.setValue(1990);
        np_a.setWrapSelectorWheel(false);
    }



    public void setearHash(){

        datos_inf_gen.clear();

        //Colectamos los datos de nombres.
        data_nombres = et_nombres.getText().toString();
        if (data_nombres.matches(".*\\w.*") && !data_nombres.isEmpty())
            datos_inf_gen.put("nombres", data_nombres);
        else
            datos_inf_gen.put("nombres", "-1");

        //Colectamos los datos de apellidos.
        data_apellidos = et_apellidos.getText().toString();
        if (!data_apellidos.isEmpty() && data_apellidos.matches(".*\\w.*"))
            datos_inf_gen.put("apellidos", data_apellidos);
        else
            datos_inf_gen.put("apellidos", "-1");

        //Colectamos los datos del sexo.
        if (data_sexo != null && !data_sexo.isEmpty()) {
            if (data_sexo.equals("0") || data_sexo.equals("1"))
                datos_inf_gen.put("sexo", data_sexo);
            else
                datos_inf_gen.put("sexo", "-1");
        }
        else
            datos_inf_gen.put("sexo", "-1");

        //Colectamos los datos de la fecha de nacimiento.
        calendar.set(anio, mes, dia);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        if(calendar != null){
            data_fecha_nac = format1.format(calendar.getTime());
            datos_inf_gen.put("fecha_nac", data_fecha_nac);
        }
        else
            datos_inf_gen.put("fecha_nac", "-1");


        //Colectamos los datos del telefono.
        data_telefono = et_telefono.getText().toString();
        if (!data_telefono.isEmpty() && data_telefono.matches(".*\\w.*"))
            datos_inf_gen.put("telefono", data_telefono);
        else
            datos_inf_gen.put("telefono", "-1");

        //Colectamos los datos del estado civil.
        data_estado_civil = sp_EstCivil.getSelectedItem().toString();
        if (!data_estado_civil.equals("Seleccionar")) {
            if (data_estado_civil != null && !data_estado_civil.isEmpty())
                datos_inf_gen.put("estado_civil", data_estado_civil);
            else
                datos_inf_gen.put("estado_civil", "-1");
        }
        else
            datos_inf_gen.put("estado_civil", "-1");

        //Colectamos los datos de la etnia.
        data_etnia = sp_origen.getSelectedItem().toString();
        if (!data_etnia.equals("Seleccionar")) {
            if (data_etnia != null && !data_etnia.isEmpty())
                datos_inf_gen.put("etnia", data_etnia);
            else
                datos_inf_gen.put("etnia", "-1");
        }
        else
            datos_inf_gen.put("etnia", "-1");

    }


}

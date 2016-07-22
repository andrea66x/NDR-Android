package fiec.ndr.inf_general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import fiec.ndr.R;

public class DatosFragment extends Fragment {

    static ImageButton btn_calendario;
    static EditText et_nombres, et_apellidos, et_telefono,et_edad, et_dia, et_mes, et_anio;
    static RadioGroup radioSexGroup;
    static Spinner sp_EstCivil,sp_origen;

    private GregorianCalendar cal;
    private String data_nombres, data_apellidos, data_fecha_nac, data_etnia, data_estado_civil, data_edad, data_sexo, data_telefono;
    private Map<String, String> datos_inf_gen = new HashMap<String, String>();


    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTab interface_Datos;

    public interface changeTab {
        void onChangeTab(Map<String, String> datos_inf_gen);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Datos = (changeTab) activity;
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

    public DatosFragment() {
    }

    /*public static DatosFragment newInstance(int sectionNumber) {
        DatosFragment fragment = new DatosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }*/


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INICIALIZAR COMPONENTES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datos_personales, container, false);

        //Capturamos los componentes del formulario, con los datos.
        et_nombres = (EditText) rootView.findViewById(R.id.datos_nombres);
        et_apellidos = (EditText) rootView.findViewById(R.id.datos_apellidos);
        radioSexGroup = (RadioGroup) rootView.findViewById(R.id.radioSex);
        et_dia = (EditText) rootView.findViewById(R.id.datos_fecha_dia);
        et_mes = (EditText) rootView.findViewById(R.id.datos_fecha_mes);
        et_anio = (EditText) rootView.findViewById(R.id.datos_fecha_anio);
        et_edad = (EditText) rootView.findViewById(R.id.datos_edad);
        et_telefono = (EditText) rootView.findViewById(R.id.datos_telefono);
        sp_EstCivil = (Spinner) rootView.findViewById(R.id.datos_estado_civil);
        sp_origen = (Spinner) rootView.findViewById(R.id.datos_etnia);

        btn_calendario = (ImageButton) rootView.findViewById(R.id.btn_dato_fecha);

        btn_calendario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Show the DatePickerDialog
                showDatePicker();
            }
        });


        // Obtengo el spinner deseado.
        Spinner spinner_estado_civil = (Spinner) rootView.findViewById(R.id.datos_estado_civil);

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter1 = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesEstadoCivil, R.layout.spinners);

        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter1.setDropDownViewResource(R.layout.spinners);

        // Coloco la data en el spinner deseado
        spinner_estado_civil.setAdapter(dataAdapter1);

        // Obtengo el spinner deseado.
        Spinner spinner_etnia = (Spinner) rootView.findViewById(R.id.datos_etnia);

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter2=ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesEtnia, R.layout.spinners);

        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter2.setDropDownViewResource(R.layout.spinners);

        // Coloco la data en el spinner deseado
        spinner_etnia.setAdapter(dataAdapter2);

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 Para masculino, 1 para femenino.
                data_sexo = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        return rootView;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        //Seteamos la fecha actual en el dialog.

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Fecha");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setearFecha(dayOfMonth,monthOfYear,year);
            setearEdad(dayOfMonth,monthOfYear,year);
        }
    };


    public static class DatePickerFragment extends DialogFragment {
        DatePickerDialog.OnDateSetListener ondateSet;
        private int year, month, day;

        public DatePickerFragment() {
        }

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
            ondateSet = ondate;
        }

        @SuppressLint("NewApi")
        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        }
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
                interface_Datos.onChangeTab(datos_inf_gen);
            }
    }


    public void setearFecha(int dia, int mes, int anio){

        et_dia.setText(String.valueOf(dia));
        et_mes.setText(String.valueOf(mes));
        et_anio.setText(String.valueOf(anio));

    }
    public void setearEdad(int dia, int mes, int anio){

        cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(anio, mes, dia);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");

        data_edad = String.valueOf(a);
        et_edad.setText(data_edad);
    }


    public void setearHash(){

        datos_inf_gen.clear();

        datos_inf_gen.put("hashmap","datos_personales");

        //Colectamos los datos de nombres.
        data_nombres = et_nombres.getText().toString();
        if (data_nombres != null && !data_nombres.isEmpty()&& data_nombres.matches(".*\\w.*"))
            datos_inf_gen.put("nombres", data_nombres);
        else
            datos_inf_gen.put("nombres", "");

        //Colectamos los datos de apellidos.
        data_apellidos = et_apellidos.getText().toString();
        if (data_apellidos != null && !data_apellidos.isEmpty()&& data_apellidos.matches(".*\\w.*"))
            datos_inf_gen.put("apellidos", data_apellidos);
        else
            datos_inf_gen.put("apellidos", "");

        //Colectamos los datos del sexo.
        if (data_sexo != null && !data_sexo.isEmpty())
            if(data_sexo.equals("0") || data_sexo.equals("1"))
                datos_inf_gen.put("sexo", data_sexo);
            else
                datos_inf_gen.put("sexo", "-1");
        else
            datos_inf_gen.put("sexo", "-1");

        //Colectamos los datos de la fecha de nacimiento.
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        if(cal != null)
            data_fecha_nac = format1.format(cal.getTime());
        else
            data_fecha_nac= "01-01-1900";
        if (data_fecha_nac != null && !data_fecha_nac.isEmpty())
            datos_inf_gen.put("fecha_nac", data_fecha_nac);
        else
            datos_inf_gen.put("fecha_nac", "");

        //Colectamos los datos de la edad.
        if (data_edad != null && !data_edad.isEmpty())
            if(Integer.valueOf(data_edad)>=0)
                datos_inf_gen.put("edad", data_edad);
            else
                datos_inf_gen.put("edad", "-1");
        else
            datos_inf_gen.put("edad", "-1");

        //Colectamos los datos del telefono.
        data_telefono = et_telefono.getText().toString();
        if (data_telefono != null && !data_telefono.isEmpty()&& data_telefono.matches(".*\\w.*"))
            datos_inf_gen.put("telefono", data_telefono);
        else
            datos_inf_gen.put("telefono", "");

        //Colectamos los datos del estado civil.
        data_estado_civil = sp_EstCivil.getSelectedItem().toString();
        if (data_estado_civil != null && !data_estado_civil.isEmpty())
            datos_inf_gen.put("estado_civil", data_estado_civil);
        else
            datos_inf_gen.put("telefono", "");

        //Colectamos los datos de la etnia.
        data_etnia = sp_origen.getSelectedItem().toString();
        if (data_etnia != null && !data_etnia.isEmpty())
            datos_inf_gen.put("etnia", data_etnia);
        else
            datos_inf_gen.put("etnia", "");

        /*
        Forma de transformar de string a date:
        String string = "January 2, 2010";
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = format.parse(string);
        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
         */

    }


}

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiec.ndr.R;

public class DatosFragment extends Fragment {


    private ImageButton btn_calendario;
    private EditText dato_fecha_nac;

    static EditText et_nombres;
    static EditText et_apellidos;
    static RadioGroup radioSexGroup;
    static RadioButton radioSexButton;
    static EditText et_fecNac;
    static EditText et_edad;
    static EditText et_telefono;
    static Spinner sp_EstCivil;
    static Spinner sp_origen;

    Map<String, String> datos_inf_gen = new HashMap<String, String>();


    ///////////////////////////////////////////////////////////////////
    /////////////////// INTERFACES ///////////////////////////////////
    ///////////////////////////////////////////////////////////////////


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


    public DatosFragment() {
    }

    /*public static DatosFragment newInstance(int sectionNumber) {
        DatosFragment fragment = new DatosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datos_personales, container, false);

        //Capturamos los componentes del formulario, con los datos.
        et_nombres = (EditText) rootView.findViewById(R.id.datos_nombres);
        et_apellidos = (EditText) rootView.findViewById(R.id.datos_apellidos);
        radioSexGroup = (RadioGroup) rootView.findViewById(R.id.radioSex);
        //et_fecNac = (EditText) rootView.findViewById(R.id.datos_fecha);
        et_edad = (EditText) rootView.findViewById(R.id.datos_edad);
        et_telefono = (EditText) rootView.findViewById(R.id.datos_telefono);
        sp_EstCivil = (Spinner) rootView.findViewById(R.id.datos_estado_civil);
        sp_origen = (Spinner) rootView.findViewById(R.id.datos_etnia);

        btn_calendario = (ImageButton) rootView.findViewById(R.id.btn_dato_fecha);

        //dato_fecha_nac = (EditText) rootView.findViewById(R.id.datos_fecha);
        btn_calendario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Show the DatePickerDialog
                showDatePicker();
            }
        });


        // Obtengo el spinner deseado.
        Spinner spinner_estado_civil = (Spinner) rootView.findViewById(R.id.datos_estado_civil);

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter1=ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesEstadoCivil, R.layout.spinners);

        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter1.setDropDownViewResource(R.layout.spinners);

        // Coloco la data en el spinner deseado
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

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Toast.makeText(getContext(), String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year), Toast.LENGTH_SHORT).show();
        }
    };
/*
    @Override
    public void onPause(){
        super.onPause();
        miFormulario.setNombres(et_nombres.getText().toString());
        miFormulario.setApellidos(et_apellidos.getText().toString());
        miFormulario.setEdad(Integer.parseInt(et_edad.getText().toString()));
        miFormulario.setCodigo(codigo);
        miFormulario.setFec_nac(et_fecNac.getText().toString());
        miFormulario.setTelefono(et_telefono.getText().toString());
        miFormulario.setTipo("Datos Personales");
        radioSexButton = (RadioButton) getActivity().findViewById(radioSexGroup.getCheckedRadioButtonId());
        miFormulario.setSexo(radioSexButton.getText().toString());
        miFormulario.setEst_civil(sp_EstCivil.getSelectedItem().toString());
        miFormulario.setOrigen(sp_origen.getSelectedItem().toString());

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
*/

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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Nos aseguramos que el tab sea visible.
        if (this.isVisible())
            if (!isVisibleToUser) {
                interface_Datos.onChangeTab(datos_inf_gen);
                Log.d("DATOS PERSONALES","Sali del Fragment Datos Personales");
            }
    }

}

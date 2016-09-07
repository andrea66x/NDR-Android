package fiec.ndr.inf_general;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiec.ndr.R;

public class HabitosFragment extends Fragment {

    static LinearLayout lyt_tabaco, lyt_alcohol,lyt_otros;
    static RadioGroup rg_tabaco, rg_alcohol, rg_otros;
    static EditText et_frc_tabaco, et_frc_alcohol, et_frc_otros;
    static Spinner sp_ejercicios;

    private String data_tabaco, data_alcohol, data_otros, data_ejercicios;
    private String det_frc_tabaco, det_frc_alcohol, det_frc_otros;

    private Map<String, String> datos_habitos = new HashMap<String, String>();

    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabHabitos interface_Habitos;

    public interface changeTabHabitos {
        void onChangeTabHabitos(Map<String, String> datos_habitos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Habitos = (changeTabHabitos) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTabAntecedentes");
        }
    }

    @Override
    public void onDetach() {
        interface_Habitos = null; // previene el "leaking"
        super.onDetach();
    }

    ////////////////////////////////////////////////////////////////////////
    ///////////////////FIN -- INTERFACES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INICIALIZAR COMPONENTES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_habitos, container, false);

        lyt_tabaco = (LinearLayout) rootView.findViewById(R.id.lyt_tabaco);
        lyt_alcohol = (LinearLayout) rootView.findViewById(R.id.lyt_alcohol);
        lyt_otros = (LinearLayout) rootView.findViewById(R.id.lyt_otros);

        rg_tabaco = (RadioGroup) rootView.findViewById(R.id.rg_tabaco);
        rg_alcohol = (RadioGroup) rootView.findViewById(R.id.rg_alcohol);
        rg_otros = (RadioGroup) rootView.findViewById(R.id.rg_otros);

        et_frc_alcohol = (EditText) rootView.findViewById(R.id.datos_frc_alcohol);
        et_frc_tabaco = (EditText) rootView.findViewById(R.id.datos_frc_tabaco);
        et_frc_otros = (EditText) rootView.findViewById(R.id.datos_frc_otros);

        sp_ejercicios = (Spinner) rootView.findViewById(R.id.sp_ejercicios);

        rg_tabaco.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_tabaco_si:
                        lyt_tabaco.setVisibility(View.VISIBLE);
                        data_tabaco = "1";
                        break;
                    case R.id.datos_tabaco_no:
                        lyt_tabaco.setVisibility(View.GONE);
                        data_tabaco = "0";
                        et_frc_tabaco.setText("");
                        det_frc_alcohol = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_alcohol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_alcohol_si:
                        lyt_alcohol.setVisibility(View.VISIBLE);
                        data_alcohol = "1";
                        break;
                    case R.id.datos_alcohol_no:
                        lyt_alcohol.setVisibility(View.GONE);
                        data_alcohol = "0";
                        et_frc_alcohol.setText("");
                        det_frc_alcohol = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_otros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_otros_si:
                        lyt_otros.setVisibility(View.VISIBLE);
                        data_otros = "1";
                        break;
                    case R.id.datos_otros_no:
                        lyt_otros.setVisibility(View.GONE);
                        data_otros = "0";
                        et_frc_otros.setText("");
                        det_frc_otros = "";
                        break;
                    default:
                        break;
                }
            }
        });

        // Paso el array de las opciones y el aspecto que tendra la caja selectora.
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.opcionesActividadFisica, R.layout.spinners);

        // Paso el formato que tendra las opciones al mostrar el dialog spinner.
        dataAdapter.setDropDownViewResource(R.layout.spinners);

        sp_ejercicios.setAdapter(dataAdapter);

        setearHash();
        interface_Habitos.onChangeTabHabitos(datos_habitos);

        return rootView;
    }
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////// FIN - INICIALIZAR COMPONENTES ///////////////////////////////////
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
                interface_Habitos.onChangeTabHabitos(datos_habitos);
            }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - METODOS AUXILIARES /////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    public void setearHash(){

        datos_habitos.clear();

        //Colectamos los datos de si fuma y su frecuencia.
        if (data_tabaco != null && !data_tabaco.isEmpty()){
            if(data_tabaco.equals("0")) {
                datos_habitos.put("tabaco", data_tabaco);
                datos_habitos.put("det_frc_tabaco", "");
            }
            else if(data_tabaco.equals("1")) {
                datos_habitos.put("tabaco", data_tabaco);
                det_frc_tabaco = et_frc_tabaco.getText().toString();
                if(!det_frc_tabaco.isEmpty())
                    datos_habitos.put("det_frc_tabaco", det_frc_tabaco);
                else
                    datos_habitos.put("det_frc_tabaco", "-1");
            }
            else{
                datos_habitos.put("tabaco", "-1");
                datos_habitos.put("det_frc_tabaco", "");
            }
        }
        else {
            datos_habitos.put("tabaco", "-1");
            datos_habitos.put("det_frc_tabaco", "");
        }

        //Colectamos los datos de si toma y su frecuencia.
        if (data_alcohol != null && !data_alcohol.isEmpty()){
            if(data_alcohol.equals("0")) {
                datos_habitos.put("alcohol", data_alcohol);
                datos_habitos.put("det_frc_alcohol", "");
            }
            else if(data_alcohol.equals("1")) {
                datos_habitos.put("alcohol", data_tabaco);
                det_frc_alcohol = et_frc_alcohol.getText().toString();
                if(!det_frc_alcohol.isEmpty())
                    datos_habitos.put("det_frc_alcohol", det_frc_alcohol);
                else
                    datos_habitos.put("det_frc_alcohol", "-1");

            }
            else{
                datos_habitos.put("alcohol", "-1");
                datos_habitos.put("det_frc_alcohol", "");
            }
        }
        else {
            datos_habitos.put("alcohol", "-1");
            datos_habitos.put("det_frc_alcohol", "");
        }

        //Colectamos los datos de si tiene otro vicio y cual es.
        if (data_otros != null && !data_otros.isEmpty()){
            if(data_otros.equals("0")) {
                datos_habitos.put("otros", data_otros);
                datos_habitos.put("det_frc_otros", "");
            }
            else if(data_otros.equals("1")) {
                datos_habitos.put("otros", data_tabaco);
                det_frc_otros = et_frc_otros.getText().toString();
                if(!det_frc_otros.isEmpty())
                    datos_habitos.put("det_frc_otros", det_frc_otros);
                else
                    datos_habitos.put("det_frc_otros", "-1");

            }
            else{
                datos_habitos.put("otros", "-1");
                datos_habitos.put("det_frc_otros", "");
            }
        }
        else {
            datos_habitos.put("otros", "-1");
            datos_habitos.put("det_frc_otros", "");
        }

        //Colectamos los datos de la actividad fisica.
        data_ejercicios = sp_ejercicios.getSelectedItem().toString();
        if (!data_ejercicios.equals("Seleccionar")) {
            if (data_ejercicios != null && !data_ejercicios.isEmpty())
                datos_habitos.put("ejercicios", data_ejercicios);
            else
                datos_habitos.put("ejercicios", "-1");
        }
        else
            datos_habitos.put("ejercicios", "-1");

    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// FIN - METODOS AUXILIARES /////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
}

package fiec.ndr.inf_general;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

import fiec.ndr.R;

public class SaludFragment extends Fragment {

    static RadioGroup rg_seguro, rg_chequeo, rg_diabetes, rg_presion, rg_renal, rg_enfermedad;
    static EditText et_seguro, et_vcs_chequeo, et_mes_chequeo, et_tmp_diabetes, et_renal, et_enfermedad;
    static LinearLayout lyt_seguro, lyt_chequeo, lyt_tiempo_diabetes, lyt_renal,lyt_enfermedad;

    private String data_seguro, data_chequeos, data_diabetes, data_presion, data_renal, data_enfermedad;
    private String det_seguro, det_vec_chequeo, det_mes_chequeo, det_tmp_diabetes, det_renal, det_enfermedad;
    private Map<String, String> datos_salud = new HashMap<String, String>();

    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabSalud interface_Salud;

    public interface changeTabSalud {
        void onChangeTabSalud(Map<String, String> datos_salud);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Salud = (changeTabSalud) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTabSalud");
        }
    }

    @Override
    public void onDetach() {
        interface_Salud = null; // previene el "leaking"
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
        View rootView = inflater.inflate(R.layout.fragment_salud, container, false);

        lyt_seguro = (LinearLayout) rootView.findViewById(R.id.lyt_seguro);
        lyt_chequeo = (LinearLayout) rootView.findViewById(R.id.lyt_chequeo);
        lyt_tiempo_diabetes = (LinearLayout) rootView.findViewById(R.id.lyt_tiempo_diabetes);
        lyt_renal = (LinearLayout) rootView.findViewById(R.id.lyt_renal);
        lyt_enfermedad = (LinearLayout) rootView.findViewById(R.id.lyt_enfermedad);

        rg_seguro = (RadioGroup) rootView.findViewById(R.id.rg_seguro);
        rg_chequeo = (RadioGroup) rootView.findViewById(R.id.rg_chequeo);
        rg_diabetes = (RadioGroup) rootView.findViewById(R.id.rg_diabetes);
        rg_presion = (RadioGroup) rootView.findViewById(R.id.rg_presion);
        rg_renal = (RadioGroup) rootView.findViewById(R.id.rg_renal);
        rg_enfermedad = (RadioGroup) rootView.findViewById(R.id.rg_enfermedad);

        et_seguro = (EditText) rootView.findViewById(R.id.datos_seguro_medico);
        et_vcs_chequeo = (EditText) rootView.findViewById(R.id.datos_veces_chequeo);
        et_mes_chequeo = (EditText) rootView.findViewById(R.id.datos_meses_chequeo);
        et_tmp_diabetes = (EditText) rootView.findViewById(R.id.datos_tiempo_diabetes);
        et_renal = (EditText) rootView.findViewById(R.id.datos_enfermedad_renal);
        et_enfermedad = (EditText) rootView.findViewById(R.id.datos_enfermedad_otra);


        rg_seguro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId ) {
                switch (checkedId) {
                    case R.id.datos_seguro_si:
                        lyt_seguro.setVisibility(View.VISIBLE);
                        data_seguro = "1";
                        break;
                    case R.id.datos_seguro_no:
                        lyt_seguro.setVisibility(View.GONE);
                        data_seguro = "0";
                        et_seguro.setText("");
                        det_seguro = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_chequeo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_chequeo_si:
                        lyt_chequeo.setVisibility(View.VISIBLE);
                        data_chequeos = "1";
                        break;
                    case R.id.datos_chequeo_no:
                        lyt_chequeo.setVisibility(View.GONE);
                        data_chequeos = "0";
                        et_mes_chequeo.setText("");
                        et_vcs_chequeo.setText("");
                        det_mes_chequeo = "";
                        det_vec_chequeo = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_diabetes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_diabetes_si:
                        lyt_tiempo_diabetes.setVisibility(View.VISIBLE);
                        data_diabetes = "1";
                        break;
                    case R.id.datos_diabetes_no:
                        lyt_tiempo_diabetes.setVisibility(View.GONE);
                        data_diabetes = "0";
                        et_tmp_diabetes.setText("");
                        det_tmp_diabetes = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_renal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_renal_si:
                        lyt_renal.setVisibility(View.VISIBLE);
                        data_renal = "1";
                        break;
                    case R.id.datos_renal_no:
                        lyt_renal.setVisibility(View.GONE);
                        data_renal = "0";
                        et_renal.setText("");
                        det_renal = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_enfermedad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_enfermedad_si:
                        lyt_enfermedad.setVisibility(View.VISIBLE);
                        data_enfermedad = "1";
                        break;
                    case R.id.datos_enfermedad_no:
                        lyt_enfermedad.setVisibility(View.GONE);
                        data_enfermedad = "0";
                        et_enfermedad.setText("");
                        det_enfermedad = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_presion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_presion = String.valueOf(radioGroup.indexOfChild(radioButton));
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
                interface_Salud.onChangeTabSalud(datos_salud);
            }
    }

    public void setearHash(){

        datos_salud.clear();

        //Colectamos los datos de si tiene seguro y si lo tiene lo recolectamos.
        if (data_seguro != null && !data_seguro.isEmpty()){
            if(data_seguro.equals("0")) {
                datos_salud.put("seguro", data_seguro);
                datos_salud.put("det_seguro", "");
            }
            else if(data_seguro.equals("1")) {
                datos_salud.put("seguro", data_seguro);
                det_seguro = et_seguro.getText().toString();
                if(!det_seguro.isEmpty())
                    datos_salud.put("det_seguro", det_seguro);
                else
                    datos_salud.put("det_seguro", "-1");
            }
            else{
                datos_salud.put("seguro", "-1");
                datos_salud.put("det_seguro", "");
            }
        }
        else {
            datos_salud.put("seguro", "-1");
            datos_salud.put("det_seguro", "");
        }

        //Colectamos los datos de si realiza chequeos y si los realiza los recolectamos.
        if (data_chequeos != null && !data_chequeos.isEmpty()){
            if(data_chequeos.equals("0")) {
                datos_salud.put("chequeos", data_chequeos);
                datos_salud.put("det_mes_chequeo", "");
                datos_salud.put("det_vec_chequeo", "");
            }
            else if(data_chequeos.equals("1")) {
                datos_salud.put("chequeos", data_chequeos);
                det_vec_chequeo = et_vcs_chequeo.getText().toString();
                det_mes_chequeo = et_mes_chequeo.getText().toString();
                if(!det_mes_chequeo.isEmpty())
                    datos_salud.put("det_mes_chequeo", det_mes_chequeo);
                else
                    datos_salud.put("det_mes_chequeo", "-1");
                if(!det_vec_chequeo.isEmpty())
                    datos_salud.put("det_vec_chequeo", det_vec_chequeo);
                else
                    datos_salud.put("det_vec_chequeo", "1");
            }
            else{
                datos_salud.put("chequeos", "-1");
                datos_salud.put("det_mes_chequeo", "");
                datos_salud.put("det_vec_chequeo", "");
            }
        }
        else {
            datos_salud.put("chequeos", "-1");
            datos_salud.put("det_mes_chequeo", "");
            datos_salud.put("det_vec_chequeo", "");
        }

        //Colectamos los datos de si tiene diabetes y si tiene hace cuanto.
        if (data_diabetes != null && !data_diabetes.isEmpty()){
            if(data_diabetes.equals("0")) {
                datos_salud.put("diabetes", data_diabetes);
                datos_salud.put("det_tmp_diabetes", "");
            }
            else if(data_diabetes.equals("1")) {
                datos_salud.put("diabetes", data_diabetes);
                det_tmp_diabetes = et_tmp_diabetes.getText().toString();
                if(!det_tmp_diabetes.isEmpty())
                    datos_salud.put("det_tmp_diabetes", det_tmp_diabetes);
                else
                    datos_salud.put("det_tmp_diabetes", "-1");
            }
            else{
                datos_salud.put("diabetes", "-1");
                datos_salud.put("det_tmp_diabetes", "");
            }
        }
        else {
            datos_salud.put("diabetes", "-1");
            datos_salud.put("det_tmp_diabetes", "");
        }

        //Colectamos los datos de si tiene presión alta.
        if (data_presion != null && !data_presion.isEmpty()) {
            if (data_presion.equals("0") ||data_presion.equals("1"))
                datos_salud.put("presion", data_presion);
            else
                datos_salud.put("presion", "-1");
        }
        else {
            datos_salud.put("presion", "-1");
        }

        //Colectamos los datos de si tiene enfermedad renal y si tiene cual.
        if (data_renal != null && !data_renal.isEmpty()){
            if(data_renal.equals("0")) {
                datos_salud.put("renal", data_renal);
                datos_salud.put("det_renal", "");
            }
            else if(data_renal.equals("1")) {
                datos_salud.put("renal", data_renal);
                det_renal = et_renal.getText().toString();
                if(!det_renal.isEmpty())
                    datos_salud.put("det_renal", det_renal);
                else
                    datos_salud.put("det_renal", "-1");
            }
            else{
                datos_salud.put("renal", "-1");
                datos_salud.put("det_renal", "");
            }
        }
        else {
            datos_salud.put("renal", "-1");
            datos_salud.put("det_renal", "");
        }

        //Colectamos los datos de si tiene enfermedad renal y si tiene cual.
        if (data_enfermedad != null && !data_enfermedad.isEmpty()){
            if(data_enfermedad.equals("0")) {
                datos_salud.put("enfermedad", data_enfermedad);
                datos_salud.put("det_enfermedad", "");
            }
            else if(data_enfermedad.equals("1")) {
                datos_salud.put("enfermedad", data_enfermedad);
                det_enfermedad = et_enfermedad.getText().toString();
                if(!det_enfermedad.isEmpty())
                    datos_salud.put("det_enfermedad", det_enfermedad);
                else
                    datos_salud.put("det_enfermedad", "-1");
            }
            else{
                datos_salud.put("enfermedad", "-1");
                datos_salud.put("det_enfermedad", "");
            }
        }
        else {
            datos_salud.put("enfermedad", "-1");
            datos_salud.put("det_enfermedad", "");
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// FIN - METODOS AUXILIARES /////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
}

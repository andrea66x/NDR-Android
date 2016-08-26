package fiec.ndr.inf_general;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import fiec.ndr.InformacionGeneral;
import fiec.ndr.R;

public class AntecedentesFragment extends Fragment {

    static RadioGroup rg_ant_glucosa, rg_ant_familia, rg_ant_parientes, rg_ant_embarazo, rg_ant_pso_hijos, rg_ant_presion, rg_ant_renal;
    static EditText et_ant_enf_renal;
    static LinearLayout lyt_ant_enf_renal, lyt_embarazo, lyt_hijos;

    private String data_ant_glucosa, data_ant_familia, data_ant_parientes, data_ant_embarazo, data_ant_pso_hijos, data_ant_presion, data_ant_renal;
    private String det_ant_enf_renal;

    int sexo_encuestado=0;

    private Map<String, String> datos_antecedentes = new HashMap<String, String>();

    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabAntecedentes interface_Antecedentes;

    public interface changeTabAntecedentes {
        void onChangeTabAntecedentes(Map<String, String> datos_antecedentes);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Antecedentes = (changeTabAntecedentes) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTabAntecedentes");
        }
    }

    @Override
    public void onDetach() {
        interface_Antecedentes = null; // previene el "leaking"
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
        View rootView = inflater.inflate(R.layout.fragment_antecedentes, container, false);

        rg_ant_glucosa = (RadioGroup) rootView.findViewById(R.id.rg_ant_glucosa);
        rg_ant_familia = (RadioGroup) rootView.findViewById(R.id.rg_ant_familia);
        rg_ant_parientes = (RadioGroup) rootView.findViewById(R.id.rg_ant_parientes);
        rg_ant_embarazo = (RadioGroup) rootView.findViewById(R.id.rg_ant_embarazo);
        rg_ant_pso_hijos = (RadioGroup) rootView.findViewById(R.id.rg_ant_pso_hijos);
        rg_ant_presion = (RadioGroup) rootView.findViewById(R.id.rg_ant_presion);
        rg_ant_renal = (RadioGroup) rootView.findViewById(R.id.rg_ant_renal);

        et_ant_enf_renal = (EditText) rootView.findViewById(R.id.datos_ant_enf_renal);

        lyt_ant_enf_renal = (LinearLayout) rootView.findViewById(R.id.lyt_ant_fam_renal);
        lyt_embarazo = (LinearLayout) rootView.findViewById(R.id.lyt_embarazo);
        lyt_hijos = (LinearLayout) rootView.findViewById(R.id.lyt_hijos);

        rg_ant_glucosa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_ant_glucosa = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        rg_ant_familia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_ant_familia = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        rg_ant_parientes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_ant_parientes = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        rg_ant_embarazo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_ant_embarazo = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        rg_ant_pso_hijos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_ant_pso_hijos = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        rg_ant_presion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                // 0 para no o falso y 1 para si o verdadero.
                data_ant_presion = String.valueOf(radioGroup.indexOfChild(radioButton));
            }
        });

        rg_ant_renal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_ant_renal_si:
                        lyt_ant_enf_renal.setVisibility(View.VISIBLE);
                        data_ant_renal = "1";
                        break;
                    case R.id.datos_ant_renal_no:
                        lyt_ant_enf_renal.setVisibility(View.GONE);
                        data_ant_renal = "0";
                        et_ant_enf_renal.setText("");
                        det_ant_enf_renal = "";
                        break;
                    default:
                        break;
                }
            }
        });

        InformacionGeneral act_inf_gen = (InformacionGeneral)getActivity();
        sexo_encuestado = act_inf_gen.sexo;
        if(sexo_encuestado==0){
            lyt_embarazo.setVisibility(View.GONE);
            lyt_hijos.setVisibility(View.GONE);
        }
        else if(sexo_encuestado==1){
            lyt_embarazo.setVisibility(View.VISIBLE);
            lyt_hijos.setVisibility(View.VISIBLE);
        }
        else{
            lyt_embarazo.setVisibility(View.VISIBLE);
            lyt_hijos.setVisibility(View.VISIBLE);
        }


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
                interface_Antecedentes.onChangeTabAntecedentes(datos_antecedentes);
            }
    }

    public void setearHash() {

        datos_antecedentes.clear();

        //Colectamos los datos de si ha tenido glucosa o no.
        if (data_ant_glucosa != null && !data_ant_glucosa.isEmpty()) {
            if (data_ant_glucosa.equals("0") || data_ant_glucosa.equals("1"))
                datos_antecedentes.put("ant_glucosa", data_ant_glucosa);
            else
                datos_antecedentes.put("ant_glucosa", "-1");
        } else
            datos_antecedentes.put("ant_glucosa", "-1");

        //Colectamos los datos de si sus familiares tienen diabetes o no.
        if (data_ant_familia != null && !data_ant_familia.isEmpty()) {
            if (data_ant_familia.equals("0") || data_ant_familia.equals("1"))
                datos_antecedentes.put("ant_familia", data_ant_familia);
            else
                datos_antecedentes.put("ant_familia", "-1");
        } else
            datos_antecedentes.put("ant_familia", "-1");

        //Colectamos los datos si sus parientes tienen diabetes o no.
        if (data_ant_parientes != null && !data_ant_parientes.isEmpty()) {
            if (data_ant_parientes.equals("0") || data_ant_parientes.equals("1"))
                datos_antecedentes.put("ant_parientes", data_ant_parientes);
            else
                datos_antecedentes.put("ant_parientes", "-1");
        } else
            datos_antecedentes.put("ant_parientes", "-1");

        //Colectamos los datos si tuvo diabetes en el embarazo.

        if (sexo_encuestado == 1) {
            if (data_ant_embarazo != null && !data_ant_embarazo.isEmpty()) {
                if (data_ant_embarazo.equals("0") || data_ant_embarazo.equals("1"))
                    datos_antecedentes.put("ant_embarazo", data_ant_embarazo);
                else
                    datos_antecedentes.put("ant_embarazo", "-1");
            } else
                datos_antecedentes.put("ant_embarazo", "-1");
        } else
            datos_antecedentes.put("ant_embarazo", "0");


        //Colectamos los datos de si ha tenido glucosa o no.

        if (sexo_encuestado == 1) {
            if (data_ant_pso_hijos != null && !data_ant_pso_hijos.isEmpty()) {
                if (data_ant_pso_hijos.equals("0") || data_ant_pso_hijos.equals("1"))
                    datos_antecedentes.put("ant_pso_hijos", data_ant_pso_hijos);
                else
                    datos_antecedentes.put("ant_pso_hijos", "-1");

            } else
                datos_antecedentes.put("ant_pso_hijos", "-1");
        } else
            datos_antecedentes.put("ant_pso_hijos", "0");


        //Colectamos los datos de si ha tenido glucosa o no.
        if (data_ant_presion != null && !data_ant_presion.isEmpty()) {
            if (data_ant_presion.equals("0") || data_ant_presion.equals("1"))
                datos_antecedentes.put("ant_presion", data_ant_presion);
            else
                datos_antecedentes.put("ant_presion", "-1");
        }
        else
            datos_antecedentes.put("ant_presion", "-1");

        //Colectamos los datos de si tiene enfermedad renal y si tiene cual.
        if (data_ant_renal != null && !data_ant_renal.isEmpty()){
            if(data_ant_renal.equals("0")) {
                datos_antecedentes.put("ant_renal", data_ant_renal);
                datos_antecedentes.put("det_ant_enf_renal", "");
            }
            else if(data_ant_renal.equals("1")) {
                datos_antecedentes.put("ant_renal", data_ant_renal);
                det_ant_enf_renal = et_ant_enf_renal.getText().toString();
                if(!det_ant_enf_renal.isEmpty())
                    datos_antecedentes.put("det_ant_enf_renal", det_ant_enf_renal);
                else
                    datos_antecedentes.put("det_ant_enf_renal", "-1");
            }
            else{
                datos_antecedentes.put("ant_renal", "-1");
                datos_antecedentes.put("det_ant_enf_renal", "");
            }
        }
        else {
            datos_antecedentes.put("ant_renal", "-1");
            datos_antecedentes.put("det_ant_enf_renal", "");
        }


    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// FIN - METODOS AUXILIARES /////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
}

package fiec.ndr.inf_general;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiec.ndr.InformacionGeneral;
import fiec.ndr.R;

/* Fragmento Salud*/
public class MedicamentosFragment extends Fragment {

    static LinearLayout lyt_hipogluc, lyt_medicinas_presion,lyt_medicinas_analgesicos,lyt_medicinas_otros, lyt_razones_med;
    static RadioGroup rg_insulina, rg_hipoglucemias, rg_medicinas_presion, rg_analgesicos, rg_medicinas_otros;
    static EditText et_hipoglucemias, et_medicina_presion, et_analgesicos, et_medicinas_otros;
    static CheckBox chk_razon_1,chk_razon_2,chk_razon_3,chk_razon_4, chk_med_1, chk_med_2, chk_med_3;

    private String data_insulina, data_hipoglucemias, data_medicina_presion, data_analgesicos, data_medicinas_otros;
    private String det_hipoglucemias, det_medicina_presion, det_analgesicos, det_medicinas_otros;
    private String data_razon_1, data_razon_2, data_razon_3, data_razon_4, data_med_1, data_med_2, data_med_3;
    private boolean check1, check2;
    int diabetes;

    private Map<String, String> datos_medicamentos = new HashMap<String, String>();

    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabMedicamentos interface_Medicamentos;

    public interface changeTabMedicamentos {
        void onChangeTabMedicamentos(Map<String, String> datos_medicamentos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Medicamentos = (changeTabMedicamentos) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTabMedicamentos");
        }
    }

    @Override
    public void onDetach() {
        interface_Medicamentos = null; // previene el "leaking"
        super.onDetach();
    }

    ////////////////////////////////////////////////////////////////////////
    ///////////////////FIN -- INTERFACES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////


    /*
        //Este metodo de instanciar el fragment, sirve para mantener variables
        //cuando se recree, evitando que se llame al constructor vacio.
        public static MedicamentosFragment newInstance(int sectionNumber) {
            MedicamentosFragment fragment = new MedicamentosFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_medicamentos, container, false);

        lyt_hipogluc = (LinearLayout) rootView.findViewById(R.id.lyt_hipoglucemias);
        lyt_medicinas_presion = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_presion);
        lyt_medicinas_analgesicos = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_analgesicos);
        lyt_medicinas_otros = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_otros);
        lyt_razones_med = (LinearLayout) rootView.findViewById(R.id.lyt_razones_med);

        rg_insulina = (RadioGroup) rootView.findViewById(R.id.rg_insulina);
        rg_hipoglucemias = (RadioGroup) rootView.findViewById(R.id.rg_hipoglucemias);
        rg_medicinas_presion = (RadioGroup) rootView.findViewById(R.id.rg_medicinas_presion);
        rg_analgesicos = (RadioGroup) rootView.findViewById(R.id.rg_analgesicos);
        rg_medicinas_otros = (RadioGroup) rootView.findViewById(R.id.rg_medicinas_otros);

        et_hipoglucemias = (EditText) rootView.findViewById(R.id.datos_hipoglucemias);
        et_medicina_presion = (EditText) rootView.findViewById(R.id.datos_medicinas_presion);
        et_analgesicos = (EditText) rootView.findViewById(R.id.datos_analgesicos);
        et_medicinas_otros = (EditText) rootView.findViewById(R.id.datos_medicinas_otros);

        chk_med_1 = (CheckBox) rootView.findViewById(R.id.chk_med_1);
        chk_med_2 = (CheckBox) rootView.findViewById(R.id.chk_med_2);
        chk_med_3 = (CheckBox) rootView.findViewById(R.id.chk_med_3);
        chk_razon_1 = (CheckBox) rootView.findViewById(R.id.chk_razon_1);
        chk_razon_2 = (CheckBox) rootView.findViewById(R.id.chk_razon_2);
        chk_razon_3 = (CheckBox) rootView.findViewById(R.id.chk_razon_3);
        chk_razon_4 = (CheckBox) rootView.findViewById(R.id.chk_razon_4);

        rg_insulina.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_insulina_si:
                        data_insulina = "1";
                        check1 = false;
                        break;
                    case R.id.datos_insulina_no:
                        data_insulina = "0";
                        check1 = true;
                        break;
                    default:
                        break;
                }
                if(check1&&check2&&(diabetes==1))
                    lyt_razones_med.setVisibility(View.VISIBLE);
                else
                    limpiar_razones();
            }
        });

        rg_hipoglucemias.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_hipoglucemias_si:
                        lyt_hipogluc.setVisibility(View.VISIBLE);
                        data_hipoglucemias = "1";
                        check2 = false;
                        break;
                    case R.id.datos_hipoglucemias_no:
                        lyt_hipogluc.setVisibility(View.GONE);
                        data_hipoglucemias = "0";
                        check2 = true;
                        et_hipoglucemias.setText("");
                        det_hipoglucemias = "";
                        break;
                    default:
                        break;
                }

                if(check1&&check2&&(diabetes==1))
                    lyt_razones_med.setVisibility(View.VISIBLE);
                else
                    limpiar_razones();

            }
        });

        rg_medicinas_presion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_medicinas_presion_si:
                        lyt_medicinas_presion.setVisibility(View.VISIBLE);
                        data_medicina_presion = "1";
                        break;
                    case R.id.datos_medicinas_presion_no:
                        lyt_medicinas_presion.setVisibility(View.GONE);
                        data_medicina_presion = "0";
                        et_medicina_presion.setText("");
                        det_medicina_presion = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_analgesicos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_analgesicos_si:
                        lyt_medicinas_analgesicos.setVisibility(View.VISIBLE);
                        data_analgesicos = "1";
                        break;
                    case R.id.datos_analgesicos_no:
                        lyt_medicinas_analgesicos.setVisibility(View.GONE);
                        data_analgesicos = "0";
                        et_analgesicos.setText("");
                        det_analgesicos = "";
                        break;
                    default:
                        break;
                }
            }
        });

        rg_medicinas_otros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_medicinas_otros_si:
                        lyt_medicinas_otros.setVisibility(View.VISIBLE);
                        data_medicinas_otros = "1";
                        break;
                    case R.id.datos_medicinas_otros_no:
                        lyt_medicinas_otros.setVisibility(View.GONE);
                        data_medicinas_otros = "0";
                        et_medicinas_otros.setText("");
                        det_medicinas_otros = "";
                        break;
                    default:
                        break;
                }
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
        if (this.isVisible()) {
            InformacionGeneral act_inf_gen = (InformacionGeneral) getActivity();
            diabetes = 0;
            diabetes = act_inf_gen.tiene_diabetes;
            if(diabetes==0)
                limpiar_razones();
            else if(check1&&check2&&(diabetes==1))
                lyt_razones_med.setVisibility(View.VISIBLE);
            //Comprobamos si el fragment ya no es visible para el usuario.
            if (!isVisibleToUser) {
                //Rellenamos el hash con los datos obtenidos de los componentes.
                setearHash();
                //Llamamos al interface.
                interface_Medicamentos.onChangeTabMedicamentos(datos_medicamentos);
            }
        }
    }

    public void limpiar_razones(){
        lyt_razones_med.setVisibility(View.GONE);
        chk_razon_1.setChecked(false);
        chk_razon_2.setChecked(false);
        chk_razon_3.setChecked(false);
        chk_razon_4.setChecked(false);
        data_razon_1="0";
        data_razon_2="0";
        data_razon_3="0";
        data_razon_4="0";
    }

    public void setearHash(){

        datos_medicamentos.clear();

        //Colectamos los datos de si toma insulina y lo recolectamos.
        if (data_insulina != null && !data_insulina.isEmpty()){
            if(data_insulina.equals("0") || data_insulina.equals("1"))
                datos_medicamentos.put("insulina", data_insulina);
            else
                datos_medicamentos.put("insulina", "-1");
        }
        else
            datos_medicamentos.put("insulina", "-1");

        //Colectamos los datos de si toma hipoglucemias y si la toma lo recolectamos.
        if (data_hipoglucemias != null && !data_hipoglucemias.isEmpty()){
            if(data_hipoglucemias.equals("0")) {
                datos_medicamentos.put("hipoglucemias", data_hipoglucemias);
                datos_medicamentos.put("det_hipoglucemias", "");
            }
            else if(data_hipoglucemias.equals("1")) {
                datos_medicamentos.put("hipoglucemias", data_hipoglucemias);
                det_hipoglucemias = et_hipoglucemias.getText().toString();
                if(!det_hipoglucemias.isEmpty())
                    datos_medicamentos.put("det_hipoglucemias", det_hipoglucemias);
                else
                    datos_medicamentos.put("det_hipoglucemias", "-1");
            }
            else{
                datos_medicamentos.put("hipoglucemias", "-1");
                datos_medicamentos.put("det_hipoglucemias", "");
            }
        }
        else {
            datos_medicamentos.put("hipoglucemias", "-1");
            datos_medicamentos.put("det_hipoglucemias", "");
        }

        //Colectamos los datos de por que no toma medicamentos, si tiene diabetes.
        if (data_hipoglucemias != null && !data_hipoglucemias.isEmpty()
                && data_insulina!= null && !data_insulina.isEmpty()){
            if(data_hipoglucemias.equals("0") && data_insulina.equals("0")
                    && lyt_razones_med.getVisibility() == View.VISIBLE) {

                if(chk_razon_1.isChecked())
                    data_razon_1 = "1";
                else
                    data_razon_1 = "0";

                if(chk_razon_2.isChecked())
                    data_razon_2 = "1";
                else
                    data_razon_2 = "0";

                if(chk_razon_3.isChecked())
                    data_razon_3 = "1";
                else
                    data_razon_3 = "0";

                if(chk_razon_4.isChecked())
                    data_razon_4 = "1";
                else
                    data_razon_4 = "0";

                datos_medicamentos.put("razon_1", data_razon_1);
                datos_medicamentos.put("razon_2", data_razon_2);
                datos_medicamentos.put("razon_3", data_razon_3);
                datos_medicamentos.put("razon_4", data_razon_4);
            }
            else{
                datos_medicamentos.put("razon_1", "0" );
                datos_medicamentos.put("razon_2", "0");
                datos_medicamentos.put("razon_3", "0");
                datos_medicamentos.put("razon_4", "0");
            }
        }
        else {
            datos_medicamentos.put("razon_1", "0" );
            datos_medicamentos.put("razon_2", "0");
            datos_medicamentos.put("razon_3", "0");
            datos_medicamentos.put("razon_4", "0");
        }

        //Colectamos los datos de si toma medicinas para la presion y si la toma lo recolectamos.
        if (data_medicina_presion != null && !data_medicina_presion.isEmpty()){
            if(data_medicina_presion.equals("0")) {
                datos_medicamentos.put("medicina_presion", data_medicina_presion);
                datos_medicamentos.put("det_medicina_presion", "");
            }
            else if(data_medicina_presion.equals("1")) {
                datos_medicamentos.put("medicina_presion", data_medicina_presion);
                det_medicina_presion = et_medicina_presion.getText().toString();
                if(!det_medicina_presion.isEmpty())
                    datos_medicamentos.put("det_medicina_presion", det_medicina_presion);
                else
                    datos_medicamentos.put("det_medicina_presion", "-1");
            }
            else{
                datos_medicamentos.put("medicina_presion", "-1");
                datos_medicamentos.put("det_medicina_presion", "");
            }
        }
        else {
            datos_medicamentos.put("medicina_presion", "-1");
            datos_medicamentos.put("det_medicina_presion", "");
        }

        //Colectamos los datos de si toma analgesicos y si la toma lo recolectamos.
        if (data_analgesicos != null && !data_analgesicos.isEmpty()){
            if(data_analgesicos.equals("0")) {
                datos_medicamentos.put("analgesicos", data_analgesicos);
                datos_medicamentos.put("det_analgesicos", "");
            }
            else if(data_analgesicos.equals("1")) {
                datos_medicamentos.put("analgesicos", data_analgesicos);
                det_analgesicos = et_analgesicos.getText().toString();
                if(!det_analgesicos.isEmpty())
                    datos_medicamentos.put("det_analgesicos", det_analgesicos);
                else
                    datos_medicamentos.put("det_analgesicos", "-1");
            }
            else{
                datos_medicamentos.put("analgesicos", "-1");
                datos_medicamentos.put("det_analgesicos", "");
            }
        }
        else {
            datos_medicamentos.put("analgesicos", "-1");
            datos_medicamentos.put("det_analgesicos", "");
        }

        //Colectamos los datos de si toma otros medicamentos y si los toma los recolectamos.
        if (data_medicinas_otros != null && !data_medicinas_otros.isEmpty()){
            if(data_medicinas_otros.equals("0")) {
                datos_medicamentos.put("medicinas_otros", data_medicinas_otros);
                datos_medicamentos.put("det_medicinas_otros", "");
            }
            else if(data_medicinas_otros.equals("1")) {
                datos_medicamentos.put("medicinas_otros", data_medicinas_otros);
                det_medicinas_otros = et_medicinas_otros.getText().toString();
                if(!det_medicinas_otros.isEmpty())
                    datos_medicamentos.put("det_medicinas_otros", det_medicinas_otros);
                else
                    datos_medicamentos.put("det_medicinas_otros", "-1");

            }
            else{
                datos_medicamentos.put("medicinas_otros", "-1");
                datos_medicamentos.put("det_medicinas_otros", "");
            }
        }
        else {
            datos_medicamentos.put("medicinas_otros", "-1");
            datos_medicamentos.put("det_medicinas_otros", "");
        }

        //Colectamos los datos de cual de los medicamentos especiales toma.
        if(chk_med_1.isChecked())
            data_med_1 = "1";
        else
            data_med_1 = "0";

        if(chk_med_2.isChecked())
            data_med_2 = "1";
        else
            data_med_2 = "0";

        if(chk_med_3.isChecked())
            data_med_3 = "1";
        else
            data_med_3 = "0";

        if (!data_med_1.isEmpty()&&!data_med_2.isEmpty()&&
                !data_med_3.isEmpty()){
            datos_medicamentos.put("med_1", data_med_1);
            datos_medicamentos.put("med_2", data_med_2);
            datos_medicamentos.put("med_3", data_med_3);
        }
        else{
            datos_medicamentos.put("med_1", "0");
            datos_medicamentos.put("med_2", "0");
            datos_medicamentos.put("med_3", "0");
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// FIN - METODOS AUXILIARES /////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
}

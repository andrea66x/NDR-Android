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
                        check1 = true;
                        break;
                    case R.id.datos_insulina_no:
                        data_insulina = "0";
                        check1 = false;
                        break;
                    default:
                        break;
                }
                if(check1&&check2)
                    lyt_razones_med.setVisibility(View.VISIBLE);
                else if(!check1&&check2){
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
                        check2 = true;
                        break;
                    case R.id.datos_hipoglucemias_no:
                        lyt_hipogluc.setVisibility(View.GONE);
                        data_hipoglucemias = "0";
                        check2 = false;
                        et_hipoglucemias.setText("");
                        det_hipoglucemias = "";
                        break;
                    default:
                        break;
                }

                if(check1&&check2)
                    lyt_razones_med.setVisibility(View.VISIBLE);
                else if(!check1&&check2) {
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
}

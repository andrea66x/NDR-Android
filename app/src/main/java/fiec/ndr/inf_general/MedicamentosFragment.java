package fiec.ndr.inf_general;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fiec.ndr.R;

/* Fragmento Salud*/
public class MedicamentosFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public MedicamentosFragment() {
    }

    public static MedicamentosFragment newInstance(int sectionNumber) {
        MedicamentosFragment fragment = new MedicamentosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medicamentos, container, false);
        final LinearLayout lyt_hipogluc = (LinearLayout) rootView.findViewById(R.id.lyt_hipoglucemias);
        final LinearLayout lyt_medicinas_presion = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_presion);
        final LinearLayout lyt_medicinas_analgesicos = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_analgesicos);
        final LinearLayout lyt_medicinas_otros = (LinearLayout) rootView.findViewById(R.id.lyt_medicinas_otros);

        RadioGroup rg_hipoglucemias = (RadioGroup) rootView.findViewById(R.id.rg_hipoglucemias);
        rg_hipoglucemias.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_hipoglucemias_si:
                        lyt_hipogluc.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_hipoglucemias_no:
                        lyt_hipogluc.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_medicinas_presion = (RadioGroup) rootView.findViewById(R.id.rg_medicinas_presion);
        rg_medicinas_presion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_medicinas_presion_si:
                        lyt_medicinas_presion.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_medicinas_presion_no:
                        lyt_medicinas_presion.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_analgesicos = (RadioGroup) rootView.findViewById(R.id.rg_analgesicos);
        rg_analgesicos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_analgesicos_si:
                        lyt_medicinas_analgesicos.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_analgesicos_no:
                        lyt_medicinas_analgesicos.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_medicinas_otros = (RadioGroup) rootView.findViewById(R.id.rg_medicinas_otros);
        rg_medicinas_otros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_medicinas_otros_si:
                        lyt_medicinas_otros.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_medicinas_otros_no:
                        lyt_medicinas_otros.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        Spinner spinner_medicinas_presion_otros = (Spinner) rootView.findViewById(R.id.datos_medicinas_presion_otros);
        List<String> medicinas_presion_otros = new ArrayList<>();
        medicinas_presion_otros.add("Enalapril");
        medicinas_presion_otros.add("Captopril");
        medicinas_presion_otros.add("Ibersatan");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, medicinas_presion_otros);
        dataAdapter.setDropDownViewResource(R.layout.spinners);
        spinner_medicinas_presion_otros.setAdapter(dataAdapter);

        return rootView;
    }
}

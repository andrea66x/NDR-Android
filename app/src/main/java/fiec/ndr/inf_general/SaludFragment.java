package fiec.ndr.inf_general;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import fiec.ndr.R;

public class SaludFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public SaludFragment() {
    }

    public static SaludFragment newInstance(int sectionNumber) {
        SaludFragment fragment = new SaludFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_salud, container, false);
        final LinearLayout lyt_seguro = (LinearLayout) rootView.findViewById(R.id.lyt_seguro);
        final LinearLayout lyt_chequeo = (LinearLayout) rootView.findViewById(R.id.lyt_chequeo);
        final LinearLayout lyt_tiempo_diabetes = (LinearLayout) rootView.findViewById(R.id.lyt_tiempo_diabetes);
        final LinearLayout lyt_renal = (LinearLayout) rootView.findViewById(R.id.lyt_renal);
        final LinearLayout lyt_enfermedad = (LinearLayout) rootView.findViewById(R.id.lyt_enfermedad);

        RadioGroup rg_seguro = (RadioGroup) rootView.findViewById(R.id.rg_seguro);
        rg_seguro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_seguro_si:
                        lyt_seguro.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_seguro_no:
                        lyt_seguro.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_chequeo = (RadioGroup) rootView.findViewById(R.id.rg_chequeo);
        rg_chequeo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_chequeo_si:
                        lyt_chequeo.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_chequeo_no:
                        lyt_chequeo.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_tmp_diabetes = (RadioGroup) rootView.findViewById(R.id.rg_tmp_diabetes);
        rg_tmp_diabetes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_diabetes_si:
                        lyt_tiempo_diabetes.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_diabetes_no:
                        lyt_tiempo_diabetes.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_renal = (RadioGroup) rootView.findViewById(R.id.rg_renal);
        rg_renal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_renal_si:
                        lyt_renal.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_renal_no:
                        lyt_renal.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_enfermedad = (RadioGroup) rootView.findViewById(R.id.rg_enfermedad);
        rg_enfermedad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_enfermedad_si:
                        lyt_enfermedad.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_enfermedad_no:
                        lyt_enfermedad.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
        return rootView;
    }
}

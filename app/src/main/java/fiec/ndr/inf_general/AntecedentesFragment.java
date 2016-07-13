package fiec.ndr.inf_general;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import fiec.ndr.R;

public class AntecedentesFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public AntecedentesFragment() {
    }

    public static AntecedentesFragment newInstance(int sectionNumber) {
        AntecedentesFragment fragment = new AntecedentesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_antecedentes, container, false);
        final LinearLayout lyt_ant_fam_renal = (LinearLayout) rootView.findViewById(R.id.lyt_ant_fam_renal);

        RadioGroup rg_ant_renal = (RadioGroup) rootView.findViewById(R.id.rg_ant_renal);
        rg_ant_renal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_ant_renal_si:
                        lyt_ant_fam_renal.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_ant_renal_no:
                        lyt_ant_fam_renal.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
        return rootView;
    }
}

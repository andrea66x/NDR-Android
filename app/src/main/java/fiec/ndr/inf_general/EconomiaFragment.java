package fiec.ndr.inf_general;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fiec.ndr.R;

public class EconomiaFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public EconomiaFragment() {
    }

    public static EconomiaFragment newInstance(int sectionNumber) {
        EconomiaFragment fragment = new EconomiaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_economia_familiar, container, false);

        //Spinner para la ocupación:
        Spinner spinner_ocupacion = (Spinner) rootView.findViewById(R.id.datos_ocupacion);
        List<String> ocupacion = new ArrayList<>();
        ocupacion.add("Jubilado");
        ocupacion.add("No Trabaja");
        ocupacion.add("Estudia");
        ocupacion.add("Ama de Casa");
        ocupacion.add("Empleado");
        ocupacion.add("Cuenta Propia");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, ocupacion);
        dataAdapter1.setDropDownViewResource(R.layout.spinners);
        spinner_ocupacion.setAdapter(dataAdapter1);

        //Spinner para los estudios:
        Spinner spinner_estudio = (Spinner) rootView.findViewById(R.id.datos_estudios);
        List<String> estudios = new ArrayList<>();
        estudios.add("Analfabeto");
        estudios.add("Primarios Completos");
        estudios.add("Primarios Incompletos");
        estudios.add("Secundarios Completos");
        estudios.add("Secundarios Incompletos");
        estudios.add("Terciarios Completos");
        estudios.add("Terciarios Incompletos");
        estudios.add("Universitarios Completos");
        estudios.add("Universitarios Incompletos");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, estudios);
        dataAdapter2.setDropDownViewResource(R.layout.spinners);
        spinner_estudio.setAdapter(dataAdapter2);

        return rootView;
    }
}

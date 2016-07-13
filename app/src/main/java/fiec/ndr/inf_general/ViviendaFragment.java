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

public class ViviendaFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public ViviendaFragment() {
    }

    public static ViviendaFragment newInstance(int sectionNumber) {
        ViviendaFragment fragment = new ViviendaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vivienda, container, false);

        //Spinner para la provincia:
        Spinner spinner_provincia = (Spinner) rootView.findViewById(R.id.datos_provincia);
        List<String> provincias = new ArrayList<>();
        provincias.add("Guayas");
        provincias.add("Esmeraldas");
        provincias.add("Los Ríos");
        provincias.add("El Oro");
        provincias.add("Manabí");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, provincias);
        dataAdapter1.setDropDownViewResource(R.layout.spinners);
        spinner_provincia.setAdapter(dataAdapter1);

        //Spinner para el canton:
        Spinner spinner_canton = (Spinner) rootView.findViewById(R.id.datos_canton);
        List<String> cantones = new ArrayList<>();
        cantones.add("Balzar");
        cantones.add("Guayaquil");
        cantones.add("Manta");
        cantones.add("Portoviejo");
        cantones.add("Esmeraldas");
        cantones.add("Balzar");
        cantones.add("Guayaquil");
        cantones.add("Manta");
        cantones.add("Portoviejo");
        cantones.add("Esmeraldas");
        cantones.add("Balzar");
        cantones.add("Guayaquil");
        cantones.add("Manta");
        cantones.add("Portoviejo");
        cantones.add("Esmeraldas");
        cantones.add("Balzar");
        cantones.add("Guayaquil");
        cantones.add("Manta");
        cantones.add("Portoviejo");
        cantones.add("Esmeraldas");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, cantones);
        dataAdapter2.setDropDownViewResource(R.layout.spinners);
        spinner_canton.setAdapter(dataAdapter2);

        //Spinner para la vivienda:
        Spinner spinner_vivienda = (Spinner) rootView.findViewById(R.id.datos_vivienda);
        List<String> vivienda = new ArrayList<>();
        vivienda.add("Ciudad");
        vivienda.add("Campo");
        vivienda.add("Alrededor de la Ciudad");
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, vivienda);
        dataAdapter3.setDropDownViewResource(R.layout.spinners);
        spinner_vivienda.setAdapter(dataAdapter3);

        //Spinner para el agua:
        Spinner spinner_agua = (Spinner) rootView.findViewById(R.id.datos_agua);
        List<String> agua = new ArrayList<>();
        agua.add("Intubada");
        agua.add("Cisterna");
        agua.add("Tanquero");
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, agua);
        dataAdapter4.setDropDownViewResource(R.layout.spinners);
        spinner_agua.setAdapter(dataAdapter4);

        return rootView;
    }
}

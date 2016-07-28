package fiec.ndr.inf_general;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiec.ndr.AdminSQLiteOpenHelper;
import fiec.ndr.R;

public class ViviendaFragment extends Fragment {

    static Spinner spinner_canton;
    AdminSQLiteOpenHelper admin;
    private Map<String, String> datos_vivienda = new HashMap<String, String>();


    ///////////////////////////////////////////////////////////////////////////
    /////////////////// INICIO - INTERFACES ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    changeTabVivienda interface_Vivienda;

    public interface changeTabVivienda {
        void onChangeTabVivienda(Map<String, String> datos_vivienda);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Esto me asegura que el activity que llama a la interfaz
        // la haya implementado, sino lanza una excepcion.
        try {
            interface_Vivienda = (changeTabVivienda) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Error: Se debe implementar la interfaz changeTab");
        }
    }

    @Override
    public void onDetach() {
        interface_Vivienda = null; // previene el "leaking"
        super.onDetach();
    }

    ////////////////////////////////////////////////////////////////////////
    ///////////////////FIN -- INTERFACES ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////


    /*
        //Este metodo de instanciar el fragment, sirve para mantener variables
        //cuando se recree, evitando que se llame al constructor vacio.
        public static ViviendaFragment newInstance(int sectionNumber) {
            ViviendaFragment fragment = new ViviendaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vivienda, container, false);
        admin = new AdminSQLiteOpenHelper(rootView.getContext());
        final Context fragmentContext = rootView.getContext();

        //Spinner para la provincia:
        Spinner spinner_provincia = (Spinner) rootView.findViewById(R.id.datos_provincia);
        List<String> provincias = admin.getProvincias();
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, provincias);
        dataAdapter1.setDropDownViewResource(R.layout.spinners);
        spinner_provincia.setAdapter(dataAdapter1);

        //Spinner para los cantones
        spinner_canton = (Spinner) rootView.findViewById(R.id.datos_canton);

        spinner_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(),"position: "+position+"id: "+id,Toast.LENGTH_LONG).show();
                List<String> cantones = admin.getCantones(position);
                //Toast.makeText(view.getContext(),cantones.get(0), Toast.LENGTH_LONG).show();
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(fragmentContext, R.layout.spinners, cantones);
                dataAdapter2.setDropDownViewResource(R.layout.spinners);
                spinner_canton.setAdapter(dataAdapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

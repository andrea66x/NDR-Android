package fiec.ndr.inf_general;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fiec.ndr.R;

public class HabitosFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static Button btn_guardar;
    static Button btn_leer;
    static TextView tv;
    static EditText et1, et2, et3;
    public HabitosFragment() {
    }

    public static HabitosFragment newInstance(int sectionNumber) {
        HabitosFragment fragment = new HabitosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_habitos, container, false);
        tv = (TextView) rootView.findViewById(R.id.salidaJson);
        btn_guardar = (Button) rootView.findViewById(R.id.btn_GuardarHb);
        /*btn_guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //convertToJson();
                String texto = convertToJson();
                try {
                    FileOutputStream fileOutputStream = v.getContext().openFileOutput("mi_archivo.txt", MODE_PRIVATE);
                    fileOutputStream.write(texto.getBytes());
                    Toast.makeText(v.getContext(),"Archivo guardado correctamente",Toast.LENGTH_LONG).show();
                    fileOutputStream.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(v.getContext(),convertToJson(),Toast.LENGTH_LONG).show();
            }
        });

        btn_leer = (Button) rootView.findViewById(R.id.btn_LeerHb);
        btn_leer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInputStream = v.getContext().openFileInput("mi_archivo.txt");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    String lines;
                    while((lines = bufferedReader.readLine()) != null){
                        stringBuffer.append(lines+"\n");
                    }
                    Toast.makeText(v.getContext(),stringBuffer.toString(),Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

        final LinearLayout lyt_tabaco = (LinearLayout) rootView.findViewById(R.id.lyt_tabaco);
        final LinearLayout lyt_alcohol = (LinearLayout) rootView.findViewById(R.id.lyt_alcohol);
        final LinearLayout lyt_otros = (LinearLayout) rootView.findViewById(R.id.lyt_otros);

        RadioGroup rg_tabaco = (RadioGroup) rootView.findViewById(R.id.rg_tabaco);
        rg_tabaco.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_tabaco_si:
                        lyt_tabaco.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_tabaco_no:
                        lyt_tabaco.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_alcohol = (RadioGroup) rootView.findViewById(R.id.rg_alcohol);
        rg_alcohol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_alcohol_si:
                        lyt_alcohol.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_alcohol_no:
                        lyt_alcohol.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        RadioGroup rg_otros = (RadioGroup) rootView.findViewById(R.id.rg_otros);
        rg_otros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.datos_otros_si:
                        lyt_otros.setVisibility(View.VISIBLE);
                        break;
                    case R.id.datos_otros_no:
                        lyt_otros.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        Spinner spinner_act_fisica = (Spinner) rootView.findViewById(R.id.datos_ejercicios);
        List<String> act_fisica = new ArrayList<>();
        act_fisica.add("No hago actividad física");
        act_fisica.add("Solo hago ejercicios en el tiempo libre");
        act_fisica.add("Hago ejercicios mas de 3 veces por semana");
        act_fisica.add("Hago ejercicios todos los dias");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(rootView.getContext(), R.layout.spinners, act_fisica);
        dataAdapter.setDropDownViewResource(R.layout.spinners);
        spinner_act_fisica.setAdapter(dataAdapter);

        return rootView;
    }
/*
    public String convertToJson(){
        JSONObject miJson = new JSONObject();
        try{
            miJson.put("codigo",miFormulario.getCodigo());
            miJson.put("tipo","datos personales");
            miJson.put("nombres",miFormulario.getNombres());
            miJson.put("apellidos",miFormulario.getApellidos());
            miJson.put("sexo",miFormulario.getSexo());
            miJson.put("edad",miFormulario.getEdad());
            miJson.put("fec_nac",miFormulario.getFec_nac());
            miJson.put("telefono",miFormulario.getTelefono());
            miJson.put("est_civil",miFormulario.getEst_civil());
            miJson.put("origen",miFormulario.getOrigen());

        } catch (JSONException e){
            e.printStackTrace();
        }

        String json = miJson.toString();
        return json;
        //tv.setText(json);
    }*/
}

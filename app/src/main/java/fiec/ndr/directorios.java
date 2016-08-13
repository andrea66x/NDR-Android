package fiec.ndr;


import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Directorios {

    final static String root = "/NDR";

    final static String forms_preparacion = root + "/Preparación";
    final static String forms_fotos = forms_preparacion + "/Consentimientos-Informados";
    final static String forms = forms_preparacion + "/Inicial";

    final static String forms_general = root + "/Informacion-General";
    final static String forms_medidas = root + "/Medidas";
    final static String forms_presion = root + "/Presión";
    final static String forms_laboratorio = root + "/Laboratorio";



    public Directorios(boolean inicial){

        if(inicial){
            existeDir(root);
            existeDir(forms_preparacion);
            existeDir(forms_fotos);
            existeDir(forms);
            existeDir(forms_general);
            existeDir(forms_medidas);
            existeDir(forms_presion);
            existeDir(forms_laboratorio);
        }

    }



    public File existeDir(String ruta){
        File folder = new File(Environment.getExternalStorageDirectory(), ruta);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
            Log.d("DIRECTORIOS", "Intentando crear directorio: "+Environment.getExternalStorageDirectory().toString());
        }
        if (success) {
            Log.d("DIRECTORIOS", "El directorio esta listo para usarse.");
        } else {
            Log.d("DIRECTORIOS", "El directorio ya existe o salio algo mal.");
        }
        return folder;
    }

    public int guardarAchivo(String JSON, String codigo, int tipo){

        String root = Environment.getExternalStorageDirectory().toString();
        FileOutputStream fos = null;
        Writer out = null;
        File carpeta;
        File archivo;
        String fname;


        switch (tipo){

            //Preparacion: Ruta /NDR/Preparacion/Inicial
            case 1:
                carpeta = existeDir(forms);
                fname = "prepa_"+ codigo +".json";
                break;

            //Informacion General: Ruta /NDR/Informacion-General
            case 2:
                carpeta = existeDir(forms_general);
                fname = "infog_"+ codigo +".json";
                break;

            //Medidas: Ruta /NDR/Medidas
            case 3:
                carpeta = existeDir(forms_medidas);
                fname = "medidas_"+ codigo +".json";
                break;

            //Presion: Ruta /NDR/Presion
            case 4:
                carpeta = existeDir(forms_presion);
                fname = "presion_"+ codigo +".json";
                break;

            //Medidas: Ruta /NDR/Laboratorio
            case 5:
                carpeta = existeDir(forms_laboratorio);
                fname = "lab_"+ codigo +".json";
                break;

            default:
                carpeta = existeDir(root);
                fname = "error.json";
                break;

        }

        archivo = new File (carpeta, fname);
        if (archivo.exists())
            return 0;

        try {
            fos = new FileOutputStream(archivo);
            out = new OutputStreamWriter(fos, "UTF-8");
            out.write(JSON);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {
                    return -1;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                    return -1;
                }
            }
        }

        return 1;

    }

}



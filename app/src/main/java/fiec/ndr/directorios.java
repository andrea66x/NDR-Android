package fiec.ndr;


import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Directorios {

    final public static String root = "/NDR";

    final public static String forms_preparacion = root + "/Preparacion";
    final public static String forms_fotos = forms_preparacion + "/Consentimientos-Informados";
    final public static String forms = forms_preparacion + "/Inicial";

    final public static String forms_general = root + "/Informacion-General";
    final public static String forms_medidas = root + "/Medidas";
    final public static String forms_presion = root + "/Presion";
    final public static String forms_laboratorio = root + "/Laboratorio";



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

    public Directorios(){
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
                fname = "prep_"+ codigo +".json";
                break;

            //Informacion General: Ruta /NDR/Informacion-General
            case 2:
                carpeta = existeDir(forms_general);
                fname = "info_"+ codigo +".json";
                break;

            //Medidas: Ruta /NDR/Medidas
            case 3:
                carpeta = existeDir(forms_medidas);
                fname = "medi_"+ codigo +".json";
                break;

            //Presion: Ruta /NDR/Presion
            case 4:
                carpeta = existeDir(forms_presion);
                fname = "pres_"+ codigo +".json";
                break;

            //Medidas: Ruta /NDR/Laboratorio
            case 5:
                carpeta = existeDir(forms_laboratorio);
                fname = "labo_"+ codigo +".json";
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

    public void moveFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + "/" + inputFile);
            out = new FileOutputStream(outputPath + "/"+ inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();


        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    }



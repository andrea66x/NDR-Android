package fiec.ndr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea on 20/07/2016.
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "administracion.db";

    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //aquí creamos las tablas
        db.execSQL("create table responsable(id_responsable integer primary key, user text, password text, rol text)");
        db.execSQL("create table localidad(id_localidad integer primary key, nombre text, padre_id integer NULL, FOREIGN KEY (padre_id) REFERENCES localidad (id_localidad))");
        db.execSQL("create table preparacion(id_preparacion integer primary key, ruta_json text, ruta_img text, fecha text, lugar text, ayunas text, id_responsable integer, FOREIGN KEY (id_responsable) REFERENCES responsable (id_responsable))");
        db.execSQL("create table inf_general(id_inf_general integer primary key, ruta_json text, fecha text, id_responsable integer, FOREIGN KEY (id_responsable) REFERENCES responsable (id_responsable))");
        db.execSQL("create table medidas(id_medidas integer primary key, ruta_json text, fecha text, id_responsable integer, FOREIGN KEY (id_responsable) REFERENCES responsable (id_responsable))");
        db.execSQL("create table presion_arterial(id_presion integer primary key, ruta_json text, fecha text, id_responsable integer, FOREIGN KEY (id_responsable) REFERENCES responsable (id_responsable))");
        db.execSQL("create table laboratorio(id_laboratorio integer primary key, ruta_json text, fecha text, id_responsable integer, FOREIGN KEY (id_responsable) REFERENCES responsable (id_responsable))");
        db.execSQL("create table formulario(id_formulario integer primary key, codigo text, id_preparacion integer NULL, id_inf_general integer NULL, id_medidas integer NULL, id_presion integer NULL, id_laboratorio integer NULL, FOREIGN KEY(id_preparacion) REFERENCES(preparacion),FOREIGN KEY(id_inf_general) REFERENCES(inf_general),FOREIGN KEY(id_medidas) REFERENCES(medidas),FOREIGN KEY(id_presion) REFERENCES(presion_arterial),FOREIGN KEY(id_laboratorio) REFERENCES(laboratorio))");

        insertarLocalidades(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS responsable");
        db.execSQL("DROP TABLE IF EXISTS Localidad");
        db.execSQL("DROP TABLE IF EXISTS preparacion");
        db.execSQL("DROP TABLE IF EXISTS inf_general");
        db.execSQL("DROP TABLE IF EXISTS medidas");
        db.execSQL("DROP TABLE IF EXISTS presion_arterial");
        db.execSQL("DROP TABLE IF EXISTS laboratorio");
        db.execSQL("DROP TABLE IF EXISTS formulario");
        // Create tables again
        onCreate(db);
    }

    public void insertarLocalidades(SQLiteDatabase db){
        ContentValues registro = new ContentValues();

        ///*** PROVINCIAS ***///

        registro.put("id_localidad",0);
        registro.put("nombre","Esmeraldas");
        db.insert("localidad", null, registro);

        registro.put("id_localidad",1);
        registro.put("nombre","Manabí");
        db.insert("localidad", null, registro);

        registro.put("id_localidad",2);
        registro.put("nombre","Guayas");
        db.insert("localidad", null, registro);

        registro.put("id_localidad",3);
        registro.put("nombre","Los Ríos");
        db.insert("localidad", null, registro);

        registro.put("id_localidad",4);
        registro.put("nombre","El Oro");
        db.insert("localidad", null, registro);

        registro.put("id_localidad",5);
        registro.put("nombre","Santa Elena");
        db.insert("localidad", null, registro);

        ///*** CANTONES ***///

        /***********************/
        /** Esmeraldas ===> 7 **/
        /***********************/

        registro.put("id_localidad",6);
        registro.put("nombre","Esmeraldas");
        registro.put("padre_id",0);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",7);
        registro.put("nombre","Eloy Alfaro");
        registro.put("padre_id",0);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",8);
        registro.put("nombre","Muisne");
        registro.put("padre_id",0);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",9);
        registro.put("nombre","Quinindé");
        registro.put("padre_id",0);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",10);
        registro.put("nombre","San Lorenzo");
        registro.put("padre_id",0);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",11);
        registro.put("nombre","Atacames");
        registro.put("padre_id",0);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",12);
        registro.put("nombre","Río verde");
        registro.put("padre_id",0);
        db.insert("localidad", null, registro);

        /********************/
        /** Manabí ===> 22 **/
        /********************/

        registro.put("id_localidad",13);
        registro.put("nombre","Bolívar");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",14);
        registro.put("nombre","Chone");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",15);
        registro.put("nombre","El Carmen");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",16);
        registro.put("nombre","Flavio Alfaro");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",17);
        registro.put("nombre","Jama");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",18);
        registro.put("nombre","Jaramijó");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",19);
        registro.put("nombre","Jipijapa");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",20);
        registro.put("nombre","Junín");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",21);
        registro.put("nombre","Manta");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",22);
        registro.put("nombre","Montecristi");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",23);
        registro.put("nombre","Olmedo");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",24);
        registro.put("nombre","Paján");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",25);
        registro.put("nombre","Pedernales");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",26);
        registro.put("nombre","Pichincha");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",27);
        registro.put("nombre","Portoviejo");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",28);
        registro.put("nombre","Puerto López");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",29);
        registro.put("nombre","Rocafuerte");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",30);
        registro.put("nombre","San Vicente");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",31);
        registro.put("nombre","Santa Ana");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",32);
        registro.put("nombre","Sucre");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",33);
        registro.put("nombre","Tosagua");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",34);
        registro.put("nombre","24 de Mayo");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        /********************/
        /** Guayas ===> 25 **/
        /********************/

        registro.put("id_localidad",35);
        registro.put("nombre","Balao");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",36);
        registro.put("nombre","Balzar");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",37);
        registro.put("nombre","Baquerizo Moreno");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",38);
        registro.put("nombre","Colimes");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",39);
        registro.put("nombre","Coronel Marcelino Maridueña");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",40);
        registro.put("nombre","Daule");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",41);
        registro.put("nombre","Durán");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",42);
        registro.put("nombre","El Empalme");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",43);
        registro.put("nombre","El Triunfo");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",44);
        registro.put("nombre","General Antonio Elizalde");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",45);
        registro.put("nombre","Guayaquil");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",46);
        registro.put("nombre","Isidro Ayora");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",47);
        registro.put("nombre","Lomas de Sargentillo");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",48);
        registro.put("nombre","Milagro");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",49);
        registro.put("nombre","Naranjal");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",50);
        registro.put("nombre","Naranjito");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",51);
        registro.put("nombre","Nobol");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",52);
        registro.put("nombre","Palestina");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",53);
        registro.put("nombre","Pedro Carbo");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",54);
        registro.put("nombre","Playas");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",55);
        registro.put("nombre","Salitre");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",56);
        registro.put("nombre","Samborondón");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",57);
        registro.put("nombre","San Jacinto de Yaguachi");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",58);
        registro.put("nombre","Santa Lucía");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",59);
        registro.put("nombre","Simón Bolívar");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        /**********************/
        /** Los Ríos ===> 13 **/
        /**********************/

        registro.put("id_localidad",60);
        registro.put("nombre","Baba");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",61);
        registro.put("nombre","Babahoyo");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",62);
        registro.put("nombre","Buena Fé");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",63);
        registro.put("nombre","Catarama");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",64);
        registro.put("nombre","Mocache");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",65);
        registro.put("nombre","Montalvo");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",66);
        registro.put("nombre","Palenque");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",67);
        registro.put("nombre","Puebloviejo");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",68);
        registro.put("nombre","Quevedo");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",69);
        registro.put("nombre","Quinsaloma");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",70);
        registro.put("nombre","Urdaneta");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",71);
        registro.put("nombre","Valencia");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",72);
        registro.put("nombre","Ventanas");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",73);
        registro.put("nombre","Vinces");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        /*********************/
        /** El Oro ===>  14 **/
        /*********************/

        registro.put("id_localidad",74);
        registro.put("nombre","Arenillas");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",75);
        registro.put("nombre","Atahualpa");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",76);
        registro.put("nombre","Balsas");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",77);
        registro.put("nombre","Chilla");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",78);
        registro.put("nombre","El Guabo");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",79);
        registro.put("nombre","Huaquillas");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",80);
        registro.put("nombre","Las Lajas");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",81);
        registro.put("nombre","Machala");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",82);
        registro.put("nombre","Marcabelí");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",83);
        registro.put("nombre","Pasaje");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",84);
        registro.put("nombre","Piñas");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",19);
        registro.put("nombre","Portovelo");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",85);
        registro.put("nombre","Santa Rosa");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",86);
        registro.put("nombre","Zaruma");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        /************************/
        /** Santa Elena ===> 3 **/
        /************************/

        registro.put("id_localidad",87);
        registro.put("nombre","Libertad");
        registro.put("padre_id",5);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",88);
        registro.put("nombre","Salinas");
        registro.put("padre_id",5);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",89);
        registro.put("nombre","Santa Elena");
        registro.put("padre_id",5);
        db.insert("localidad", null, registro);
    }

    /**
     * Retorina una lista con las localidades
     * */

    public List<String> getProvincias(){
        List<String> localidades = new ArrayList<>();
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM localidad WHERE padre_id IS NULL", null);
        if (cursor.moveToFirst()) {
            do {
                localidades.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();

        return localidades;
    }

    /**
     * Retorina una lista con los cantones
     * */

    public List<String> getCantones(int padre_id){
        List<String> cantones = new ArrayList<>();
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM localidad WHERE padre_id = "+padre_id, null);
        if (cursor.moveToFirst()) {
            do {
                cantones.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();

        return cantones;
    }
}

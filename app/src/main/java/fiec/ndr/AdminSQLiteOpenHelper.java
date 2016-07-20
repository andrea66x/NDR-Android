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
        db.execSQL("create table localidad(id_localidad integer primary key, nombre text, padre_id integer NULL, FOREIGN KEY (padre_id) REFERENCES localidad (id_localidad))");
        insertarLocalidades(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Localidad");
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

        /* Esmeraldas */
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

        /* Manabí */
        registro.put("id_localidad",13);
        registro.put("nombre","Portoviejo");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",14);
        registro.put("nombre","Bolívar");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",14);
        registro.put("nombre","Chone");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",14);
        registro.put("nombre","El Carmen");
        registro.put("padre_id",1);
        db.insert("localidad", null, registro);

        /* Guayas */
        registro.put("id_localidad",15);
        registro.put("nombre","Guayaquil");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",16);
        registro.put("nombre","Baquerizo Moreno");
        registro.put("padre_id",2);
        db.insert("localidad", null, registro);

        /* Los Ríos */
        registro.put("id_localidad",17);
        registro.put("nombre","Babahoyo");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",18);
        registro.put("nombre","Baba");
        registro.put("padre_id",3);
        db.insert("localidad", null, registro);

        /* El Oro */
        registro.put("id_localidad",19);
        registro.put("nombre","Machala");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",20);
        registro.put("nombre","Arenillas");
        registro.put("padre_id",4);
        db.insert("localidad", null, registro);

        /* Santa Elena */
        registro.put("id_localidad",21);
        registro.put("nombre","Libertad");
        registro.put("padre_id",5);
        db.insert("localidad", null, registro);

        registro.put("id_localidad",22);
        registro.put("nombre","Salinas");
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

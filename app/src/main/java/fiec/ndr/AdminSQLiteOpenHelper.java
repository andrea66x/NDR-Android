package fiec.ndr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrea on 02/07/2016.
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{
    public AdminSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla para el formulario
        db.execSQL("create table formulario(codigo integer primary key, nombres text, apellidos text, edad integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists formulario");
        db.execSQL("create table formulario(codigo integer primary key, nombres text, apellidos text, edad integer)");
    }
}

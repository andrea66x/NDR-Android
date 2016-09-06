package fiec.ndr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity{

    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Componentes del Formulario
        mEmailView = (EditText) findViewById(R.id.usuario);
        mPasswordView = (EditText) findViewById(R.id.contrasena);
        Button mEmailSignInButton = (Button) findViewById(R.id.btn_ingreso);

        Directorios tempdir = new Directorios(false);
        String temp = Environment.getDataDirectory()+ "/data/fiec.ndr/databases/";
        tempdir.moveFile(temp, "administracion.db", Environment.getExternalStorageDirectory().getPath());

        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Guardamos los valores al momento de presionar el boton.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Revisamos si es una contraseña valida o si ingreso una.

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_contrasena_corta));
            focusView = mPasswordView;
            cancel = true;
        }


        // Revisamos si ingreso un correo y si tiene el formato correcto.
        else if (!isUserValid(email)) {
            mEmailView.setError(getString(R.string.error_correo_invalido));
            focusView = mEmailView;
            cancel = true;
        }

        if(!ingresoExitoso(email,password)){
            mEmailView.setError("Error en combinación de Usuario/Contraseña");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            //Hubo algun error, hacemos focus al campo requerido.
            focusView.requestFocus();
        }
        else{
            Intent int_ingreso = new Intent(this,MenuPrincipal.class);
            startActivity(int_ingreso);
            finish();
        }
    }

    private boolean isUserValid(String email) {
        if (email.matches(".*\\w.*"))
            return true;
        else
            return false;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean ingresoExitoso(String usuario, String password){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila = bd.rawQuery("select user, password, rol from usuarios where user = '" + usuario + "'", null);
        String user,pass;
        Integer rol;

        //Verificar si existe el usuario

        if (fila.moveToFirst()) {
            user = fila.getString(0);
            pass = fila.getString(1);
            rol = Integer.valueOf(fila.getString(2));
        } else{
            bd.close();
            return false;
        }

        if(pass.equals(password)){
            SharedPreferences prefs =  getSharedPreferences("NDR_PREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("usuario", user);
            editor.putInt("rol", rol);
            editor.apply();
            bd.close();
            return true;
        } else{
            bd.close();
            return false;
        }
    }
}






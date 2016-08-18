package fiec.ndr;

import android.content.Intent;
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

    // UI references.
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

        String temp = this.getDatabasePath("administracion.db").toString();
        Directorios tempdir = new Directorios(false);
        tempdir.moveFile("/data/data/fiec.ndr/databases/", "administracion.db", Environment.getExternalStorageDirectory().getPath());

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
        /*
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_contrasena_corta));
            focusView = mPasswordView;
            cancel = true;
        }*/


        // Revisamos si ingreso un correo y si tiene el formato correcto.
        /*else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_correo_invalido));
            focusView = mEmailView;
            cancel = true;
        }*/
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_requerido));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            //Hubo algun error, hacemos focus al campo requerido.
            focusView.requestFocus();
        } else if(ingresoExitoso(email,password)){
            Intent int_ingreso = new Intent(this,MenuPrincipal.class);
            startActivity(int_ingreso);
            finish();
        }
        else{
            Toast.makeText(this, "Usuario o contraseña inválida!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean ingresoExitoso(String usuario, String password){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila = bd.rawQuery("select user, password, rol from usuarios where user = '" + usuario + "'", null);
        String d1,d2,d3;

        //Verificar si existe el usuario

        if (fila.moveToFirst()) {
            d1 = fila.getString(0);
            d2 = fila.getString(1);
            d3 = fila.getString(2);
        } else{
            bd.close();
            return false;
        }

        // Contraseña válida

        if(d2.equals(password)){
            bd.close();
            return true;
        } else{
            bd.close();
            return false;
        }
    }
}






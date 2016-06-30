package fiec.ndr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;



public class LoginActivity extends AppCompatActivity{

        // UI references.
        private EditText mEmailView;
        private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Componentes del Formulario
        mEmailView = (EditText) findViewById(R.id.correo);
        mPasswordView = (EditText) findViewById(R.id.contrasena);
        Button mEmailSignInButton = (Button) findViewById(R.id.btn_ingreso);


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
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_requerido));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_correo_invalido));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            //Hubo algun error, hacemos focus al campo requerido.
            focusView.requestFocus();
        } else {
            Intent int_ingreso = new Intent(this,MenuPrincipal.class);
            startActivity(int_ingreso);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}






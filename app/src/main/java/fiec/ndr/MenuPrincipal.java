package fiec.ndr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void nuevoFormulario(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void buscarEncuesta(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void formularios(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void sincronizarBase(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void informacion(View view){
        Intent intent = new Intent(this, Secciones.class);
        startActivity(intent);
    }

    public void salirSistema(View view){
        System.exit(0);
    }
}

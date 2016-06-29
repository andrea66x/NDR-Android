package fiec.ndr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_ingresar = (Button) findViewById(R.id.btnIngresar);
        btn_ingresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIngresar:
                Intent int_ingreso = new Intent(this,MenuPrincipal.class);
                startActivity(int_ingreso);
                break;

            default:
                break;
        }
    }
}

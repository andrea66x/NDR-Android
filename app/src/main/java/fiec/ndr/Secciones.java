package fiec.ndr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Secciones extends AppCompatActivity implements View.OnClickListener{
    Button btn_infgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secciones);

        btn_infgen = (Button) findViewById(R.id.btnInfGen);
        btn_infgen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInfGen:
                Intent int_infgen = new Intent(this,InformacionGeneral.class);
                startActivity(int_infgen);
                break;

            default:
                break;
        }
    }
}

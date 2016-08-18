package fiec.ndr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class SincronizarEncuestas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizar_encuestas);
        subirachivos();
    }



    public void subirachivos(){
        // gather your request parameters
        File myFile = new File("/NDR/Preparacion/AA11111.json");
        RequestParams params = new RequestParams();
        /*try {
            params.put("profile_picture", myFile);
        } catch(FileNotFoundException e) {}
        */

        // send request
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.1.200:8000/recibirjson", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                Toast.makeText(getApplicationContext(),"GG MADAFUCKER", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"LA FALLA", Toast.LENGTH_LONG).show();
            }
        });

    }


}

package com.example.firstjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private String url = "http://clasespersonales.com/android/listauni.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.texto);

        new CargaUnidades().execute(url);

    }

    class CargaUnidades extends AsyncTask <String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Cargando...", Toast.LENGTH_SHORT).show();
        }
        @Override
        protected String doInBackground(String... args) {
            return BajarDatos(args[0]);
        }
        @Override
        protected void onPostExecute(String resultado) {
            txt = (TextView) findViewById(R.id.texto);
            txt.setText(resultado);
        }

    }

    public static String BajarDatos(String url) {
        InputStream pt = null;
        String r = "";
        try {
            URL sitio = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) sitio.openConnection();
            pt = uc.getInputStream();
            if (pt!=null) {
                r = conviertesString(pt);
            }
        } catch (Exception e) {
            r = e.getMessage();
        }
        return r;
    }
    public static String conviertesString(InputStream bloque) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(bloque));
        String linea;
        String res = "";
        while (  (linea = br.readLine())!= null) {
            res = res + linea;
        }
        br.close();
        return res;
    }
}

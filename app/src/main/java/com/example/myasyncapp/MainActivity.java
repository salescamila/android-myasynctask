package com.example.myasyncapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import util.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ProgressBar progressBarLoading;
    TextView tvTextExibido;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTextExibido = findViewById(R.id.tv_texto_exibido);
        progressBarLoading = findViewById(R.id.pb_loading);
        listView = findViewById(R.id.lvClinicas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.main, m);
        return super.onCreateOptionsMenu(m);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_web_service:
                callWebService();
                break;
            case R.id.menu_clear:
                clearText();
                break;
            case R.id.menu_evo:
                callWebServiceEvo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void callWebServiceEvo(){
        URL url = NetworkUtil.buildUrlCredenciadasEvo();
        MinhaAsyncTask task = new MinhaAsyncTask();
        task.execute(url);
    }

    public void callWebService(){
        Log.d(TAG, "method callWebService");
        URL url = NetworkUtil.buildUrl("stf");
        MinhaAsyncTask task = new MinhaAsyncTask();
        task.execute(url);
    }

    public void mostrarLoading(){
        tvTextExibido.setVisibility(View.GONE);
        progressBarLoading.setVisibility(View.VISIBLE);
    }

    public void esconderLoading(){
        tvTextExibido.setVisibility(View.GONE);
        progressBarLoading.setVisibility(View.GONE);
    }

    public void clearText(){
        Log.d(TAG, "method clearText");
        tvTextExibido.setText("");
    }

    public ListView listaClinicas (List<Clinicas> clinica) {
        MyAdapter adapter = new MyAdapter(clinica, this);
        listView.setAdapter(adapter);
        return listView;
    }


    class MinhaAsyncTask extends AsyncTask<URL, Void, List<Clinicas>> {

        @Override
        protected void onPreExecute() {
            mostrarLoading();
            super.onPreExecute();
        }

        @Override
        protected List<Clinicas> doInBackground(URL... urls) {
            Object json="";
            URL url = urls[0];
            Log.d(TAG, "URL utilizada: " + url.toString());
            try {
                json = NetworkUtil.getResponseFromHttpUrl(url);
                Log.d(TAG, "AsyncTask retornou: " + json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TypeToken<List<Clinicas>> token = new TypeToken<List<Clinicas>>() {};
            List<Clinicas> clinicas = new Gson().fromJson(json.toString(), token.getType());
            return clinicas;
        }

        @Override
        protected void onPostExecute(List<Clinicas> clinicas){
            esconderLoading();
            //tvTextExibido.setText(clinicas.get(0).razaoToString());

            /*
            for (int i = 0; i < clinicas.size(); i++) {
                tvTextExibido.setText(tvTextExibido.getText() + clinicas.get(i).razaoToString());
            }
            */

            if (clinicas == null) {
                tvTextExibido.setText("Nada encontrado.");
            } else {
                listaClinicas(clinicas);
            }

        }

    }
}

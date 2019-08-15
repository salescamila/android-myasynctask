package com.example.myasyncapp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myasyncapp.Clinicas;
import com.example.myasyncapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    List<Clinicas> clinicasList;
    Activity act;

    public MyAdapter(List<Clinicas> clinicas, Activity act) {
        this.clinicasList = clinicas;
        this.act = act;
    }

    @Override
    public int getCount() {
        return clinicasList.size();
    }

    @Override
    public Object getItem(int position) {
        return clinicasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.clinicas_layout, parent, false);
        Clinicas clinica = (Clinicas) getItem(position);
        TextView nomeDaClinica = view.findViewById(R.id.tv_name_clinica);
        nomeDaClinica.setText(clinica.getNome_fantasia());

        return view;
    }
}

package com.example.dokterhewan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dokterhewan.R;
import com.example.dokterhewan.model.ModelDokter;
import com.example.dokterhewan.model.ModelDokwan;

import java.util.List;

public class AdapterDokter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelDokter> item;

    public AdapterDokter(Activity activity, List<ModelDokter> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_dokter, null);


        TextView namaDokwan = (TextView) convertView.findViewById(R.id.txtNamaDokter);
        TextView notelp      = (TextView) convertView.findViewById(R.id.txtNoTelp);
//        TextView izin      = (TextView) convertView.findViewById(R.id.txtIzin);
        namaDokwan.setText(item.get(position).getNamaDokter());
        notelp.setText(item.get(position).getNomorTelp());
//        izin.setText(item.get(position).getNoIzin());

        return convertView;
    }
}
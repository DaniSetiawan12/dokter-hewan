package com.example.dokterhewan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dokterhewan.R;
import com.example.dokterhewan.model.ModelDokwan;
import com.example.dokterhewan.model.ModelPerawatan;
import com.example.dokterhewan.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterJenisHewan extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelPerawatan> item;

    public AdapterJenisHewan(Activity activity, List<ModelPerawatan> item) {
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
            convertView = inflater.inflate(R.layout.content_jenis_perawatan, null);


        TextView nama = (TextView) convertView.findViewById(R.id.txtNama);


        nama.setText(item.get(position).getNama());

        return convertView;
    }
}

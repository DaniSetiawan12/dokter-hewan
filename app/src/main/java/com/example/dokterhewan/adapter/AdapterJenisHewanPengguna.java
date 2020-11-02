package com.example.dokterhewan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dokterhewan.R;
import com.example.dokterhewan.model.ModelPerawatan;

import java.util.List;

public class AdapterJenisHewanPengguna extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelPerawatan> item;

    public AdapterJenisHewanPengguna(Activity activity, List<ModelPerawatan> item) {
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
        LinearLayout linearEditHapus = (LinearLayout) convertView.findViewById(R.id.linearEditHapus);
        linearEditHapus.setVisibility(View.GONE);


        nama.setText(item.get(position).getNama());

        return convertView;
    }
}

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
import com.example.dokterhewan.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDokwan extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelDokwan> item;

    public AdapterDokwan(Activity activity, List<ModelDokwan> item) {
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
            convertView = inflater.inflate(R.layout.content_dokwan_admin, null);


        TextView namaDokwan = (TextView) convertView.findViewById(R.id.txtNamaKlinik);
        TextView notelp      = (TextView) convertView.findViewById(R.id.txtNoTelp);
        TextView alamat      = (TextView) convertView.findViewById(R.id.txtAlamat);
        TextView email      = (TextView) convertView.findViewById(R.id.txtEmail);
        ImageView gambar     = (ImageView) convertView.findViewById(R.id.gambarPetshop);


        namaDokwan.setText(item.get(position).getNamaDokwan());
        notelp.setText(item.get(position).getNotelp());
        alamat.setText(item.get(position).getAlamat());
        email.setText(item.get(position).getEmail());

        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);

        return convertView;
    }
}
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

public class AdapterFav extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelDokwan> item;

    public AdapterFav(Activity activity, List<ModelDokwan> item) {
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
            convertView = inflater.inflate(R.layout.content_fav, null);


        TextView namaDokwan = (TextView) convertView.findViewById(R.id.txtNamaKlinik);
        TextView txtKm = (TextView) convertView.findViewById(R.id.txtKm);
        TextView txtDuration = (TextView) convertView.findViewById(R.id.txtDuration);
        TextView notelp      = (TextView) convertView.findViewById(R.id.txtNoTelp);
        TextView alamat      = (TextView) convertView.findViewById(R.id.txtAlamat);
        TextView email      = (TextView) convertView.findViewById(R.id.txtEmail);
        ImageView gambar     = (ImageView) convertView.findViewById(R.id.gambarPetshop);


        namaDokwan.setText(item.get(position).getNamaDokwan());
        notelp.setText(item.get(position).getNotelp());
        alamat.setText(item.get(position).getAlamat());
        email.setText(item.get(position).getEmail());
        txtKm.setText(item.get(position).getJarak());
        txtDuration.setText(item.get(position).getDuration());


        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);

        return convertView;
    }
}

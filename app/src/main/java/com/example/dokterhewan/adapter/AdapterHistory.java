package com.example.dokterhewan.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dokterhewan.R;
import com.example.dokterhewan.admin.HomeAdmin;
import com.example.dokterhewan.model.ModelDokwan;
import com.example.dokterhewan.pengguna.MainActivity;
import com.example.dokterhewan.server.BaseURL;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterHistory extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelDokwan> item;
    private RequestQueue mRequestQueue;

    public AdapterHistory(Activity activity, List<ModelDokwan> item, RequestQueue mRequestQueue) {
        this.activity = activity;
        this.item = item;
        this.mRequestQueue = mRequestQueue;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_history, null);


        TextView namaDokwan = (TextView) convertView.findViewById(R.id.txtNamaKlinik);
        TextView txtKm = (TextView) convertView.findViewById(R.id.txtKm);
        TextView txtDuration = (TextView) convertView.findViewById(R.id.txtDuration);
        TextView notelp      = (TextView) convertView.findViewById(R.id.txtNoTelp);
        TextView alamat      = (TextView) convertView.findViewById(R.id.txtAlamat);
        TextView email      = (TextView) convertView.findViewById(R.id.txtEmail);
        ImageView gambar     = (ImageView) convertView.findViewById(R.id.gambarPetshop);
        LinearLayout hapusHistory     = (LinearLayout) convertView.findViewById(R.id.hapusHistory);


        namaDokwan.setText(item.get(position).getNamaDokwan());
        notelp.setText(item.get(position).getNotelp());
        alamat.setText(item.get(position).getAlamat());
        email.setText(item.get(position).getEmail());
        txtKm.setText(item.get(position).getJarak());
        txtDuration.setText(item.get(position).getDuration());

        hapusHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage("Hapus History ? ");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                hapus(newsList.get(position).get_id());
                                try {
                                    JSONObject jsonObj1 = null;
                                    jsonObj1 = new JSONObject();
                                    jsonObj1.put("history", false);
                                    hapusHistori(jsonObj1, item.get(position).get_id());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                builder1.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);

        return convertView;
    }

    public void hapusHistori(JSONObject datas, String id){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.udpdateData+ id, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(activity, "Berhasil", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(activity, MainActivity.class);
                                activity.startActivity(i);
                                activity.finish();
                            }else {
                                Toast.makeText(activity, strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        mRequestQueue.add(req);
    }
}
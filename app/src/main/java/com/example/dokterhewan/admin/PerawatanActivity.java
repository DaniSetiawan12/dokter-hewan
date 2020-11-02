package com.example.dokterhewan.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dokterhewan.R;
import com.example.dokterhewan.adapter.AdapterJenisHewan;
import com.example.dokterhewan.model.ModelPerawatan;
import com.example.dokterhewan.server.BaseURL;
import com.vistrav.pop.Pop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PerawatanActivity extends AppCompatActivity {

    Intent i;

    String namaTempat, _id, alamat, nomorTelp, dokters, lat, longi, email, dataUser, gambar, idUser, perawatan, jenisHewan;
    ListView list;
    ArrayList<ModelPerawatan> newsList = new ArrayList<ModelPerawatan>();

    AdapterJenisHewan adapter;
    ImageView tambahData;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perawatan2);

        getSupportActionBar().hide();
        mRequestQueue = Volley.newRequestQueue(this);

        i = getIntent();
        namaTempat = i.getStringExtra("namaTempat");
        _id = i.getStringExtra("_id");
        alamat = i.getStringExtra("alamat");
        nomorTelp = i.getStringExtra("nomorTelp");
        dokters = i.getStringExtra("dokters");
        lat = i.getStringExtra("latitude");
        longi = i.getStringExtra("longitude");
        gambar = i.getStringExtra("gambar");
        email = i.getStringExtra("email");
        perawatan = i.getStringExtra("perawatan");
        jenisHewan = i.getStringExtra("jenisHewan");
        dataUser = i.getStringExtra("dataUser");

        list = (ListView) findViewById(R.id.array_list);
        tambahData = (ImageView) findViewById(R.id.tambahData);
        newsList.clear();
        adapter = new AdapterJenisHewan(PerawatanActivity.this, newsList);
        list.setAdapter(adapter);
        perawatan();
        tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pop.on(PerawatanActivity.this)
                        .with()
                        .title("Tambah Perawatan")
                        .cancelable(false)
                        .layout(R.layout.edit_hapus_)
                        .when(new Pop.Nah() { // ignore if dont need negative button
                            @Override
                            public void clicked(DialogInterface dialog, View view) {
                            }
                        })
                        .show(new Pop.View() { // assign value to view element
                            @Override
                            public void prepare(View view) {
                                TextView txtJudul = (TextView) view.findViewById(R.id.txtJudul);
                                txtJudul.setText("Tambah Perawatan");
                                final EditText edtNnama = (EditText) view.findViewById(R.id.edtNama);
                                Button btnEdit    = (Button) view.findViewById(R.id.btnEdit);
                                Button btnHapus    = (Button) view.findViewById(R.id.btnHapus);
                                btnHapus.setVisibility(View.GONE);
                                btnEdit.setText("Tambah Data");
                                btnEdit.setBackgroundResource(R.drawable.rounded_hijau);

                                btnEdit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String strNama = edtNnama.getText().toString();

                                        try {
                                            JSONObject jsonObj1=null;
                                            JSONArray array=new JSONArray();
                                            jsonObj1=new JSONObject();
                                            array.put(new JSONObject().put("nama", strNama));
                                            jsonObj1.put("perawatan", array);

                                            Log.d("Data = ", jsonObj1.toString());
                                            inputJasa(jsonObj1);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });
            }
        });
    }

    public void perawatan() {
        try {
            JSONArray arrayJenisHewan = new JSONArray(perawatan);
            for (int i = 0; i < arrayJenisHewan.length(); i++){
                JSONObject objJenisHewan = arrayJenisHewan.getJSONObject(i);
                ModelPerawatan model = new ModelPerawatan();
                String nama = objJenisHewan.getString("nama");
                String _id = objJenisHewan.getString("_id");
                model.setNama(nama);
                model.set_id(_id);
                newsList.add(model);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Pop.on(PerawatanActivity.this)
                        .with()
                        .title("Edit / Hapus Perawatan")
                        .cancelable(false)
                        .layout(R.layout.edit_hapus_)
                        .when(new Pop.Nah() { // ignore if dont need negative button
                            @Override
                            public void clicked(DialogInterface dialog, View view) {
                            }
                        })
                        .show(new Pop.View() { // assign value to view element
                            @Override
                            public void prepare(View view) {
                                TextView txtJudul = (TextView) view.findViewById(R.id.txtJudul);
                                txtJudul.setText("Edit / Hapus Perawatan");
                                final EditText edtNnama = (EditText) view.findViewById(R.id.edtNama);
                                Button btnEdit    = (Button) view.findViewById(R.id.btnEdit);
                                Button btnHapus    = (Button) view.findViewById(R.id.btnHapus);
                                edtNnama.setText(newsList.get(position).getNama());

                                btnEdit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String strNama = edtNnama.getText().toString();

                                        try {
                                            JSONObject jsonObj1=null;
                                            JSONArray array=new JSONArray();
                                            jsonObj1=new JSONObject();
                                            array.put(new JSONObject()
                                                    .put("nama", strNama));
                                            jsonObj1.put("perawatan", array);
                                            ubahJasa(jsonObj1, newsList.get(position).get_id());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                btnHapus.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        hapusJasa(newsList.get(position).get_id());
                                    }
                                });

                            }
                        });
            }
        });
    }

    public void ubahJasa(JSONObject datas, String id){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.ubahPerawatanHewan+ _id +"/" +id, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(PerawatanActivity.this, DataDokwanAdmin.class);
                                i.putExtra("dataUser", dataUser);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
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

    public void hapusJasa(String id){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, BaseURL.hapusDataPerawatan+ _id +"/" +id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(PerawatanActivity.this, DataDokwanAdmin.class);
                                i.putExtra("dataUser", dataUser);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
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

    public void inputJasa(JSONObject datas){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.updateDokwan+ _id, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Intent i = new Intent(PerawatanActivity.this, DataDokwanAdmin.class);
                                i.putExtra("dataUser", dataUser);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
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


    @Override
    public void onBackPressed(){
        Intent a = new Intent(PerawatanActivity.this, DetailDokwan.class);
        a.putExtra("namaTempat", namaTempat);
        a.putExtra("_id", _id);
        a.putExtra("alamat", alamat);
        a.putExtra("nomorTelp", nomorTelp);
        a.putExtra("dokters", dokters);
        a.putExtra("latitude", lat);
        a.putExtra("longitude", longi);
        a.putExtra("email", email);
        a.putExtra("gambar", gambar);
        a.putExtra("jenisHewan", jenisHewan);
        a.putExtra("perawatan", perawatan);
        a.putExtra("dataUser", dataUser);
        startActivity(a);
        finish();
    }
}
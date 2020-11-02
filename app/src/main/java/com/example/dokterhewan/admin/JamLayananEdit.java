package com.example.dokterhewan.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dokterhewan.R;
import com.example.dokterhewan.server.BaseURL;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JamLayananEdit extends AppCompatActivity {

    Intent i;

    String namaTempat, _id, alamat, nomorTelp, dokters, lat, longi, email, dataUser, gambar, idUser, perawatan, jenisHewan;

    EditText edtJamSenin, edtJamSelasa,edtJamRabu, edtJamKamis, edtJamJumat, edtJamSabtu, edtJamMinggu;
    FloatingActionButton fabButtonSimpanJamSenin, fabButtonSimpanJamSelasa, fabButtonSimpanJamRabu, fabButtonSimpanJamKamis,
            fabButtonSimpanJamJumat, fabButtonSimpanJamSabtu, fabButtonSimpanJamMinggu;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_layanan_edit);
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

        edtJamSenin = (EditText) findViewById(R.id.edtJamSenin);
        edtJamSelasa = (EditText) findViewById(R.id.edtJamSelasa);
        edtJamRabu = (EditText) findViewById(R.id.edtJamRabu);
        edtJamKamis = (EditText) findViewById(R.id.edtJamKamis);
        edtJamJumat = (EditText) findViewById(R.id.edtJamJumat);
        edtJamSabtu = (EditText) findViewById(R.id.edtJamSabtu);
        edtJamMinggu = (EditText) findViewById(R.id.edtJamMinggu);

        fabButtonSimpanJamSenin = (FloatingActionButton) findViewById(R.id.fabButtonSimpanJamSenin);
        fabButtonSimpanJamSelasa = (FloatingActionButton) findViewById(R.id.fabButtonJamSelasa);
        fabButtonSimpanJamRabu = (FloatingActionButton) findViewById(R.id.fabButtonJamRabu);
        fabButtonSimpanJamKamis = (FloatingActionButton) findViewById(R.id.fabButtonJamKamis);
        fabButtonSimpanJamJumat = (FloatingActionButton) findViewById(R.id.fabButtonJamJumat);
        fabButtonSimpanJamSabtu = (FloatingActionButton) findViewById(R.id.fabButtonJamSabtu);
        fabButtonSimpanJamMinggu = (FloatingActionButton) findViewById(R.id.fabButtonJamMinggu);

        JSONArray arrayDokter = null;
        JSONArray jamBuka = null;

        try {
            arrayDokter = new JSONArray(dokters);
            for (int i = 0; i < arrayDokter.length(); i++) {
                JSONObject objDokter = arrayDokter.getJSONObject(i);

                String jamLayanan = objDokter.getString("jadwalLayanan");
                jamBuka = new JSONArray(jamLayanan);

                for (int j = 0; j < jamBuka.length(); j++) {
                    JSONObject objJamBuka = jamBuka.getJSONObject(j);
                    if(objJamBuka.getString("hari").equals("Senin")){
                        edtJamSenin.setText(objJamBuka.getString("jam"));
                    }else if(objJamBuka.getString("hari").equals("Selasa")){
                        edtJamSelasa.setText(objJamBuka.getString("jam"));
                    }else if(objJamBuka.getString("hari").equals("Rabu")){
                        edtJamRabu.setText(objJamBuka.getString("jam"));
                    }else if(objJamBuka.getString("hari").equals("Kamis")){
                        edtJamKamis.setText(objJamBuka.getString("jam"));
                    }else if(objJamBuka.getString("hari").equals("Jum'at")){
                        edtJamJumat.setText(objJamBuka.getString("jam"));
                    }else if(objJamBuka.getString("hari").equals("Sabtu")){
                        edtJamSabtu.setText(objJamBuka.getString("jam"));
                    }else if(objJamBuka.getString("hari").equals("Minggu")){
                        edtJamMinggu.setText(objJamBuka.getString("jam"));
                    }else {
                        Toast.makeText(getApplicationContext(), "Tidak ada data", Toast.LENGTH_LONG).show();
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fabButtonSimpanJamSenin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1=new JSONObject();
                    jsonObj1.put("jam", edtJamSenin.getText().toString());
                    ubahJamBuka(jsonObj1, "Senin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fabButtonSimpanJamSelasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1=new JSONObject();
                    jsonObj1.put("jam", edtJamSelasa.getText().toString());
                    ubahJamBuka(jsonObj1, "Selasa");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fabButtonSimpanJamRabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1=new JSONObject();
                    jsonObj1.put("jam", edtJamRabu.getText().toString());
                    ubahJamBuka(jsonObj1, "Rabu");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fabButtonSimpanJamKamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1=new JSONObject();
                    jsonObj1.put("jam", edtJamKamis.getText().toString());
                    ubahJamBuka(jsonObj1, "Kamis");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fabButtonSimpanJamJumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1=new JSONObject();
                    jsonObj1.put("jam", edtJamJumat.getText().toString());
                    ubahJamBuka(jsonObj1, "Jum'at");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fabButtonSimpanJamSabtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1=new JSONObject();
                    jsonObj1.put("jam", edtJamSabtu.getText().toString());
                    ubahJamBuka(jsonObj1, "Sabtu");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fabButtonSimpanJamMinggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1=new JSONObject();
                    jsonObj1.put("jam", edtJamMinggu.getText().toString());
                    ubahJamBuka(jsonObj1, "Minggu");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(JamLayananEdit.this, DetailDokwan.class);
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

    public void ubahJamBuka(JSONObject datas, String hari){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.updatePetDataShhop+ _id +"/" +hari, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
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
}
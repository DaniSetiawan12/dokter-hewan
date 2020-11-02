package com.example.dokterhewan.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.dokterhewan.adapter.AdapterDokter;
import com.example.dokterhewan.adapter.AdapterJenisHewan;
import com.example.dokterhewan.adapter.AdapterPerawatan;
import com.example.dokterhewan.model.ModelDokter;
import com.example.dokterhewan.model.ModelPerawatan;
import com.example.dokterhewan.pengguna.Detail;
import com.example.dokterhewan.server.BaseURL;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailDokwan extends AppCompatActivity {


    Intent i;

    String namaTempat, _id, alamat, nomorTelp, dokters, lat, longi, email, dataUser, gambar, idUser, perawatan, jenisHewan;

    TextView txtNama, txtAlamat, txtNoTelp, txtEmail, txtJudul;
    CardView myCard;
    LinearLayout lihatDokter, linearKembali, linearPerawaran, linearJenisHewan;

    AdapterDokter adapter;
    AdapterJenisHewan adapterJenisHewan;
    AdapterPerawatan adapterPerawatan;
    ListView list, listPerawatan, listJenisHewan;

    ArrayList<ModelDokter> newsList = new ArrayList<ModelDokter>();
    ArrayList<ModelPerawatan> perawatanModel = new ArrayList<ModelPerawatan>();
    ArrayList<ModelPerawatan> jenisHewanModel = new ArrayList<ModelPerawatan>();

    ArrayList gam = new ArrayList();
    CarouselView carouselView;

    FloatingActionButton btnEditNama, btnSimpanNama, fabButtonEditNoTelp, fabButtonSimpanNoTelp,
            fabButtonEditEmail, fabButtonSimpanEmail, fabAddGambar;
    EditText edtNama, edtNoTelp, edtEmail;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    JSONObject jObjData;

    LinearLayout hapusData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokwan);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

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


        Log.d("Perawatan = ", perawatan);

        try {
            jObjData = new JSONObject(dataUser);
            idUser = jObjData.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        txtNama = (TextView) findViewById(R.id.txtNamaKlinik);
        txtAlamat = (TextView) findViewById(R.id.txtAlamat);
        txtNoTelp = (TextView) findViewById(R.id.txtNoTelp);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtJudul = (TextView) findViewById(R.id.txtJudul);

        carouselView = (CarouselView) findViewById(R.id.carouselView);

        edtNama = (EditText) findViewById(R.id.edtNama);
        edtNoTelp = (EditText) findViewById(R.id.edtNoTelp);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        hapusData = (LinearLayout) findViewById(R.id.hapusData);

        txtNama.setText(namaTempat);
        txtAlamat.setText(alamat);
        txtNoTelp.setText(nomorTelp);
        txtEmail.setText(email);

        myCard = (CardView) findViewById(R.id.myCard);
        lihatDokter = (LinearLayout) findViewById(R.id.lihatDokter);
        linearKembali = (LinearLayout) findViewById(R.id.kembali);
        linearPerawaran = (LinearLayout) findViewById(R.id.lihatPerawatan);
        linearJenisHewan = (LinearLayout) findViewById(R.id.lihatJenisHewan);

        list = (ListView) findViewById(R.id.array_list);
        listJenisHewan = (ListView) findViewById(R.id.array_list_jenisHewan);
        listPerawatan = (ListView) findViewById(R.id.array_list_perawatan);

        btnEditNama = (FloatingActionButton) findViewById(R.id.fabButtonEditNama);
        fabButtonEditNoTelp = (FloatingActionButton) findViewById(R.id.fabButtonEditNoTelp);
        fabButtonEditEmail = (FloatingActionButton) findViewById(R.id.fabButtonEditEmail);
        btnSimpanNama = (FloatingActionButton) findViewById(R.id.fabButtonSimpanNama);
        fabButtonSimpanNoTelp = (FloatingActionButton) findViewById(R.id.fabButtonSimpanNoTelp);
        fabButtonSimpanEmail = (FloatingActionButton) findViewById(R.id.fabButtonSimpanEmail);

        newsList.clear();
        perawatanModel.clear();
        jenisHewanModel.clear();
        adapter = new AdapterDokter(DetailDokwan.this, newsList);
        adapterJenisHewan = new AdapterJenisHewan(DetailDokwan.this, jenisHewanModel);
        adapterPerawatan = new AdapterPerawatan(DetailDokwan.this, perawatanModel);
        list.setAdapter(adapter);
        listJenisHewan.setAdapter(adapterJenisHewan);
        listPerawatan.setAdapter(adapterPerawatan);


        fabAddGambar = (FloatingActionButton) findViewById(R.id.fabAddGambar);

        fabAddGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailDokwan.this, TambahGambar.class);
                i.putExtra("_id", _id);
                i.putExtra("dataUser", dataUser);
                startActivity(i);
                finish();
            }
        });

        btnEditNama.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                btnSimpanNama.setVisibility(View.VISIBLE);
                btnEditNama.setVisibility(View.GONE);
                edtNama.setFocusableInTouchMode(true);
                edtNama.requestFocus();
                edtNama.setVisibility(View.VISIBLE);
                txtNama.setVisibility(View.GONE);
                if(namaTempat.equals(namaTempat)){
                    edtNama.setText(namaTempat);
                }else {
                    edtNama.setText(edtNama.getText().toString());
                }

                if (edtNama.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });


        fabButtonEditNoTelp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabButtonSimpanNoTelp.setVisibility(View.VISIBLE);
                fabButtonEditNoTelp.setVisibility(View.GONE);
                edtNoTelp.setFocusableInTouchMode(true);
                edtNoTelp.requestFocus();
                edtNoTelp.setVisibility(View.VISIBLE);
                txtNoTelp.setVisibility(View.GONE);
                if(nomorTelp.equals(nomorTelp)){
                    edtNoTelp.setText(nomorTelp);
                }else {
                    edtNoTelp.setText(edtNoTelp.getText().toString());
                }

                if (edtNoTelp.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        fabButtonEditEmail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabButtonSimpanEmail.setVisibility(View.VISIBLE);
                fabButtonEditEmail.setVisibility(View.GONE);
                edtEmail.setFocusableInTouchMode(true);
                edtEmail.requestFocus();
                edtEmail.setVisibility(View.VISIBLE);
                txtEmail.setVisibility(View.GONE);
                if(email.equals(email)){
                    edtEmail.setText(email);
                }else {
                    edtEmail.setText(edtEmail.getText().toString());
                }

                if (edtEmail.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        btnSimpanNama.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                btnSimpanNama.setVisibility(View.GONE);
                btnEditNama.setVisibility(View.VISIBLE);
                edtNama.setVisibility(View.GONE);
                txtNama.setVisibility(View.VISIBLE);
                namaTempat = edtNama.getText().toString();
                txtNama.setText(namaTempat);
                String namaDokwan = edtNama.getText().toString();
                try {
                    JSONObject jsonObj1 = null;
                    jsonObj1 = new JSONObject();
                    jsonObj1.put("namaTempat", namaDokwan);
                    updateData(jsonObj1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        fabButtonSimpanNoTelp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabButtonSimpanNoTelp.setVisibility(View.GONE);
                fabButtonEditNoTelp.setVisibility(View.VISIBLE);
                edtNoTelp.setVisibility(View.GONE);
                txtNoTelp.setVisibility(View.VISIBLE);
                nomorTelp = edtNoTelp.getText().toString();
                txtNoTelp.setText(nomorTelp);
                String noTelp = edtNoTelp.getText().toString();
                try {
                    JSONObject jsonObj1 = null;
                    jsonObj1 = new JSONObject();
                    jsonObj1.put("nomorTelp", noTelp);
                    updateData(jsonObj1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        fabButtonSimpanEmail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabButtonSimpanEmail.setVisibility(View.GONE);
                fabButtonEditEmail.setVisibility(View.VISIBLE);
                edtEmail.setVisibility(View.GONE);
                txtEmail.setVisibility(View.VISIBLE);
                email = edtEmail.getText().toString();
                txtEmail.setText(email);
                String email = edtEmail.getText().toString();
                try {
                    JSONObject jsonObj1 = null;
                    jsonObj1 = new JSONObject();
                    jsonObj1.put("email", email);
                    updateDataUser(jsonObj1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        lihatDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(DetailDokwan.this, JamLayananEdit.class);
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
        });

        linearJenisHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listJenisHewan.setVisibility(View.VISIBLE);
//                linearKembali.setVisibility(View.VISIBLE);
//                myCard.setVisibility(View.GONE);
//                jenisHewan();
                Intent a = new Intent(DetailDokwan.this, JenisHewanActivity.class);
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
        });

        linearPerawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listPerawatan.setVisibility(View.VISIBLE);
//                linearKembali.setVisibility(View.VISIBLE);
//                myCard.setVisibility(View.GONE);
//                perawatan();

                Intent a = new Intent(DetailDokwan.this, PerawatanActivity.class);
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
        });

        linearKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setVisibility(View.GONE);
                listPerawatan.setVisibility(View.GONE);
                listJenisHewan.setVisibility(View.GONE);
                linearKembali.setVisibility(View.GONE);
                myCard.setVisibility(View.VISIBLE);
                newsList.clear();
                jenisHewanModel.clear();
                perawatanModel.clear();
            }
        });

        try {
            JSONArray arrayGambar = new JSONArray(gambar);
            for (int i = 0; i < arrayGambar.length(); i++) {
                gam.add(BaseURL.baseUrl + "gambar/" + arrayGambar.get(i).toString());
            }

            carouselView.setPageCount(gam.size());
            carouselView.setImageListener(imageListener);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
//                Intent a = new Intent(DataDokwanAdmin.this, DetailDokwan.class);
//                a.putExtra("_id", newsList.get(position).get_id());
//                a.putExtra("dataUser", dataUser);
//                startActivity(a);
            }
        });


        hapusData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailDokwan.this);
                builder1.setMessage("Ingin Menghapus " + namaTempat + " ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hapusData();
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

    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso.get().load(String.valueOf(gam.get(position))).fit().centerCrop().into(imageView);
            //imageView.setImageResource(sampleImages[position]);
        }
    };

    View.OnClickListener pauseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            carouselView.pauseCarousel();
        }
    };

    @Override
    public void onBackPressed(){
        Intent i = new Intent(DetailDokwan.this, DataDokwanAdmin.class);
        i.putExtra("dataUser", dataUser);
        startActivity(i);
        finish();
    }

    public void dokter() {
        try {
            JSONArray arrayDokter = new JSONArray(dokters);
            for (int i = 0; i < arrayDokter.length(); i++){
                JSONObject objProduk = arrayDokter.getJSONObject(i);
                ModelDokter modelDokter = new ModelDokter();
                String strNamaDokter = objProduk.getString("nama_dokter");
                String noTelp = objProduk.getString("nomorTelp");
                String noIzin = objProduk.getString("noIzinPrak");
                String _id = objProduk.getString("_id");
                String jadwalLayanan = objProduk.getString("jadwalLayanan");
                modelDokter.setNamaDokter(strNamaDokter);
                modelDokter.setNomorTelp(noTelp);
                modelDokter.setNoIzin(noIzin);
                modelDokter.set_id(_id);
                modelDokter.setJadwalLayanan(jadwalLayanan);
                txtJudul.setText("Data Dokter");

                newsList.add(modelDokter);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void jenisHewan() {
        try {
            JSONArray arrayJenisHewan = new JSONArray(jenisHewan);
            for (int i = 0; i < arrayJenisHewan.length(); i++){
                JSONObject objJenisHewan = arrayJenisHewan.getJSONObject(i);
                ModelPerawatan model = new ModelPerawatan();
                String nama = objJenisHewan.getString("nama");
                String _id = objJenisHewan.getString("_id");
                model.setNama(nama);
                model.set_id(_id);
                txtJudul.setText("Jenis Hewan");
                jenisHewanModel.add(model);
            }
            adapterJenisHewan.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                txtJudul.setText("Perawatan");
                perawatanModel.add(model);
            }
            adapterPerawatan.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void updateData(JSONObject datas){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.udpdateData+ _id, datas,
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

    public void updateDataUser(JSONObject datas){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.updateUser+ idUser, datas,
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

    public void hapusData(){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, BaseURL.hapusData+ _id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent a = new Intent(DetailDokwan.this, HomeAdmin.class);
                                startActivity(a);
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
                hideDialog();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        mRequestQueue.add(req);
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}
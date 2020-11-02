package com.example.dokterhewan.pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.example.dokterhewan.adapter.AdapterDokterPengguna;
import com.example.dokterhewan.adapter.AdapterJenisHewan;
import com.example.dokterhewan.adapter.AdapterJenisHewanPengguna;
import com.example.dokterhewan.adapter.AdapterPerawatan;
import com.example.dokterhewan.adapter.AdapterPerawatanPengguna;
import com.example.dokterhewan.admin.DataDokwanAdmin;
import com.example.dokterhewan.admin.DetailDokwan;
import com.example.dokterhewan.admin.JamLayananEdit;
import com.example.dokterhewan.model.ModelDokter;
import com.example.dokterhewan.model.ModelPerawatan;
import com.example.dokterhewan.server.BaseURL;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {

    Intent i;
    String namaTempat, _id, alamat, nomorTelp, dokters, lat, longi, email, gambar, perawatan, jenisHewan, fav;

    ImageView startTrue, startFalse;

    TextView txtNama, txtAlamat, txtNoTelp, txtEmail, txtJudul;
    CardView myCard;
    LinearLayout lihatDokter, linearKembali, linearPerawaran, linearJenisHewan, goRoutes;
    ScrollView scrollView;

    AdapterDokterPengguna adapter;
    AdapterJenisHewanPengguna adapterJenisHewan;
    AdapterPerawatanPengguna adapterPerawatan;
    ListView list, listPerawatan, listJenisHewan;

    ArrayList<ModelDokter> newsList = new ArrayList<ModelDokter>();
    ArrayList<ModelPerawatan> perawatanModel = new ArrayList<ModelPerawatan>();
    ArrayList<ModelPerawatan> jenisHewanModel = new ArrayList<ModelPerawatan>();

    ArrayList gam = new ArrayList();
    CarouselView carouselView;

    private RequestQueue mRequestQueue;

    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
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
        fav = i.getStringExtra("fav");

        startFalse = (ImageView) findViewById(R.id.startFalse);
        startTrue = (ImageView) findViewById(R.id.startTrue);

        txtNama = (TextView) findViewById(R.id.txtNamaKlinik);
        txtAlamat = (TextView) findViewById(R.id.txtAlamat);
        txtNoTelp = (TextView) findViewById(R.id.txtNoTelp);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtJudul = (TextView) findViewById(R.id.txtJudul);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        carouselView = (CarouselView) findViewById(R.id.carouselView);

        txtNama.setText(namaTempat);
        txtAlamat.setText(alamat);
        txtNoTelp.setText(nomorTelp);
        txtEmail.setText(email);

        myCard = (CardView) findViewById(R.id.myCard);
        lihatDokter = (LinearLayout) findViewById(R.id.lihatDokter);
        linearKembali = (LinearLayout) findViewById(R.id.kembali);
        linearPerawaran = (LinearLayout) findViewById(R.id.lihatPerawatan);
        linearJenisHewan = (LinearLayout) findViewById(R.id.lihatJenisHewan);
        goRoutes = (LinearLayout) findViewById(R.id.goRoutes);

        list = (ListView) findViewById(R.id.array_list);
        listJenisHewan = (ListView) findViewById(R.id.array_list_jenisHewan);
        listPerawatan = (ListView) findViewById(R.id.array_list_perawatan);

        if(fav.equals("true")){
            startTrue.setVisibility(View.VISIBLE);
            startFalse.setVisibility(View.GONE);
        }else {
            startTrue.setVisibility(View.GONE);
            startFalse.setVisibility(View.VISIBLE);
        }

        startFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1 = null;
                    jsonObj1 = new JSONObject();
                    jsonObj1.put("fav", true);
                    updateData(jsonObj1);
                    startTrue.setVisibility(View.VISIBLE);
                    startFalse.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObj1 = null;
                    jsonObj1 = new JSONObject();
                    jsonObj1.put("fav", false);
                    updateData(jsonObj1);
                    startTrue.setVisibility(View.GONE);
                    startFalse.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        newsList.clear();
        perawatanModel.clear();
        jenisHewanModel.clear();
        adapter = new AdapterDokterPengguna(Detail.this, newsList);
        adapterJenisHewan = new AdapterJenisHewanPengguna(Detail.this, jenisHewanModel);
        adapterPerawatan = new AdapterPerawatanPengguna(Detail.this, perawatanModel);
        list.setAdapter(adapter);
        listJenisHewan.setAdapter(adapterJenisHewan);
        listPerawatan.setAdapter(adapterPerawatan);

        lihatDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(Detail.this, JamLayanan.class);
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
                a.putExtra("fav", fav);
                startActivity(a);
                finish();
            }
        });

        linearJenisHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listJenisHewan.setVisibility(View.VISIBLE);
                linearKembali.setVisibility(View.VISIBLE);
                myCard.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                jenisHewan();
            }
        });

        linearPerawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPerawatan.setVisibility(View.VISIBLE);
                linearKembali.setVisibility(View.VISIBLE);
                myCard.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                perawatan();
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
                scrollView.setVisibility(View.VISIBLE);
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

        goRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmmIntentUri = Uri.parse("google.navigation:q=" + lat+","+longi);

                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                mapIntent.setPackage(goolgeMap);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                    try {
                        JSONObject jsonObj1 = null;
                        jsonObj1 = new JSONObject();
                        jsonObj1.put("history", true);
                        updateData(jsonObj1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Detail.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        txtNoTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:" + nomorTelp;
                intent.setData(Uri.parse(temp));
                startActivity(intent);
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
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
        Intent i = new Intent(Detail.this, DataDokwanPengguna.class);
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
                txtJudul.setText("Jenis Layanan Kesehatan");
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
                                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
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
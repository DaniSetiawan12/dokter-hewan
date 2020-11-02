package com.example.dokterhewan.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dokterhewan.R;
import com.example.dokterhewan.admin.DetailDokwan;
import com.example.dokterhewan.admin.JamLayananEdit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JamLayanan extends AppCompatActivity {

    Intent i;

    String namaTempat, _id, alamat, nomorTelp, dokters, lat, longi, email, dataUser, gambar, idUser, perawatan, jenisHewan, fav;

    EditText edtJamSenin, edtJamSelasa,edtJamRabu, edtJamKamis, edtJamJumat, edtJamSabtu, edtJamMinggu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_layanan);
        getSupportActionBar().hide();

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

        edtJamSenin = (EditText) findViewById(R.id.edtJamSenin);
        edtJamSelasa = (EditText) findViewById(R.id.edtJamSelasa);
        edtJamRabu = (EditText) findViewById(R.id.edtJamRabu);
        edtJamKamis = (EditText) findViewById(R.id.edtJamKamis);
        edtJamJumat = (EditText) findViewById(R.id.edtJamJumat);
        edtJamSabtu = (EditText) findViewById(R.id.edtJamSabtu);
        edtJamMinggu = (EditText) findViewById(R.id.edtJamMinggu);

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
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(JamLayanan.this, Detail.class);
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
}
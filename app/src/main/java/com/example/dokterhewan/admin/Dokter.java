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
import com.example.dokterhewan.adapter.AdapterDokter;
import com.example.dokterhewan.model.ModelDokter;
import com.example.dokterhewan.pengguna.DataDokwanPengguna;
import com.example.dokterhewan.pengguna.Detail;
import com.example.dokterhewan.server.BaseURL;
import com.vistrav.pop.Pop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dokter extends AppCompatActivity {

    Intent i;

    String namaTempat, _id, alamat, nomorTelp, dokters, lat, longi, email, dataUser, gambar, idUser, perawatan, jenisHewan;
    ListView list;
    ArrayList<ModelDokter> newsList = new ArrayList<ModelDokter>();

    AdapterDokter adapter;
    private RequestQueue mRequestQueue;

    ImageView tambahData;

    EditText edtJamSenin, edtJamSelasa,edtJamRabu, edtJamKamis, edtJamJumat, edtJamSabtu,
            edtJamMinggu, edtNamaDok, edtNotelpDok, edtNoIzin,
            edtRuangSenin, edtRuangSelasa, edtRuangRabu, edtRuangKamis, edtRuangJumat, edtRuangSabtu, edtRuangMinggu;

    Button btnSumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);

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
        adapter = new AdapterDokter(Dokter.this, newsList);
        list.setAdapter(adapter);
        dokter();

        tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pop.on(Dokter.this)
                        .with()
                        .title("Tambah Dokter")
                        .cancelable(false)
                        .layout(R.layout.activity_input_dokter)
                        .when(new Pop.Nah() { // ignore if dont need negative button
                            @Override
                            public void clicked(DialogInterface dialog, View view) {
                            }
                        })
                        .show(new Pop.View() { // assign value to view element
                            @Override
                            public void prepare(View view) {
                                edtJamSenin = (EditText) view.findViewById(R.id.edtJamSenin);
                                edtJamSelasa = (EditText) view.findViewById(R.id.edtJamSelasa);
                                edtJamRabu = (EditText) view.findViewById(R.id.edtJamRabu);
                                edtJamKamis = (EditText) view.findViewById(R.id.edtJamKamis);
                                edtJamJumat = (EditText) view.findViewById(R.id.edtJamJumat);
                                edtJamSabtu = (EditText) view.findViewById(R.id.edtJamSabtu);
                                edtJamMinggu = (EditText) view.findViewById(R.id.edtJamMinggu);

                                edtNamaDok = (EditText) view.findViewById(R.id.edtNamaDokter);
                                edtNotelpDok = (EditText) view.findViewById(R.id.edtNotelpDokter);
                                edtNoIzin = (EditText) view.findViewById(R.id.edtNoIzinPrak);

                                edtRuangSenin = (EditText) view.findViewById(R.id.edtRuanganSenin);
                                edtRuangSelasa = (EditText) view.findViewById(R.id.edtRuanganSelasa);
                                edtRuangRabu = (EditText) view.findViewById(R.id.edtRuanganRabu);
                                edtRuangKamis = (EditText) view.findViewById(R.id.edtRuanganKamis);
                                edtRuangJumat = (EditText) view.findViewById(R.id.edtRuanganJumat);
                                edtRuangSabtu = (EditText) view.findViewById(R.id.edtRuanganSabtu);
                                edtRuangMinggu = (EditText) view.findViewById(R.id.edtRuanganMinggu);

                                btnSumbit = (Button) view.findViewById(R.id.btnSubmit);
                                btnSumbit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String jamSenin = edtJamSenin.getText().toString();
                                        String jamSelasa = edtJamSelasa.getText().toString();
                                        String jamRabu = edtJamRabu.getText().toString();
                                        String jamKamis = edtJamKamis.getText().toString();
                                        String jamJumat = edtJamJumat.getText().toString();
                                        String jamSabtu = edtJamSabtu.getText().toString();
                                        String Minggu = edtJamMinggu.getText().toString();

                                        String ruangSenin = edtRuangSenin.getText().toString();
                                        String ruangSelasa = edtRuangSelasa.getText().toString();
                                        String ruangRabu = edtRuangRabu.getText().toString();
                                        String ruangKamis = edtRuangKamis.getText().toString();
                                        String ruangJumat = edtRuangJumat.getText().toString();
                                        String ruangSabtu = edtRuangSabtu.getText().toString();
                                        String ruangMinggu = edtRuangMinggu.getText().toString();

                                        String namaDok = edtNamaDok.getText().toString();
                                        String noTelp = edtNotelpDok.getText().toString();
                                        String noIzin = edtNoIzin.getText().toString();

                                        try {
                                            JSONObject jsonObj1=null;
                                            JSONArray array=new JSONArray();
                                            jsonObj1=new JSONObject();
                                            array
                                                    .put(new JSONObject().put("hari", "Senin").put("jam", jamSenin).put("ruang", ruangSenin))
                                                    .put(new JSONObject().put("hari", "Selasa").put("jam", jamSelasa).put("ruang", ruangSelasa))
                                                    .put(new JSONObject().put("hari", "Rabu").put("jam", jamRabu).put("ruang", ruangRabu))
                                                    .put(new JSONObject().put("hari", "Kamis").put("jam", jamKamis).put("ruang", ruangKamis))
                                                    .put(new JSONObject().put("hari", "Jum'at").put("jam", jamJumat).put("ruang", ruangJumat))
                                                    .put(new JSONObject().put("hari", "Sabtu").put("jam", jamSabtu).put("ruang", ruangSabtu))
                                                    .put(new JSONObject().put("hari", "Minggu").put("jam", Minggu).put("ruang", ruangMinggu));
                                            jsonObj1.put("jadwalLayanan", array);
                                            jsonObj1.put("nama_dokter", namaDok);
                                            jsonObj1.put("nomorTelp", noTelp);
                                            jsonObj1.put("noIzinPrak", noIzin);
                                            jsonObj1.put("idDokwan", _id);

                                            Log.d("Data = ", jsonObj1.toString());
                                            inputDokter(jsonObj1);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Pop.on(Dokter.this)
                        .with()
                        .title("Edit / Hapus Dokter")
                        .cancelable(false)
                        .layout(R.layout.edit_hapus)
                        .when(new Pop.Nah() { // ignore if dont need negative button
                            @Override
                            public void clicked(DialogInterface dialog, View view) {
                            }
                        })
                        .show(new Pop.View() { // assign value to view element
                            @Override
                            public void prepare(View view) {
                                TextView txtJudul = (TextView) view.findViewById(R.id.txtJudul);
                                txtJudul.setText("Edit / Hapus Dokter");
                                final EditText edtNnama = (EditText) view.findViewById(R.id.edtNamaDokter);
                                final EditText edtNoTelp = (EditText) view.findViewById(R.id.edtNoTelp);
                                Button btnEdit    = (Button) view.findViewById(R.id.btnEdit);
                                Button btnHapus    = (Button) view.findViewById(R.id.btnHapus);
                                edtNnama.setText(newsList.get(position).getNamaDokter());
                                edtNoTelp.setText(newsList.get(position).getNomorTelp());

                                btnEdit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String strNama = edtNnama.getText().toString();
                                        String strNotelp = edtNoTelp.getText().toString();

                                        try {
                                            JSONObject jsonObj1=null;
                                            jsonObj1=new JSONObject();
                                            jsonObj1.put("nama_dokter", strNama).put("nomorTelp", strNotelp);
                                            Log.d("Data = ", jsonObj1.toString());
                                            ubahDokter(jsonObj1, newsList.get(position).get_id());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                btnHapus.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        hapusData(newsList.get(position).get_id());
                                    }
                                });

                            }
                        });
            }
        });
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

                newsList.add(modelDokter);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void inputDokter(JSONObject datas){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.inputDokter, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Intent i = new Intent(Dokter.this, DataDokwanAdmin.class);
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

    public void ubahDokter(JSONObject datas, String id){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.updateDokter +id, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Dokter.this, DataDokwanAdmin.class);
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

    public void hapusData(String id){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, BaseURL.hapusDokter +id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Dokter.this, DataDokwanAdmin.class);
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
        Intent a = new Intent(Dokter.this, DetailDokwan.class);
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
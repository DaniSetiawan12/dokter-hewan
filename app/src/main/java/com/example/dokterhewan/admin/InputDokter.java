package com.example.dokterhewan.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InputDokter extends AppCompatActivity {


    String dataUser, idDokwan;
    Intent i;

    EditText edtJamSenin, edtJamSelasa,edtJamRabu, edtJamKamis, edtJamJumat, edtJamSabtu,
            edtJamMinggu, edtNamaDok, edtNotelpDok, edtNoIzin,
            edtRuangSenin, edtRuangSelasa, edtRuangRabu, edtRuangKamis, edtRuangJumat, edtRuangSabtu, edtRuangMinggu;

    Button btnSumbit;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_dokter);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtJamSenin = (EditText) findViewById(R.id.edtJamSenin);
        edtJamSelasa = (EditText) findViewById(R.id.edtJamSelasa);
        edtJamRabu = (EditText) findViewById(R.id.edtJamRabu);
        edtJamKamis = (EditText) findViewById(R.id.edtJamKamis);
        edtJamJumat = (EditText) findViewById(R.id.edtJamJumat);
        edtJamSabtu = (EditText) findViewById(R.id.edtJamSabtu);
        edtJamMinggu = (EditText) findViewById(R.id.edtJamMinggu);

        edtNamaDok = (EditText) findViewById(R.id.edtNamaDokter);
        edtNotelpDok = (EditText) findViewById(R.id.edtNotelpDokter);
        edtNoIzin = (EditText) findViewById(R.id.edtNoIzinPrak);

        edtRuangSenin = (EditText) findViewById(R.id.edtRuanganSenin);
        edtRuangSelasa = (EditText) findViewById(R.id.edtRuanganSelasa);
        edtRuangRabu = (EditText) findViewById(R.id.edtRuanganRabu);
        edtRuangKamis = (EditText) findViewById(R.id.edtRuanganKamis);
        edtRuangJumat = (EditText) findViewById(R.id.edtRuanganJumat);
        edtRuangSabtu = (EditText) findViewById(R.id.edtRuanganSabtu);
        edtRuangMinggu = (EditText) findViewById(R.id.edtRuanganMinggu);

        btnSumbit = (Button) findViewById(R.id.btnSubmit);


        i = getIntent();
        dataUser = i.getStringExtra("dataUser");
        idDokwan = i.getStringExtra("idDokwan");


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
                    jsonObj1.put("idDokwan", idDokwan);

                    Log.d("Data = ", jsonObj1.toString());
                    inputDokter(jsonObj1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void inputDokter(JSONObject datas){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.inputDokter, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            final String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(InputDokter.this, HomeAdmin.class);
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

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Data Harus DiLengkapi", Toast.LENGTH_LONG).show();
    }
}
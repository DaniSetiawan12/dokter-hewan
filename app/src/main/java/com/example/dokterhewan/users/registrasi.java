package com.example.dokterhewan.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

public class registrasi extends AppCompatActivity {

    Button btnLinklogin, btnRegistrasi;
    EditText edtNamaLengkap, edtEmail, edtNotelp, edtPasword;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnLinklogin = (Button) findViewById(R.id.btnLinklogin);
        btnRegistrasi = (Button) findViewById(R.id.btnRegistrasi);
        edtNamaLengkap = (EditText) findViewById(R.id.NamaLengkap);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtNotelp = (EditText) findViewById(R.id.Notelp);
        edtPasword = (EditText) findViewById(R.id.Katasandi);

        btnLinklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(registrasi.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namaLengkap = edtNamaLengkap.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPasword.getText().toString();
                String noTelp = edtNotelp.getText().toString();

                try {
                    JSONObject jsonObj1=null;
                    jsonObj1=new JSONObject();
                    jsonObj1.put("namaTempat", namaLengkap);
                    jsonObj1.put("email", email);
                    jsonObj1.put("nomorTelp", noTelp);
                    jsonObj1.put("password", password);

                    Log.d("Data = ", jsonObj1.toString());
                    registrasi(jsonObj1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(registrasi.this, Login.class);
        startActivity(i);
        finish();
    }

    public void registrasi(JSONObject datas){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.registrasiUser, datas,
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
                                Intent i = new Intent(registrasi.this, Login.class);
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
}

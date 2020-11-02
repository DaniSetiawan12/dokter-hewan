package com.example.dokterhewan.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dokterhewan.R;
import com.example.dokterhewan.adapter.AdapterDokwan;
import com.example.dokterhewan.adapter.AdapterDokwanPengguna;
import com.example.dokterhewan.admin.DataDokwanAdmin;
import com.example.dokterhewan.admin.DetailDokwan;
import com.example.dokterhewan.admin.HomeAdmin;
import com.example.dokterhewan.model.ModelDokwan;
import com.example.dokterhewan.server.BaseURL;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataDokwanPengguna extends AppCompatActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    ProgressDialog pDialog;

    AdapterDokwanPengguna adapter;
    ListView list;


    ArrayList<ModelDokwan> newsList = new ArrayList<ModelDokwan>();
    private RequestQueue mRequestQueue;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_dokwan_pengguna);

        getSupportActionBar().setTitle("Data Dokter Hewan");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        newsList.clear();
        adapter = new AdapterDokwanPengguna(DataDokwanPengguna.this, newsList);
        list.setAdapter(adapter);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); //

        cari();
    }

    public void getAllPet(JSONObject jsonObject) {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.getDataDokwan + "0/", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelDokwan dokWan = new ModelDokwan();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaTempat = jsonObject.getString("namaTempat");
                                    final String notelp = jsonObject.getString("nomorTelp");
                                    final String lat = jsonObject.getString("latitude");
                                    final String lon = jsonObject.getString("longitude");
                                    final String alamat = jsonObject.getString("alamat");
                                    final String dokter = jsonObject.getString("dokters");
                                    final String email = jsonObject.getString("email");
                                    final String perawatan = jsonObject.getString("perawatan");
                                    final String jenisHewan = jsonObject.getString("jenisHewan");
                                    final String jarak = jsonObject.getString("distance");
                                    final String duration = jsonObject.getString("duration");
                                    final String arrGambar = jsonObject.getString("gambar");
                                    final String fav = jsonObject.getString("fav");

                                    JSONArray arrayGambar = new JSONArray(arrGambar);
                                    String gambar = arrayGambar.get(0).toString();

                                    dokWan.setNamaDokwan(namaTempat);
                                    dokWan.setAlamat(alamat);
                                    dokWan.setNotelp(notelp);
                                    dokWan.set_id(_id);
                                    dokWan.setLat(lat);
                                    dokWan.setLon(lon);
                                    dokWan.setDokter(dokter);
                                    dokWan.setEmail(email);
                                    dokWan.setGambar(gambar);
                                    dokWan.setArrGambar(arrGambar);
                                    dokWan.setArrJenisHewan(jenisHewan);
                                    dokWan.setArrPerawatan(perawatan);
                                    dokWan.setJarak(jarak);
                                    dokWan.setDuration(duration);
                                    dokWan.setFav(fav);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(DataDokwanPengguna.this, Detail.class);
                                            a.putExtra("namaTempat", newsList.get(position).getNamaDokwan());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("alamat", newsList.get(position).getAlamat());
                                            a.putExtra("nomorTelp", newsList.get(position).getNotelp());
                                            a.putExtra("dokters", newsList.get(position).getDokter());
                                            a.putExtra("latitude", newsList.get(position).getLat());
                                            a.putExtra("longitude", newsList.get(position).getLon());
                                            a.putExtra("email", newsList.get(position).getEmail());
                                            a.putExtra("gambar", newsList.get(position).getArrGambar());
                                            a.putExtra("jenisHewan", newsList.get(position).getArrJenisHewan());
                                            a.putExtra("perawatan", newsList.get(position).getArrPerawatan());
                                            a.putExtra("fav", newsList.get(position).getFav());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(dokWan);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    /**
     * If connected get lat and long
     *
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Log.d("Lat Lon = ", String.valueOf(currentLatitude) + " " + String.valueOf(currentLongitude));

            try {
                newsList.clear();
                JSONObject jsonObj1=null;
                jsonObj1=new JSONObject();
                jsonObj1.put("latitude", String.valueOf(currentLatitude));
                jsonObj1.put("longitude", String.valueOf(currentLongitude));

                getAllPet(jsonObj1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(DataDokwanPengguna.this, SubMenu.class);
        startActivity(i);
        finish();
    }

    public void cari(){
        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<ModelDokwan> filteredList = new ArrayList<ModelDokwan>();

                for (int i = 0; i < newsList.size(); i++) {

                    final String text = newsList.get(i).getNamaDokwan().toLowerCase();
                    if (text.contains(query)) {
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TODO Auto-generated method stub
                                Intent a = new Intent(DataDokwanPengguna.this, Detail.class);
                                a.putExtra("namaTempat", filteredList.get(position).getNamaDokwan());
                                a.putExtra("_id", filteredList.get(position).get_id());
                                a.putExtra("alamat", filteredList.get(position).getAlamat());
                                a.putExtra("nomorTelp", filteredList.get(position).getNotelp());
                                a.putExtra("dokters", filteredList.get(position).getDokter());
                                a.putExtra("latitude", filteredList.get(position).getLat());
                                a.putExtra("longitude", filteredList.get(position).getLon());
                                a.putExtra("email", filteredList.get(position).getEmail());
                                a.putExtra("gambar", filteredList.get(position).getArrGambar());
                                a.putExtra("jenisHewan", filteredList.get(position).getArrJenisHewan());
                                a.putExtra("perawatan", filteredList.get(position).getArrPerawatan());
                                a.putExtra("fav", filteredList.get(position).getFav());
                                startActivity(a);
                            }
                        });
                        filteredList.add(newsList.get(i));
                    }
                }
                adapter = new AdapterDokwanPengguna(DataDokwanPengguna.this, filteredList);
                list.setAdapter(adapter);
            }
        });
    }
}
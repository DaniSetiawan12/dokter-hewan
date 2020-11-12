package com.example.dokterhewan.pengguna;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dokterhewan.R;
import com.example.dokterhewan.adapter.AdapterFav;
import com.example.dokterhewan.adapter.AdapterHistory;
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

public class History extends AppCompatActivity
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

    AdapterHistory adapter;
    ListView list;


    ArrayList<ModelDokwan> newsList = new ArrayList<ModelDokwan>();
    private RequestQueue mRequestQueue;

    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;

    int socketTimeout = 500000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setTitle("History Perjalanan");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterHistory(History.this, newsList, mRequestQueue);
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
    }

    public void getAllPet(JSONObject jsonObject) {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.getDataHistory + "0/", jsonObject,
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
//
                                            gmmIntentUri = Uri.parse("google.navigation:q=" + newsList.get(position).getLat()+","
                                                    +newsList.get(position).getLon());

                                            // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                                            mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                                            // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                                            mapIntent.setPackage(goolgeMap);

                                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                startActivity(mapIntent);

                                            } else {
                                                Toast.makeText(History.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                                                        Toast.LENGTH_LONG).show();
                                            }
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

        req.setRetryPolicy(policy);
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
        Intent i = new Intent(History.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
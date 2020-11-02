package com.example.dokterhewan.pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dokterhewan.R;
import com.example.dokterhewan.users.Login;

public class MainActivity extends AppCompatActivity {

    CardView cardCariLokasi, cardRiwayat, cardFav, cardBantuan, cardTentang, cardLoginAdmin;

    LinearLayout linearAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        cardCariLokasi = (CardView) findViewById(R.id.cardCariLokasi);
        cardRiwayat = (CardView) findViewById(R.id.cardRiwayat);
        cardFav = (CardView) findViewById(R.id.cardFav);
        linearAdmin = (LinearLayout) findViewById(R.id.linearAdmin);


        linearAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        cardCariLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SubMenu.class);
                startActivity(i);
                finish();
            }
        });

        cardFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Favorite.class);
                startActivity(i);
                finish();
            }
        });

        cardRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, History.class);
                startActivity(i);
                finish();
            }
        });
    }
}

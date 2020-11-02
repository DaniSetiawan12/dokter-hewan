package com.example.dokterhewan.pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dokterhewan.R;
import com.example.dokterhewan.users.Login;

public class SubMenu extends AppCompatActivity {

    CardView cardCariNama, cardCariRadius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        getSupportActionBar().hide();

        cardCariNama = (CardView) findViewById(R.id.cardcariNama);
        cardCariRadius = (CardView) findViewById(R.id.cardCariRadius);

        cardCariNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubMenu.this, DataDokwanPengguna.class);
                startActivity(i);
                finish();
            }
        });
        cardCariRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubMenu.this, Maps.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(SubMenu.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
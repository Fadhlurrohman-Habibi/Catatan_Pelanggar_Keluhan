package com.example.catatan_pelanggar_keluhan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private CardView cvInputSiswa,cvInputkeluhan, cvShowList, cvShowKeluhan, cvMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Catatan_Pelanggar_Keluhan);
        setContentView(R.layout.activity_main);

        cvInputSiswa = findViewById(R.id.cv_input);
        cvInputkeluhan = findViewById(R.id.cv_inputkeluhan);
        cvShowList = findViewById(R.id.showPelanggar);
        cvShowKeluhan = findViewById(R.id.showKeluhan);
        cvMaster = findViewById(R.id.cv_master);


        cvMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MasterActivity.class));
            }
        });


        cvShowKeluhan.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ListKeluhan.class));
        });

        cvShowList.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
        });

        cvInputSiswa.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InputActivity.class);
            intent.putExtra("intent_type", 1);
            startActivity(intent);
        });

        cvInputkeluhan.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InputKeluhanActivity.class);
            startActivity(intent);
        });



    }
}
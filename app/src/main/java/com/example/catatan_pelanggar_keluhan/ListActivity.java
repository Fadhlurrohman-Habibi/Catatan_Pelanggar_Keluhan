package com.example.catatan_pelanggar_keluhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.catatan_pelanggar_keluhan.httpconnect.Downloader;

public class ListActivity extends AppCompatActivity {

    private ListView listView;

    //LINK AMBIL DATA DALAM BENTUK JSON
    private String url = "http://192.168.0.100/catatan_pelanggaran_keluhan/tampilList.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        final Downloader downloader = new Downloader(this, url, listView, 0);
        downloader.execute();
    }
}
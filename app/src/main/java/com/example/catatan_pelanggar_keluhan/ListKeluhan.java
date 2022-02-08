package com.example.catatan_pelanggar_keluhan;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catatan_pelanggar_keluhan.httpconnect.DownloadKeluhan;
import com.example.catatan_pelanggar_keluhan.httpconnect.Downloader;

public class ListKeluhan extends AppCompatActivity {

    private ListView listViewKeluhan;

    //LINK AMBIL DATA DALAM BENTUK JSON
    private String url = "http://192.168.0.100/catatan_pelanggaran_keluhan/tampilListKeluhan.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lis_item_keluhan);

        listViewKeluhan = findViewById(R.id.listViewKeluhan);

        final DownloadKeluhan downloadKeluhan = new DownloadKeluhan(this, url, listViewKeluhan, 0);
        downloadKeluhan.execute();

    }
}

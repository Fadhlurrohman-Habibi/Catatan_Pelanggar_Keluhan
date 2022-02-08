package com.example.catatan_pelanggar_keluhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.catatan_pelanggar_keluhan.bottomsheet.listview.BottomSheetListView;
import com.example.catatan_pelanggar_keluhan.httpconnect.Downloader;

public class MasterActivity extends AppCompatActivity {

    private CardView cvBolos, cvUpacara, cvBawaHp, cvBawaSajam, cvRibut, cvBerkelahi, cvTidaksopan, cvMerokok;
    private BottomSheetDialog dialog;
    private BottomSheetListView bottomSheetListView;

    private String linkBolos =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listBolos.php";
    private String linkUpacara =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listUpacara.php";
    private String linkBawaHp =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listBawaHp.php";
    private String linkBawaSajam =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listBawaSajam.php";
    private String linkRibut =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listRibut.php";
    private String linkBerkelahi =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listBerkelahi.php";
    private String linkTidakSopan =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listTidakSopan.php";
    private String linkMerokok =  "http://192.168.0.100/catatan_pelanggaran_keluhan/listMerokok.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        cvBolos = findViewById(R.id.bolos);
        cvUpacara = findViewById(R.id.upacara);
        cvBawaHp = findViewById(R.id.bawaHp);
        cvBawaSajam = findViewById(R.id.bawaSajam);
        cvRibut = findViewById(R.id.ribut);
        cvBerkelahi = findViewById(R.id.berkelahi);
        cvTidaksopan = findViewById(R.id.tidakSopan);
        cvMerokok = findViewById(R.id.merokok);

        cvBolos.setOnClickListener(view -> {
            showDialog(linkBolos);
        });

        cvUpacara.setOnClickListener(view -> {
            showDialog(linkUpacara);
        });

        cvBawaHp.setOnClickListener(view -> {
            showDialog(linkBawaHp);
        });

        cvBawaSajam.setOnClickListener(view -> {
            showDialog(linkBawaSajam);
        });

        cvRibut.setOnClickListener(view -> {
            showDialog(linkRibut);
        });

        cvBerkelahi.setOnClickListener(view -> {
            showDialog(linkBerkelahi);
        });

        cvTidaksopan.setOnClickListener(view -> {
            showDialog(linkTidakSopan);
        });

        cvMerokok.setOnClickListener(view -> {
            showDialog(linkMerokok);
        });
    }

    private void showDialog(String url) {
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.bottom_sheet);
        bottomSheetListView = dialog.findViewById(R.id.bottomSheet);

        final Downloader downloader = new Downloader(this, url, bottomSheetListView, 1);
        downloader.execute();
        dialog.show();
    }
}
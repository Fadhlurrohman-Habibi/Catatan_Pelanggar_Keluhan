package com.example.catatan_pelanggar_keluhan.httpconnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.example.catatan_pelanggar_keluhan.InputActivity;
import com.example.catatan_pelanggar_keluhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser extends AsyncTask<Void, Integer, Integer> {
    private final Context context;
    private final ListView listView;
    private final String data;
    private final int flag;

    ArrayList<String> dataId = new ArrayList<>();
    ArrayList<String> siswa = new ArrayList<>();
    ArrayList<String> dataNis = new ArrayList<>();
    ArrayList<String> dataKelas = new ArrayList<>();
    ArrayList<String> dataWaktu = new ArrayList<>();
    ArrayList<String> dataPelanggaran = new ArrayList<>();
    ArrayList<String> dataPoint = new ArrayList<>();
    ArrayList<String> dataIdSpinner = new ArrayList<>();

    ProgressDialog progressDialog;


    public Parser(Context context, String data, ListView listView, int flag) {
        this.context = context;
        this.data = data;
        this.listView = listView;
        this.flag = flag;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Parser");
        progressDialog.setMessage("Parsing ....Please wait");
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        if (flag == 0 ){
            try {
                return this.parse();
            }catch (Exception e) {
                return 0;
            }
        } else {
            try {
                return this.parseTop();
            }catch (Exception e) {
                return 0;
            }
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (flag == 0) {
            if(integer == 1) {
                //ADAPTER
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, siswa);

                //ADAPT KE LISTVIEW
                listView.setAdapter(adapter);

                //LISTENER
                listView.setOnItemClickListener((parent, view, position, id) -> {

                    String textId = dataId.get(position);
                    String textNama = siswa.get(position);
                    String textNis = dataNis.get(position);
                    String textKelas = dataKelas.get(position);
                    String textWaktu = dataWaktu.get(position);
                    String textPelanggaran = dataPelanggaran.get(position);
                    String textPoint = dataPoint.get(position);
                    String textIdSpinner = dataIdSpinner.get(position);

                    String message = "\nNIS: "+textNis+"\n\nNama: "+textNama+"\n\nKelas: "+textKelas+"\n\nWaktu: "+textWaktu+"\n\nPelanggaran: "+textPelanggaran+"\n\nPoint: "+textPoint;

                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Detail Siswa");
                    adb.setMessage(message);
                    adb.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, InputActivity.class);
                            intent.putExtra("id", textId);
                            intent.putExtra("nis", textNis);
                            intent.putExtra("nama", textNama);
                            intent.putExtra("kelas", textKelas);
                            intent.putExtra("waktu", textWaktu);
                            intent.putExtra("id_spinner", textIdSpinner);
                            intent.putExtra("intent_type", 0);
                            context.startActivity(intent);
                        }
                    });
                    adb.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder adb = new AlertDialog.Builder(context);
                            adb.setTitle("Hapus Data");
                            adb.setIcon(R.drawable.ic_delete);
                            adb.setMessage("\nApakah anda yakin?");
                            adb.setPositiveButton("Tidak", null);
                            adb.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new BackgroundProcess(context, 2).execute(textId);
                                    adapter.remove(adapter.getItem(position));
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            adb.create();
                            adb.show();
                        }
                    });
                    adb.setPositiveButton("Tutup", null);
                    adb.create();
                    adb.show();
                });

            }else {
                Toast.makeText(context,"Gagal parsing data",Toast.LENGTH_SHORT).show();
            }
        }else {
            if(integer == 1) {
                //ADAPTER
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, siswa);

                //ADAPT KE LISTVIEW
                listView.setAdapter(adapter);

                //LISTENER
                listView.setOnItemClickListener((parent, view, position, id) -> {

                    String textNama = siswa.get(position);
                    String textNis = dataNis.get(position);
                    String textKelas = dataKelas.get(position);
                    String textPoint = dataPoint.get(position);

                    String message = "\nNIS: "+textNis+"\n\nNama: "+textNama+"\n\nKelas: "+textKelas+"\n\nPoint: "+textPoint;

                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Detail Siswa");
                    adb.setMessage(message);
                    adb.setPositiveButton("Tutup", null);
                    adb.show();

                });

            }else {
                Toast.makeText(context,"Gagal parsing data",Toast.LENGTH_SHORT).show();
            }
        }
        progressDialog.dismiss();
    }

    //PARSE RECEIVED DATA
    private int parse() {
        try
        {
            //ADD THAT DATA TO JSON ARRAY
            JSONArray jsonArray = new JSONArray(data);

            //CREATE JSON OBJECT TO HOLD A SINGLE ITEM
            JSONObject jsonObjectId;
            JSONObject jsonObjectNama;
            JSONObject jsonObjectNis;
            JSONObject jsonObjectKelas;
            JSONObject jsonObjectWaktu;
            JSONObject jsonObjectPelanggaran;
            JSONObject jsonObjectpoint;
            JSONObject jsonObjectIdSpinner;

            dataId.clear();
            siswa.clear();
            dataNis.clear();
            dataKelas.clear();
            dataWaktu.clear();
            dataPelanggaran.clear();
            dataPoint.clear();
            dataIdSpinner.clear();

            //LOOP ARRAY
            for(int i=0; i<jsonArray.length(); i++) {

                jsonObjectId = jsonArray.getJSONObject(i);
                jsonObjectNama = jsonArray.getJSONObject(i);
                jsonObjectNis = jsonArray.getJSONObject(i);
                jsonObjectKelas = jsonArray.getJSONObject(i);
                jsonObjectWaktu = jsonArray.getJSONObject(i);
                jsonObjectPelanggaran = jsonArray.getJSONObject(i);
                jsonObjectpoint = jsonArray.getJSONObject(i);
                jsonObjectIdSpinner = jsonArray.getJSONObject(i);

                //RETRIEVE NAME
                String id = jsonObjectId.getString("id");
                String name = jsonObjectNama.getString("nama");
                String nis = jsonObjectNis.getString("nis");
                String kelas = jsonObjectKelas.getString("kelas");
                String waktu = jsonObjectWaktu.getString("waktu");
                String pelanggaran = jsonObjectPelanggaran.getString("pelanggaran");
                String point = jsonObjectpoint.getString("point");
                String idSpinner = jsonObjectIdSpinner.getString("idSpinner");


                //ADD IT TO OUR ARRAYLIST
                dataId.add(id);
                siswa.add(name);
                dataNis.add(nis);
                dataKelas.add(kelas);
                dataWaktu.add(waktu);
                dataPelanggaran.add(pelanggaran);
                dataPoint.add(point);
                dataIdSpinner.add(idSpinner);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseTop() {
        try
        {
            //ADD THAT DATA TO JSON ARRAY
            JSONArray jsonArray = new JSONArray(data);

            //CREATE JSON OBJECT TO HOLD A SINGLE ITEM
            JSONObject jsonObjectNama;
            JSONObject jsonObjectNis;
            JSONObject jsonObjectKelas;
            JSONObject jsonObjectpoint;


            siswa.clear();
            dataNis.clear();
            dataKelas.clear();
            dataPoint.clear();

            //LOOP ARRAY
            for(int i=0; i<jsonArray.length(); i++)
            {
                jsonObjectNama = jsonArray.getJSONObject(i);
                jsonObjectNis = jsonArray.getJSONObject(i);
                jsonObjectKelas = jsonArray.getJSONObject(i);
                jsonObjectpoint = jsonArray.getJSONObject(i);

                //RETRIEVE NAME
                String name = jsonObjectNama.getString("nama");
                String nis = jsonObjectNis.getString("nis");
                String kelas = jsonObjectKelas.getString("kelas");
                String point = jsonObjectpoint.getString("point");


                //ADD IT TO OUR ARRAYLIST
                siswa.add(name);
                dataNis.add(nis);
                dataKelas.add(kelas);
                dataPoint.add(point);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

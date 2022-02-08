package com.example.catatan_pelanggar_keluhan.httpconnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.catatan_pelanggar_keluhan.InputActivity;
import com.example.catatan_pelanggar_keluhan.InputKeluhanActivity;
import com.example.catatan_pelanggar_keluhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Keluhan extends AsyncTask<Void, Integer, Integer> {

    private final Context mcontext;
    private final ListView listViewKeluhan;
    private final String dataKeluhan;
    private final int flag1;

    ArrayList<String> dataId = new ArrayList<>();
    ArrayList<String> dataJeniskeluhan = new ArrayList<>();
    ArrayList<String> dataDeskripsi = new ArrayList<>();
    ArrayList<String> dataSaran = new ArrayList<>();
    ArrayList<String> dataTime = new ArrayList<>();

    ProgressDialog progressDialog;

    public Keluhan(Context mcontext, String dataKeluhan, ListView listViewKeluhan, int flag1) {
        this.mcontext = mcontext;
        this.dataKeluhan = dataKeluhan;
        this.listViewKeluhan = listViewKeluhan;
        this.flag1 = flag1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle("Parser");
        progressDialog.setMessage("Parsing ....Please wait");
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        if (flag1 == 0 ){
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

        if (flag1 == 0) {
            if(integer == 1) {
                //ADAPTER
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mcontext, android.R.layout.simple_list_item_1, dataJeniskeluhan);

                //ADAPT KE LISTVIEW
                listViewKeluhan.setAdapter(adapter);

                //LISTENER
                listViewKeluhan.setOnItemClickListener((parent, view, position, id) -> {

                    String textId = dataId.get(position);
                    String textJenisKeluhan = dataJeniskeluhan.get(position);
                    String textDeskripsi = dataJeniskeluhan.get(position);
                    String textSaran = dataDeskripsi.get(position);
                    String textTime = dataSaran.get(position);

                    String message = "\nJenisKeluhan: "+textJenisKeluhan+"\n\nDeskripsi: "+textDeskripsi+"\n\nSaran: "+textSaran+"\n\nTime: "+textTime;

                    AlertDialog.Builder adb = new AlertDialog.Builder(mcontext);
                    adb.setTitle("Detail keluhan");
                    adb.setMessage(message);
                    adb.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(mcontext, InputKeluhanActivity.class);
                            intent.putExtra("id", textId);
                            intent.putExtra("jeniskeluhan", textJenisKeluhan);
                            intent.putExtra("deskripsi", textDeskripsi);
                            intent.putExtra("saran", textSaran);
                            intent.putExtra("waktu", textTime);
                            intent.putExtra("intent_typee", 2);
                            mcontext.startActivity(intent);
                        }
                    });
                    adb.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder adb = new AlertDialog.Builder(mcontext);
                            adb.setTitle("Hapus Data");
                            adb.setIcon(R.drawable.ic_delete);
                            adb.setMessage("\nApakah anda yakin?");
                            adb.setPositiveButton("Tidak", null);
                            adb.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new BackgroundProcess(mcontext, 2).execute(textId);
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
                Toast.makeText(mcontext,"Gagal parsing data",Toast.LENGTH_SHORT).show();
            }
        }else {
            if(integer == 1) {
                //ADAPTER
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mcontext, android.R.layout.simple_list_item_1, dataJeniskeluhan);

                //ADAPT KE LISTVIEW
                listViewKeluhan.setAdapter(adapter);

                //LISTENER
                listViewKeluhan.setOnItemClickListener((parent, view, position, id) -> {

                    String textJenisKeluhan = dataJeniskeluhan.get(position);
                    String textDeskripsi = dataDeskripsi.get(position);
                    String textSaran = dataSaran.get(position);
                    String textTime = dataTime.get(position);

                    String message = "\n\nJenisKeluhan: "+textJenisKeluhan+"\n\nDeskripsi: "+textDeskripsi+"\n\nSaran: "+textSaran+"\n\nTime: "+textTime;

                    AlertDialog.Builder adb = new AlertDialog.Builder(mcontext);
                    adb.setTitle("Detail keluhan");
                    adb.setIcon(R.drawable.person);
                    adb.setMessage(message);
                    adb.setPositiveButton("Tutup", null);
                    adb.show();

                });

            }else {
                Toast.makeText(mcontext,"Gagal parsing data",Toast.LENGTH_SHORT).show();
            }
        }
        progressDialog.dismiss();
    }

    //PARSE RECEIVED DATA
    private int parse() {
        try
        {
            //ADD THAT DATA TO JSON ARRAY
            JSONArray jsonArray = new JSONArray(dataKeluhan);

            //CREATE JSON OBJECT TO HOLD A SINGLE ITEM
            JSONObject jsonObjectId;
            JSONObject jsonObjectJenisKeluhan;
            JSONObject jsonObjectDeskripsi;
            JSONObject jsonObjectSaran;
            JSONObject jsonObjectTime;

            dataId.clear();
            dataJeniskeluhan.clear();
            dataDeskripsi.clear();
            dataSaran.clear();
            dataTime.clear();

            //LOOP ARRAY
            for(int i=0; i<jsonArray.length(); i++) {

                jsonObjectId = jsonArray.getJSONObject(i);
                jsonObjectJenisKeluhan = jsonArray.getJSONObject(i);
                jsonObjectDeskripsi = jsonArray.getJSONObject(i);
                jsonObjectSaran = jsonArray.getJSONObject(i);
                jsonObjectTime = jsonArray.getJSONObject(i);

                //RETRIEVE NAME
                String id = jsonObjectId.getString("id");
                String jeniskeluhan = jsonObjectJenisKeluhan.getString("jeniskeluhan");
                String deskripsi = jsonObjectDeskripsi.getString("deskripsi");
                String saran = jsonObjectSaran.getString("saran");
                String time = jsonObjectTime.getString("time");



                //ADD IT TO OUR ARRAYLIST
                dataId.add(id);
                dataJeniskeluhan.add(jeniskeluhan);
                dataDeskripsi.add(deskripsi);
                dataSaran.add(saran);
                dataTime.add(time);

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
            JSONArray jsonArray = new JSONArray(dataKeluhan);

            //CREATE JSON OBJECT TO HOLD A SINGLE ITEM
            JSONObject jsonObjectJenisKeluhan;
            JSONObject jsonObjectDeskripsi;
            JSONObject jsonObjectSaran;


            dataJeniskeluhan.clear();
            dataDeskripsi.clear();
            dataSaran.clear();

            //LOOP ARRAY
            for(int i=0; i<jsonArray.length(); i++)
            {
                jsonObjectJenisKeluhan = jsonArray.getJSONObject(i);
                jsonObjectDeskripsi = jsonArray.getJSONObject(i);
                jsonObjectSaran = jsonArray.getJSONObject(i);

                //RETRIEVE NAME
                String jeniskeluhan = jsonObjectJenisKeluhan.getString("jeniskeluhan");
                String deskripsi = jsonObjectDeskripsi.getString("deskripsi");
                String saran = jsonObjectSaran.getString("saran");


                //ADD IT TO OUR ARRAYLIST
                dataJeniskeluhan.add(jeniskeluhan);
                dataDeskripsi.add(deskripsi);
                dataSaran.add(saran);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}


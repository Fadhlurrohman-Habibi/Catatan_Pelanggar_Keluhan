package com.example.catatan_pelanggar_keluhan.httpconnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadKeluhan extends AsyncTask<Void, Integer, String> {

    Context mcontext;
    String address1;
    ListView listViewKeluhan;
    int flag1;

    ProgressDialog progressDialog;

    public DownloadKeluhan(Context mcontext, String address1, ListView listViewKeluhan, int flag1) {
        this.mcontext = mcontext;
        this.address1 = address1;
        this.listViewKeluhan = listViewKeluhan;
        this.flag1 = flag1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog =new ProgressDialog(mcontext);
        progressDialog.setTitle("Mengambil data");
        progressDialog.setMessage("Sedang mengambil data...Tunggu!");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return downloadData1();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.dismiss();

        if(result != null) {
            Keluhan keluhan;
            if (flag1 == 0) {
                keluhan = new Keluhan(mcontext, result, listViewKeluhan, 0);
            }else {
                keluhan = new Keluhan(mcontext, result, listViewKeluhan, 1);
            }
            keluhan.execute();

        }else {
            Toast.makeText(mcontext,"Gagal mengambil data!",Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData1() {
        //koneksikan
        InputStream inputStream=null;
        String line = null;

        try {
            URL url = new URL(address1);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(connection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            if(bufferedReader != null) {

                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line+"n");
                }

            }else {
                return null;
            }

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}


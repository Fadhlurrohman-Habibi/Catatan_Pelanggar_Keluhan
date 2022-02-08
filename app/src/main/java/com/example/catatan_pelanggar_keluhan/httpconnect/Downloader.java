package com.example.catatan_pelanggar_keluhan.httpconnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader extends AsyncTask<Void, Integer, String> {

    Context context;
    String address;
    ListView listView;
    int flag;

    ProgressDialog progressDialog;

    public Downloader(Context context, String address, ListView listView, int flag) {
        this.context = context;
        this.address = address;
        this.listView = listView;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog =new ProgressDialog(context);
        progressDialog.setTitle("Mengambil data");
        progressDialog.setMessage("Sedang mengambil data...Tunggu!");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return downloadData();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.dismiss();

        if(result != null) {
            Parser parser;
            if (flag == 0) {
                parser = new Parser(context, result, listView, 0);
            }else {
                parser = new Parser(context, result, listView,1);
            }
            parser.execute();

        }else {
            Toast.makeText(context,"Gagal mengambil data!",Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData() {
        //koneksikan
        InputStream inputStream=null;
        String line = null;

        try {
            URL url = new URL(address);
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

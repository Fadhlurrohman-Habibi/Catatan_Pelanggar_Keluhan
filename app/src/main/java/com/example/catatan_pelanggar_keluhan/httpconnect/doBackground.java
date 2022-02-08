package com.example.catatan_pelanggar_keluhan.httpconnect;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;

public class doBackground extends AsyncTask<String, Void, String> {

    private final Context context;
    private final int flag1;
    private int id;

    public doBackground(Context context, int flag1) {
        this.context = context;
        this.flag1 = flag1;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String hasil = "";
        if (flag1 == 0) {
            try {
                String jeniskeluhan = arg0[0];
                String deskripsi = arg0[1];
                String saran = arg0[2];
                String time = arg0[3];


                String link = "http://192.168.0.100/catatan_pelanggaran_keluhan/simpankeluhan.php?";
                String data = URLEncoder.encode("jeniskeluhan", "UTF-8") + "=" + URLEncoder.encode(jeniskeluhan, "UTF-8");
                data += "&" + URLEncoder.encode("deskripsi", "UTF-8") + "=" + URLEncoder.encode(deskripsi, "UTF-8");
                data += "&" + URLEncoder.encode("saran", "UTF-8") + "=" + URLEncoder.encode(saran, "UTF-8");
                data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link + data));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuilder sb = new StringBuilder("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                hasil = sb.toString();
                return hasil;

            } catch (Exception e) {
                return new String("Exeption: " + e.getMessage());
            }
        } else if (flag1 == 1) {
            try {
                String id = arg0[0];
                String jeniskeluhan = arg0[1];
                String deskripsi = arg0[2];
                String saran = arg0[3];
                String time = arg0[4];


                String link = "http://192.168.0.100/catatan_pelanggaran_keluhan/editkeluhan.php?";
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                data += "&" + URLEncoder.encode("jeniskeluhan", "UTF-8") + "=" + URLEncoder.encode(jeniskeluhan, "UTF-8");
                data += "&" + URLEncoder.encode("deskripsi", "UTF-8") + "=" + URLEncoder.encode(deskripsi, "UTF-8");
                data += "&" + URLEncoder.encode("saran", "UTF-8") + "=" + URLEncoder.encode(saran, "UTF-8");
                data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link + data));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuilder sb = new StringBuilder("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                hasil = sb.toString();
                return hasil;

            } catch (Exception e) {
                return new String("Exeption: " + e.getMessage());
            }
        } else {
            try {
                String id = arg0[0];
                String link = "http://192.168.0.100/catatan_pelanggaran_keluhan/hapuskeluhan.php?id=" + id;

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                hasil = sb.toString();
                return hasil;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }
    }

    @Override
    protected void onPostExecute(String resultK) {
        if (resultK != null) {
            Toast.makeText(context.getApplicationContext(), resultK, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Ada kesalahan", Toast.LENGTH_LONG).show();
        }
    }

}



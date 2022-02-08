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

public class BackgroundProcess extends AsyncTask<String, Void, String> {

    private final Context context;
    private final int flag;
    private int id;

    public BackgroundProcess(Context context, int flag) {
        this.context = context;
        this.flag = flag;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String hasil = "";
        if (flag == 0) {
            try {
                String nis = arg0[0];
                String nama = arg0[1];
                String kelas = arg0[2];
                String waktu = arg0[3];
                String pelanggaran = arg0[4];
                String point = arg0[5];
                String idSpinner = arg0[6];

                String link = "http://192.168.0.100/catatan_pelanggaran_keluhan/simpan.php?";
                String data = URLEncoder.encode("nis", "UTF-8") + "=" + URLEncoder.encode(nis, "UTF-8");
                data += "&" + URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8");
                data += "&" + URLEncoder.encode("kelas", "UTF-8") + "=" + URLEncoder.encode(kelas, "UTF-8");
                data += "&" + URLEncoder.encode("waktu", "UTF-8") + "=" + URLEncoder.encode(waktu, "UTF-8");
                data += "&" + URLEncoder.encode("pelanggaran", "UTF-8") + "=" + URLEncoder.encode(pelanggaran, "UTF-8");
                data += "&" + URLEncoder.encode("point", "UTF-8") + "=" + URLEncoder.encode(point, "UTF-8");
                data += "&" + URLEncoder.encode("idSpinner", "UTF-8") + "=" + URLEncoder.encode(idSpinner, "UTF-8");

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
        } else if (flag == 1) {
            try {
                String id = arg0[0];
                String nis = arg0[1];
                String nama = arg0[2];
                String kelas = arg0[3];
                String waktu = arg0[4];
                String pelanggaran = arg0[5];
                String point = arg0[6];

                String link = "http://192.168.0.100/catatan_pelanggaran_keluhan/edit.php?";
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                data += "&" + URLEncoder.encode("nis", "UTF-8") + "=" + URLEncoder.encode(nis, "UTF-8");
                data += "&" + URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8");
                data += "&" + URLEncoder.encode("kelas", "UTF-8") + "=" + URLEncoder.encode(kelas, "UTF-8");
                data += "&" + URLEncoder.encode("waktu", "UTF-8") + "=" + URLEncoder.encode(waktu, "UTF-8");
                data += "&" + URLEncoder.encode("pelanggaran", "UTF-8") + "=" + URLEncoder.encode(pelanggaran, "UTF-8");
                data += "&" + URLEncoder.encode("point", "UTF-8") + "=" + URLEncoder.encode(point, "UTF-8");

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
                String link = "http://192.168.0.100/catatan_pelanggaran_keluhan/hapus.php?id=" + id;

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
    protected void onPostExecute(String result) {
        if (result != null) {
            Toast.makeText(context.getApplicationContext(), result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Ada kesalahan", Toast.LENGTH_LONG).show();
        }
    }
}


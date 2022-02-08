package com.example.catatan_pelanggar_keluhan;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.catatan_pelanggar_keluhan.httpconnect.BackgroundProcess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputActivity extends AppCompatActivity {

    private EditText etNis, etNama, etKelas, etWaktu;
    private TextInputLayout inputLayoutNis, inputLayoutNama, inputLayoutKelas, inputLayoutWaktu;
    private Spinner spinnerPelanggaran;
    private TextView tvPoint, tvJudulInput;
    private Button btnAdd, btnEdit;
    private String pelanggaran = "";
    private String textError = "Harus di isi";

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        etNis = findViewById(R.id.et_nis);
        etNama = findViewById(R.id.et_nama);
        etKelas = findViewById(R.id.et_kelas);
        etWaktu = findViewById(R.id.et_waktu);
        inputLayoutNis = findViewById(R.id.inputLayoutNis);
        inputLayoutNama = findViewById(R.id.inputLayoutNama);
        inputLayoutKelas = findViewById(R.id.inputLayoutKelas);
        inputLayoutWaktu = findViewById(R.id.inputLayoutWaktu);
        spinnerPelanggaran = findViewById(R.id.spinnerPelanggaran);
        tvPoint = findViewById(R.id.tv_point);
        tvJudulInput = findViewById(R.id.judulInput);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);

        setupView();

        etWaktu.setOnClickListener(view -> {
            showDateTimeDialog(etWaktu);
        });

        spinnerPelanggaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                pelanggaran = spinnerPelanggaran.getSelectedItem().toString();

                switch (pelanggaran) {
                    case "Bolos sekolah":
                        tvPoint.setText("15");
                        break;
                    case "Tidak ikut upacara":
                    case "Berkelahi":
                    case "Membawa senjata tajam":
                    case "Tidak sopan":
                        tvPoint.setText("10");
                        break;
                    case "Membawa Handphone":
                    case "Ribut di Kelas":
                    case "Merokok":
                        tvPoint.setText("5");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(InputActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd.setOnClickListener(view -> {
            validasiInput(0);
        });
    }

    private void setupView() {

        int intentType = getIntent().getIntExtra("intent_type", 1);

        if (intentType == 0) {
            btnAdd.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
            getEditData();
        } else {
            btnAdd.setVisibility(View.VISIBLE);
        }
    }

    private void getEditData() {
        id = getIntent().getStringExtra("id");
        String nis = getIntent().getStringExtra("nis");
        String nama = getIntent().getStringExtra("nama");
        String kelas = getIntent().getStringExtra("kelas");
        String waktu = getIntent().getStringExtra("waktu");
        String idSpinner = getIntent().getStringExtra("id_spinner");

        etNis.setText(nis);
        etNama.setText(nama);
        etKelas.setText(kelas);
        etWaktu.setText(waktu);
        spinnerPelanggaran.setSelection(Integer.parseInt(idSpinner));
        tvJudulInput.setText("Edit Data Pelanggar");

        btnEdit.setOnClickListener(view -> {
            validasiInput(1);
        });
    }

    private void showDateTimeDialog(EditText etWaktu) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm", Locale.US);
                        etWaktu.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(InputActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        };
        new DatePickerDialog(InputActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void validasiInput(int flag) {
        boolean error = false;

        if(etNis.getText().toString().isEmpty()) {
            error = true;
            inputLayoutNis.setError(textError);
        } else {
            inputLayoutNis.setError(null);
        }

        if(etNama.getText().toString().isEmpty()) {
            error = true;
            inputLayoutNama.setError(textError);
        } else {
            inputLayoutNama.setError(null);
        }

        if(etKelas.getText().toString().isEmpty()) {
            error = true;
            inputLayoutKelas.setError(textError);
        } else {
            inputLayoutKelas.setError(null);
        }

        if(etWaktu.getText().toString().isEmpty()) {
            error = true;
            inputLayoutWaktu.setError(textError);
        } else {
            inputLayoutWaktu.setError(null);
        }

        if (!error) {
            doTask(flag);
        }
    }

    private void doTask(int flag) {
        if (flag == 0) {
            String nis = etNis.getText().toString();
            String nama = etNama.getText().toString();
            String pelanggaran = spinnerPelanggaran.getSelectedItem().toString();
            String kelas = etKelas.getText().toString();
            String waktu = etWaktu.getText().toString();
            String point = tvPoint.getText().toString();
            String idSpinner = String.valueOf(spinnerPelanggaran.getSelectedItemId());

            new BackgroundProcess(this, 0).execute(nis, nama, kelas, waktu, pelanggaran, point, idSpinner );

            etNis.setText(null);
            etWaktu.setText(null);
            etKelas.setText(null);
            etNama.setText(null);
        }else {
            String nisBaru = etNis.getText().toString();
            String namaBaru = etNama.getText().toString();
            String kelasBaru = etKelas.getText().toString();
            String waktuBaru = etWaktu.getText().toString();
            String pelanggaranBaru = spinnerPelanggaran.getSelectedItem().toString();
            String pointBaru = tvPoint.getText().toString();

            new BackgroundProcess(this, 1).execute(id, nisBaru, namaBaru, kelasBaru, waktuBaru, pelanggaranBaru, pointBaru);
            etNis.setText(null);
            etWaktu.setText(null);
            etKelas.setText(null);
            etNama.setText(null);
            finish();
        }
    }
}
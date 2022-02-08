package com.example.catatan_pelanggar_keluhan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catatan_pelanggar_keluhan.httpconnect.doBackground;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputKeluhanActivity extends AppCompatActivity {

    private EditText etJenisKeluhan, etDeskripsi, etSaran, etTime;
    private TextInputLayout inputLayoutJenisKeluhan, inputLayoutDeskripsi, inputLayoutSaran, inputLayoutTime;
    private TextView tvJudulInput;
    private Button btnAdd, btnEdit;
    private String textError = "Harus di isi";

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_keluhan);

        etJenisKeluhan = findViewById(R.id.et_jeniskeluhan);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etSaran = findViewById(R.id.et_saran);
        etTime = findViewById(R.id.et_time);
        inputLayoutJenisKeluhan = findViewById(R.id.inputLayoutJenisKeluhan);
        inputLayoutDeskripsi = findViewById(R.id.inputLayoutDeskripsi);
        inputLayoutSaran = findViewById(R.id.inputLayoutSaran);
        inputLayoutTime = findViewById(R.id.inputLayoutTime);
        tvJudulInput = findViewById(R.id.judulInput);
        btnAdd = findViewById(R.id.btnAddK);
        btnEdit = findViewById(R.id.btnEditK);

        setupView();

        etTime.setOnClickListener(view -> {
            showDateTimeDialog(etTime);
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
        String jeniskeluhan = getIntent().getStringExtra("jeniskeluhan");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        String saran = getIntent().getStringExtra("saran");
        String time = getIntent().getStringExtra("time");

        etJenisKeluhan.setText(jeniskeluhan);
        etDeskripsi.setText(deskripsi);
        etSaran.setText(saran);
        etTime.setText(time);
        tvJudulInput.setText("Edit Data Keluhan");

        btnEdit.setOnClickListener(view -> {
            validasiInput(1);
        });
    }

    private void showDateTimeDialog(EditText etTime) {
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
                        etTime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(InputKeluhanActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        };
        new DatePickerDialog(InputKeluhanActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void validasiInput(int flag) {
        boolean error = false;

        if(etJenisKeluhan.getText().toString().isEmpty()) {
            error = true;
            inputLayoutJenisKeluhan.setError(textError);
        } else {
            inputLayoutJenisKeluhan.setError(null);
        }

        if(etDeskripsi.getText().toString().isEmpty()) {
            error = true;
            inputLayoutDeskripsi.setError(textError);
        } else {
            inputLayoutDeskripsi.setError(null);
        }

        if(etSaran.getText().toString().isEmpty()) {
            error = true;
            inputLayoutSaran.setError(textError);
        } else {
            inputLayoutSaran.setError(null);
        }

        if(etTime.getText().toString().isEmpty()) {
            error = true;
            inputLayoutTime.setError(textError);
        } else {
            inputLayoutTime.setError(null);
        }

        if (!error) {
            doTask(flag);
        }
    }

    private void doTask(int flag) {
        if (flag == 0) {
            String jeniskeluhan = etJenisKeluhan.getText().toString();
            String deskripsi = etDeskripsi.getText().toString();
            String saran = etSaran.getText().toString();
            String time = etTime.getText().toString();

            new doBackground(this, 0).execute(jeniskeluhan, deskripsi, saran, time );

            etJenisKeluhan.setText(null);
            etTime.setText(null);
            etDeskripsi.setText(null);
            etSaran.setText(null);
        }else {
            String jkBaru = etJenisKeluhan.getText().toString();
            String dkBaru = etDeskripsi.getText().toString();
            String srBaru = etSaran.getText().toString();
            String timeBaru = etTime.getText().toString();

            new doBackground(this, 1).execute(id, jkBaru, dkBaru, srBaru, timeBaru);
            etJenisKeluhan.setText(null);
            etTime.setText(null);
            etDeskripsi.setText(null);
            etSaran.setText(null);
            finish();
        }
    }
}

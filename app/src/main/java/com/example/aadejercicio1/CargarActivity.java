package com.example.aadejercicio1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CargarActivity extends AppCompatActivity {

    Button btCargar;
    EditText etCargar;
    TextView tvContactosCargados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        btCargar = findViewById(R.id.btCargar);
        etCargar = findViewById(R.id.etCargar);
        tvContactosCargados = findViewById(R.id.tvContactosCargados);
    }

    private void initEvents() {
        btCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvContactosCargados.setText(cargarArchivo());
            }
        });
    }

    private String cargarArchivo() {
        String name = etCargar.getText().toString();
        File f = new File(getExternalFilesDir(null), name + ".csv");
        String texto = "";
        if(f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String linea = "";
                while((linea = br.readLine()) != null){
                    texto += linea;
                }
            } catch (IOException e) {
                msg("Error");
            }
        }
        return texto;
    }

    private void msg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }



}

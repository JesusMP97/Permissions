package com.example.aadejercicio1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GuardarActivity extends AppCompatActivity {

    TextView tvMostrar;
    EditText etNombreArchivo;
    Button btGuardar;
    List<Contacto> contactos;
    Toolbar toolbar2;
    String name;
    SharedPreferences pref;
    SharedPreferences config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar);
        Intent i = getIntent();
        contactos = (List)i.getSerializableExtra("contactos");

        initComponents();
        initEvents();
    }

    private void initEvents() {
        mostrarContactos();
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombreArchivo.getText().toString() != "") {
                    almacenarArchivo();
                    if(config.getBoolean("recordar", true)) {
                        storeValues();
                    }
                }
            }
        });
        if(config.getBoolean("recordar", true)) {
            loadValues();
        }
    }

    private void loadValues() {
        etNombreArchivo.setText(pref.getString("guardarArchivo", ""));
    }

    private void storeValues() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("guardarArchivo", name);
        editor.commit();
    }

    private void msg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void mostrarContactos() {
        tvMostrar.setText(contactos.toString());
    }

    private void initComponents() {
        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        tvMostrar = findViewById(R.id.tvMostrar);
        etNombreArchivo = findViewById(R.id.etNombreArchivo);
        btGuardar = findViewById(R.id.btGuardar);
        pref = getSharedPreferences("storedValues", Context.MODE_PRIVATE);
        config = PreferenceManager.getDefaultSharedPreferences(this);

    }

    private void almacenarArchivo() {
        name = etNombreArchivo.getText().toString();
        File f = new File(getExternalFilesDir(null), name + ".csv");
        try{
            FileWriter fw = new FileWriter(f);
            fw.write(contactosToCSV());
            fw.flush();
            fw.close();
            msg("Guardado con exito en: " + f.getAbsolutePath());
        }catch(IOException e){
            tvMostrar.setText(e.getMessage());
            msg(e.getMessage());
        }

    }

    private String contactosToCSV(){
        String cadena = "id,nombre,numero\n";
        for (int i = 0; i < contactos.size(); i++){
            cadena += contactos.get(i).toCSV() + "\n";
        }
        return cadena;
    }


}

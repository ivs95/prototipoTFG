package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class hallaRitmos extends Activity {

    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    private String dificultad;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_adivinar);

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloAdivinar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        TextView nota = (TextView)findViewById(R.id.nota);
        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");
        dificultad = getIntent().getExtras().getString("dificultad");
    }


    public void reproducir(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(0));
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    public void adivina(View view){
        Intent i = new Intent(this, SeleccionarAdivinar.class);
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);
        i.putExtra("dificultad", dificultad);
        startActivity(i);
    }


}
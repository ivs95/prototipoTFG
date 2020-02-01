package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuJugar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jugar);
    }

    public void modo_adivinar(View view){
        Intent i = new Intent(this, NivelesAdivinar.class);
        startActivity(i);
    }

    public void modo_imitar(View view){
        Intent i = new Intent(this, NivelesImitar.class);
        startActivity(i);
    }
}

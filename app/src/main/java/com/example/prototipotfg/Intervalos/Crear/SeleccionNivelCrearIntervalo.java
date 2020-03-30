package com.example.prototipotfg.Intervalos.Crear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SeleccionNivelCrearIntervalo extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(50, 0, 50, 0);

        //Creamos los botones en bucle
        for (int i = 0; i < 8; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel " + (i + 1));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nivel_seleccionado(v);
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
        }
    }

    public void nivel_seleccionado(View view) {
        Intent i = new Intent(this, SeleccionarAdivinarCrearIntervalo.class);
        Random random = new Random();
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();


        //Nivel que se ha seleccionado
        ArrayList<Octavas> octavas = Controlador.getInstance().getOctavas();
        ArrayList<Intervalos> intervalos = Controlador.getInstance().getIntervalos();
        HashMap<String, String> notas = null;

            Octavas octava_intervalos = octavas.get(random.nextInt(octavas.size()));
            notas = FactoriaNotas.getInstance().getNumNotasAleatorias(Controlador.getInstance().getNum_opciones()+1, Instrumentos.Piano, octava_intervalos);


        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        ArrayList<String> rutas = new ArrayList<>(notas.values());

        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);

        startActivity(i);
    }
}
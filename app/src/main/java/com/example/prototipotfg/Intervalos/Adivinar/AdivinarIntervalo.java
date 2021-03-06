package com.example.prototipotfg.Intervalos.Adivinar;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.Entidades.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Mix.Ejercicios.AdivinarIntervaloMix;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.VISIBLE;
import static java.lang.Thread.sleep;

public class AdivinarIntervalo extends Activity {
    protected View botonSeleccionado;
    protected View respuestaCorrecta;
    protected String respuesta;
    protected ArrayList<Button> botonesOpciones = new ArrayList<>();
    protected ArrayList<Pair<Notas, Octavas>> notasIntervalo = new ArrayList<>();
    protected String intervalo_correcto;
    protected boolean comprobada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_adivinar_intervalo);
        notasIntervalo = FactoriaNotas.getInstance().getNotasIntervalo(Controlador.getInstance().getOctavas(), Controlador.getInstance().getRango());

        int tono1 = notasIntervalo.get(0).first.getTono();
        int tono2 = notasIntervalo.get(1).first.getTono();

        Intervalos intervalo = getIntervaloConDif((tono2 - tono1));
        this.intervalo_correcto = intervalo.getNombre();
        int posicion_intervalo = intervalo.getNumero();

        FactoriaNotas.getInstance().setReferencia(notasIntervalo.get(0).second);
        LinearLayout opciones = findViewById(R.id.opciones);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 50);
        Random rand = new Random();
        int num_respuestas = Controlador.getInstance().getNum_opciones();
        int random1 = 0;
        if (Controlador.getInstance().getDificultad().equals(Dificultad.Facil)) {
            random1 = rand.nextInt(Controlador.getInstance().getRango() + Controlador.getInstance().getRango()) - Controlador.getInstance().getRango();
        } else {
            random1 = rand.nextInt(Controlador.getInstance().getRango()) + 1;
            if (posicion_intervalo < 0) {
                random1 = -random1;
            }
        }
        ArrayList<Integer> aux = new ArrayList<>();
        aux.add(posicion_intervalo);


        for (int i = 0; i < num_respuestas - 1; i++) {
            while (aux.contains(random1) || random1 == 0) {
                if (Controlador.getInstance().getDificultad().equals(Dificultad.Facil)) {
                    random1 = rand.nextInt(Controlador.getInstance().getRango() + Controlador.getInstance().getRango()) - Controlador.getInstance().getRango();
                } else {
                    random1 = rand.nextInt(Controlador.getInstance().getRango()) + 1;
                    if (posicion_intervalo < 0) {
                        random1 = -random1;
                    }
                }
            }
            aux.add(random1);
        }

        Collections.shuffle(aux);

        for (int i = 0; i < num_respuestas; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            button.setLayoutParams(lp);
            button.setText(Intervalos.getIntervaloPorDiferencia(aux.get(i)).getNombre());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            button.setPadding(0, 0, 0, 0);
            if (button.getText().equals(this.intervalo_correcto)) {
                respuestaCorrecta = button;
            }
            botonesOpciones.add(button);
            opciones.addView(button);
        }
        if (!(this instanceof AdivinarIntervaloMix)) {
            if (GestorBBDD.getInstance().esPrimerNivelAdivinar(Controlador.getInstance().getModo_juego(), Controlador.getInstance().getNivel()) && Controlador.getInstance().getNivel() != 1) {

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Adivinar_Intervalo, findViewById(android.R.id.content).getRootView(), false, 0, 0);
            }
        }
    }

    public void reproduceIntervaloRespuesta(final View view) throws InterruptedException {
        view.setEnabled(false);
        reproduceNota("piano/" + notasIntervalo.get(0).second.getPath() + notasIntervalo.get(0).first.getPath());
        sleep(750);
        reproduceNota("piano/" + notasIntervalo.get(1).second.getPath() + notasIntervalo.get(1).first.getPath());
        sleep(10);
        view.setEnabled(true);
    }


    public void reproduceNota(String ruta) {
        AssetFileDescriptor afd;
        try {
            afd = getAssets().openFd(ruta);
            Reproductor.getInstance().reproducirNota(afd);
            afd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void respuesta_seleccionada(View view) {
        if (!comprobada) {
            Button b = (Button) view;
            if (botonSeleccionado != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_300)));
                }
            }
            botonSeleccionado = b;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_700)));
            }

            respuesta = b.getText().toString();
            ponerComprobarVisible(VISIBLE);
            findViewById(R.id.comprobar).setEnabled(true);
        }
    }

    protected void ponerComprobarVisible(int visible) {
        findViewById(R.id.comprobar).setVisibility(visible);
    }

    public void comprobarResultado(View view) {
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getNivel();

        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getRango()).ordinal();
        NivelAdivinar nivel = null;
        this.comprobada = true;

        if (respuesta != this.intervalo_correcto) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getNivel())
                    GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Adivinar_Intervalo.toString(), false);
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                nivel = new NivelAdivinar(ModoJuego.Adivinar_Intervalo.getNombre(), Controlador.getInstance().getNivel(), false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0, 1);
            }
        } else {
            if (Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Adivinar_Intervalo.toString(), true);
            nivel = new NivelAdivinar(ModoJuego.Adivinar_Intervalo.getNombre(), Controlador.getInstance().getNivel(), true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
        }

        GestorBBDD.getInstance().insertaNivelAdivinar(nivel);
        findViewById(R.id.comprobar).setVisibility(View.GONE);

        for (Button b : botonesOpciones) {
            b.setEnabled(false);
        }

        findViewById(R.id.botonIntervalo).setEnabled(false);
        findViewById(R.id.botonIntervalo).setAlpha(.5f);
        findViewById(R.id.botonReferencia).setEnabled(false);
        findViewById(R.id.botonReferencia).setAlpha(.5f);

        int nivelNuevo = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getNivel();
        int rangoNuevo = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getRango()).ordinal();

        if (rangoActual != rangoNuevo) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            RangosPuntuaciones.mostrar_popUp_rango(view, rangoActual, rangoNuevo, inflater, ModoJuego.Adivinar_Intervalo.toString());
        }

        if (nivelActual != nivelNuevo) {
            Controlador.getInstance().setNivel(nivelNuevo);
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Adivinar_Intervalo, findViewById(android.R.id.content).getRootView(), true, nivelActual, nivelNuevo);

            Controlador.getInstance().estableceDificultad();
        }

        findViewById(R.id.continuar_ai).setVisibility(View.VISIBLE);

    }

    public Intervalos getIntervaloConDif(int dif) {
        boolean OK = false;
        int i = 0;
        Intervalos[] intervalos_lista = Intervalos.values();
        while (i < 24 && !OK) {
            if (intervalos_lista[i].getDiferencia() == dif)
                OK = true;
            i++;
        }

        return intervalos_lista[i - 1];
    }


    public void reproducirReferencia(View view) throws IOException {
        AssetFileDescriptor afd = getAssets().openFd(FactoriaNotas.getInstance().getReferencia());
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();
    }

    public void continuar(View view) {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}

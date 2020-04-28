package com.example.prototipotfg.Ritmos.Crear;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.Ejercicios.AdivinarAcordeExamen;
import com.example.prototipotfg.Examen.Ejercicios.CrearRitmoExamen;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.MediaPlayerRitmos;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.example.prototipotfg.Enumerados.DuracionSonido.getSonidoPorSimbolo;

public class CrearRitmo extends Activity {

    protected int compas = 4;
    private int num = 4;
    protected int longitud = compas*num;

    protected ArrayList<Integer> ritmos1  = new ArrayList<>();
    protected ArrayList<Integer> ritmos2 = new ArrayList<>();
    protected ArrayList<Integer> ritmos3 = new ArrayList<>();
    protected ArrayList<Integer> ritmos4 = new ArrayList<>();
    protected ArrayList<Integer> resultado1 = new ArrayList<>();
    protected ArrayList<Integer> resultado2 = new ArrayList<>();
    protected ArrayList<Integer> resultado3 = new ArrayList<>();
    protected ArrayList<Integer> resultado4 = new ArrayList<>();
    protected ArrayList<Integer> metronomo = new ArrayList<>();
    protected ArrayList<Button> botonesGuia = new ArrayList<>();

    private boolean comprobado = false;
    protected final int COMPASES = 16;
    private int indiceSonidoActual;
    private int indicePintar;
    protected int pausa;
    protected boolean running;
    private boolean runningPropio;
    private boolean go1 = false;
    private boolean go2 = false;
    private boolean go3 = false;
    private boolean go4 = false;
    private boolean goMetronomo = false;
    private boolean tick = false;
    private boolean end = false;
    private boolean play = false;
    private boolean pause = false;
    protected int nivel;
    private int indice = 0;

    protected MediaPlayerRitmos mediaPlayer1 =  new MediaPlayerRitmos();
    protected MediaPlayerRitmos mediaPlayer2 =  new MediaPlayerRitmos();
    protected MediaPlayerRitmos mediaPlayer3 =  new MediaPlayerRitmos();
    protected MediaPlayerRitmos mediaPlayer4 =  new MediaPlayerRitmos();
    protected MediaPlayerRitmos mediaPlayerMetronomo =  new MediaPlayerRitmos();

    protected Thread hilo_ritmos = new Thread(new Runnable(){
        @Override
        public void run() {
            while(!end) {
                while (running) {

                    play = false;
                    if (metronomo.get(indice) == 1) {
                        if (indice == 0)
                            tick = true;
                        goMetronomo = true;
                    }
                    int notaActual1 = ritmos1.get(indice);
                    if (notaActual1 == 1) {
                        go1 = true;
                    }
                    if (nivel > 2) {
                        int notaActual2 = ritmos2.get(indice);
                        if (notaActual2 == 1) {
                            go2 = true;
                        }
                        if (nivel > 4) {
                            int notaActual3 = ritmos3.get(indice);
                            if (notaActual3 == 1) {
                                go3 = true;
                            }
                            if (nivel > 6) {
                                int notaActual4 = ritmos4.get(indice);
                                if (notaActual4 == 1) {
                                    go4 = true;
                                }
                            }
                        }
                    }
                    if ((!play && running)) {

                        indiceSonidoActual = indice;
                        indicePintar=indice;
                        if (!comprobado) {
                            if (indicePintar == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        botonesGuia.get(indicePintar).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_purple_600)));
                                        botonesGuia.get(botonesGuia.size() - 1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
                                    }
                                });
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        botonesGuia.get(indicePintar).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_purple_600)));
                                        botonesGuia.get(indicePintar-1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
                                    }
                                });
                            }
                        }
                        indice++;
                    }
                    try {
                        Thread.sleep(pausa);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    if (indice >= COMPASES)
                        indice = 0;
                }
            }
        }

    });

    protected Thread hilo_ritmos_propio = new Thread(new Runnable(){
        @Override
        public void run() {
            while(!end) {
                while (runningPropio) {
                    play = false;
                    if (metronomo.get(indice) == 1) {
                        if (indice == 0)
                            tick = true;
                        goMetronomo = true;
                    }
                    int notaActual1 = resultado1.get(indice);
                    if (notaActual1 == 1) {
                        go1 = true;
                    }
                    if (nivel > 2) {
                        int notaActual2 = resultado2.get(indice);
                        if (notaActual2 == 1) {
                            go2 = true;
                        }
                        if (nivel > 4) {
                            int notaActual3 = resultado3.get(indice);
                            if (notaActual3 == 1) {
                                go3 = true;
                            }
                            if (nivel > 6) {
                                int notaActual4 = resultado4.get(indice);
                                if (notaActual4 == 1) {
                                    go4 = true;
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(pausa);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!play && runningPropio) {
                        indice++;
                    }
                    if (indice == COMPASES) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.botonPlayRitmo).setEnabled(true);     findViewById(R.id.botonPlayRitmo).setAlpha(1);
                                findViewById(R.id.botonStopRitmo).setEnabled(false);     findViewById(R.id.botonStopRitmo).setAlpha(.5f);
                                findViewById(R.id.botonPalmada).setEnabled(false);       findViewById(R.id.botonPalmada).setAlpha(.5f);
                                findViewById(R.id.botonCaja).setEnabled(false);          findViewById(R.id.botonCaja).setAlpha(.5f);
                                findViewById(R.id.botonTambor).setEnabled(false);        findViewById(R.id.botonTambor).setAlpha(.5f);
                                findViewById(R.id.botonPlatillo).setEnabled(false);      findViewById(R.id.botonPlatillo).setAlpha(.5f);
                            }
                        });
                        indice = 0;
                        runningPropio = false;
                    }
                }

            }
        }

    });

    protected Thread hiloPlayer1 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(!end) {
                    while (running || runningPropio) {

                        if (go1) {
                            mediaPlayer1.play();
                            go1 = false;
                        }
                        if (go2) {
                            mediaPlayer2.play();
                            go2 = false;
                        }
                        if (go3) {
                            mediaPlayer3.play();
                            go3 = false;
                        }
                        if (go4) {
                            mediaPlayer4.play();
                            go4 = false;
                        }
                        if (goMetronomo) {
                            mediaPlayerMetronomo.playMetronomo(tick);
                            goMetronomo = false;
                            tick = false;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState){

        final Context contexto = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearritmos);

        nivel = Controlador.getInstance().getNivel();

        GestorBBDD.getInstance().modoRealizado(ModoJuego.Realiza_Ritmo);


        findViewById(R.id.continuar_cr).setEnabled(false);           findViewById(R.id.continuar_cr).setAlpha(.5f);

        Random random = new Random();
        ritmos1 = new ArrayList<>();
        ritmos2 = new ArrayList<>();
        ritmos3 = new ArrayList<>();
        ritmos4 = new ArrayList<>();
        for(int x = 0; x<4; x++) {
            int nota = random.nextInt(3) + 1;
            //Llenar aleatorios

            for (int j = getSonidoPorSimbolo(nota).getSilencio(); j <= longitud; j += getSonidoPorSimbolo(nota).getSilencio()) {
                if(x == 0)
                    agregaFigura(nota, ritmos1, compas);
                else if (x==1)
                    agregaFigura(nota, ritmos2, compas);
                else if (x==2)
                    agregaFigura(nota, ritmos3, compas);
                else if (x==3)
                    agregaFigura(nota, ritmos4, compas);
                if (longitud - j >= 4)
                    nota = random.nextInt(4);
                else if (longitud - j == 3 || longitud - j == 2)
                    nota = random.nextInt(2) + 2;
                else if (longitud - j == 1)
                    nota = 3;
            }

        }

        if (nivel % 2 == 1){
            pausa = 700;
        }
        else
            pausa = 475;

        if(nivel == 8) pausa = 400;

        TextView titulo = findViewById(R.id.tituloCrearRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        LinearLayout guia = findViewById(R.id.linearRitmo);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1 );
        running = false;
        hilo_ritmos.start();
        hiloPlayer1.start();
        hilo_ritmos_propio.start();
        mediaPlayerMetronomo.initMetronomo(this);
        mediaPlayer1.init1(this);
        if(nivel>2) {
            mediaPlayer2.init2(this);
            findViewById(R.id.botonCaja).setVisibility(View.VISIBLE);
            if(nivel>4) {
                mediaPlayer3.init3(this);
                findViewById(R.id.botonTambor).setVisibility(View.VISIBLE);
                if(nivel > 6) {
                    mediaPlayer4.init4(this);
                    findViewById(R.id.botonPlatillo).setVisibility(View.VISIBLE);
                }
            }
        }
        for (int i = 0; i < COMPASES; i++){
            resultado1.add(0);
            resultado2.add(0);
            resultado3.add(0);
            resultado4.add(0);
            if(i%4==0){
                metronomo.add(i,1);
            }
            else{
                metronomo.add(i,0);
            }

            Button button = new Button(this);
            button.setEnabled(false);
            button.setText("");
            button.setLayoutParams(lp);
            guia.addView(button);
            botonesGuia.add(button);
        }
        findViewById(R.id.botonStopRitmo).setEnabled(false);   findViewById(R.id.botonStopRitmo).setAlpha(.5f);
        findViewById(R.id.botonPalmada).setEnabled(false);   findViewById(R.id.botonPalmada).setAlpha(.5f);
        findViewById(R.id.botonCaja).setEnabled(false);   findViewById(R.id.botonCaja).setAlpha(.5f);
        findViewById(R.id.botonTambor).setEnabled(false);   findViewById(R.id.botonTambor).setAlpha(.5f);
        findViewById(R.id.botonPlatillo).setEnabled(false);   findViewById(R.id.botonPlatillo).setAlpha(.5f);

        if (!(this instanceof CrearRitmoExamen)) {
            GestorBBDD.getInstance().modoRealizado(ModoJuego.Realiza_Ritmo);
            if (GestorBBDD.getInstance().esPrimerNivelAdivinar(ModoJuego.Realiza_Ritmo, Controlador.getInstance().getNivel()) && Controlador.getInstance().getNivel() != 1) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Realiza_Ritmo, findViewById(android.R.id.content).getRootView());
            }
        }

    }

    public void play(@NotNull final View view){
        findViewById(R.id.botonStopRitmo).setEnabled(true);            findViewById(R.id.botonStopRitmo).setAlpha(1);
        findViewById(R.id.botonPlayRitmoPropio).setEnabled(false);     findViewById(R.id.botonPlayRitmoPropio).setAlpha(.5f);
        findViewById(R.id.botonPalmada).setEnabled(true);              findViewById(R.id.botonPalmada).setAlpha(1);
        findViewById(R.id.botonCaja).setEnabled(true);                 findViewById(R.id.botonCaja).setAlpha(1);
        findViewById(R.id.botonTambor).setEnabled(true);               findViewById(R.id.botonTambor).setAlpha(1);
        findViewById(R.id.botonPlatillo).setEnabled(true);             findViewById(R.id.botonPlatillo).setAlpha(1);
        if(running == true){
            if (indice == 0)
                indice = botonesGuia.size() - 1;
            else
                indice--;
            botonesGuia.get(indice).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
            indice = 0;
            play = true;
        }
        else {
            running = true;
            if(pause == false)
                indice = 0;
            pause = false;
        }
    }


    public void reproducirRitmoPropio(View view){
        findViewById(R.id.botonPlayRitmo).setEnabled(false);    findViewById(R.id.botonPlayRitmo).setAlpha(.5f);
        findViewById(R.id.botonPalmada).setEnabled(false);      findViewById(R.id.botonPalmada).setAlpha(.5f);
        findViewById(R.id.botonStopRitmo).setEnabled(false);    findViewById(R.id.botonStopRitmo).setAlpha(.5f);
        findViewById(R.id.botonCaja).setEnabled(false);         findViewById(R.id.botonCaja).setAlpha(.5f);
        findViewById(R.id.botonTambor).setEnabled(false);       findViewById(R.id.botonTambor).setAlpha(.5f);
        findViewById(R.id.botonPlatillo).setEnabled(false);     findViewById(R.id.botonPlatillo).setAlpha(.5f);
        if(runningPropio == true){
            indice = 0;
            play = true;
        }
        else {
            runningPropio = true;
            if(pause == false)
                indice = 0;
            pause = false;
        }
    }

    public void borrarRitmoPropio(View view){
        for (int i = 0; i < COMPASES; i++){
            resultado1.set(i,0);
            resultado2.set(i,0);
            resultado3.set(i,0);
            resultado4.set(i,0);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        running = false;
        runningPropio = false;
        if (indice == 0)
            indice = botonesGuia.size() - 1;

        botonesGuia.get(indice-1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
    }


    public void para(@NotNull final View view){
        findViewById(R.id.botonStopRitmo).setEnabled(false);       findViewById(R.id.botonStopRitmo).setAlpha(.5f);
        findViewById(R.id.botonPlayRitmoPropio).setEnabled(true);  findViewById(R.id.botonPlayRitmoPropio).setAlpha(1);
        findViewById(R.id.botonPalmada).setEnabled(false);         findViewById(R.id.botonPalmada).setAlpha(.5f);
        findViewById(R.id.botonCaja).setEnabled(false);            findViewById(R.id.botonCaja).setAlpha(.5f);
        findViewById(R.id.botonTambor).setEnabled(false);          findViewById(R.id.botonTambor).setAlpha(.5f);
        findViewById(R.id.botonPlatillo).setEnabled(false);        findViewById(R.id.botonPlatillo).setAlpha(.5f);
        running = false;
        if (indice == 0)
            indice = botonesGuia.size();
        botonesGuia.get(indice-1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
    }

    public void comprobar(View view){
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getNivel();
        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getRango()).ordinal();

        NivelAdivinar nivel = null;
        comprobado = true;
        if (compruebaArrays()){
            for (Button b : this.botonesGuia){
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
            if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Realiza_Ritmo.toString(), true);
            nivel  = new NivelAdivinar(ModoJuego.Realiza_Ritmo.getNombre(), this.nivel,true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0);

        }
        else{
            for (Button b : this.botonesGuia) {
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            }
            if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Realiza_Ritmo.toString(), false);
            nivel  = new NivelAdivinar(ModoJuego.Realiza_Ritmo.getNombre(), this.nivel,false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0, 1);

        }

        GestorBBDD.getInstance().insertaNivelAdivinar(nivel);

        view.setEnabled(false);
        view.setAlpha(0.5f);
        findViewById(R.id.botonPlayRitmo).setEnabled(false);        findViewById(R.id.botonPlayRitmo).setAlpha(.5f);
        findViewById(R.id.botonStopRitmo).setEnabled(false);        findViewById(R.id.botonStopRitmo).setAlpha(.5f);
        findViewById(R.id.botonPlayRitmoPropio).setEnabled(false);  findViewById(R.id.botonPlayRitmoPropio).setAlpha(.5f);
        findViewById(R.id.botonResetRitmo).setEnabled(false);       findViewById(R.id.botonResetRitmo).setAlpha(.5f);
        findViewById(R.id.botonPalmada).setEnabled(false);          findViewById(R.id.botonPalmada).setAlpha(.5f);
        findViewById(R.id.botonCaja).setEnabled(false);             findViewById(R.id.botonCaja).setAlpha(.5f);
        findViewById(R.id.botonTambor).setEnabled(false);           findViewById(R.id.botonTambor).setAlpha(.5f);
        findViewById(R.id.botonPlatillo).setEnabled(false);         findViewById(R.id.botonPlatillo).setAlpha(.5f);

        int nivelNuevo = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getNivel();
        int rangoNuevo = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getRango()).ordinal();
        if(rangoActual != rangoNuevo) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            RangosPuntuaciones.mostrar_popUp_rango(view, rangoActual, rangoNuevo, inflater, ModoJuego.Realiza_Ritmo.toString());
        }

        if(nivelActual != nivelNuevo){
            Controlador.getInstance().setNivel(nivelNuevo);
            Controlador.getInstance().estableceDificultad();
        }

        findViewById(R.id.continuar_cr).setEnabled(true);          findViewById(R.id.continuar_cr).setAlpha(1);


    }

    public void agregaFigura(int figura, ArrayList<Integer> ritmos, int compas){
        if(figura == 1){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }
        if(figura == 2){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }

        if (figura == 3){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }
        if(figura==0){
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()); i++){
                ritmos.add(0);
            }
        }


    }



    protected boolean compruebaArrays() {
        switch (nivel){
            case 1:
            case 2: return (ritmos1.equals(resultado1));
            case 3:
            case 4: return (ritmos1.equals(resultado1) && ritmos2.equals(resultado2));
            case 5:
            case 6: return (ritmos1.equals(resultado1) && ritmos2.equals(resultado2) && ritmos3.equals(resultado3));
            case 7:
            case 8: return (ritmos1.equals(resultado1) && ritmos2.equals(resultado2) && ritmos3.equals(resultado3) && ritmos4.equals(resultado4));
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        runningPropio = false;
        end = true;
        hiloPlayer1.interrupt();
        mediaPlayer1.stop();
        mediaPlayerMetronomo.stopMetronomo();
        if(nivel > 2) {
            mediaPlayer2.stop();
            if(nivel > 4) {
                mediaPlayer3.stop();
            }
            if(nivel > 6)
                mediaPlayer4.stop();
        }

    }

    public void registraPalmada(View view){
        resultado1.set(indiceSonidoActual,1);
    }

    public void registraCaja(View view){
        resultado2.set(indiceSonidoActual,1);
    }

    public void registraTambor(View view){
        resultado3.set(indiceSonidoActual,1);
    }


    public void registraPlatillo(View view){
        resultado4.set(indiceSonidoActual,1);
    }

    public void continuar(View view){
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);    }

}

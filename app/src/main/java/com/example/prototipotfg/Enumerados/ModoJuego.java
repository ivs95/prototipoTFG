package com.example.prototipotfg.Enumerados;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;

import java.util.HashMap;

public enum ModoJuego {
    Adivinar_Intervalo("Adivinar_Intervalo", 6, new HashMap<Integer, Spanned>()),
    Adivinar_Notas("Adivinar_Notas", 10, new HashMap<Integer, Spanned>()),
    Adivinar_Acordes("Adivinar_Acordes", 6, new HashMap<Integer, Spanned>()),
    Crear_Intervalo("Crear_Intervalo", 8, new HashMap<Integer, Spanned>()),
    Halla_Ritmo("Halla_Ritmo", 8, new HashMap<Integer, Spanned>()),
    Realiza_Ritmo("Realiza_Ritmo", 8, new HashMap<Integer, Spanned>()),
    Crear_Acordes("Crear_Acordes", 6, new HashMap<Integer, Spanned>()),
    Imitar_Audio("Imitar_Audio", 3, new HashMap<Integer, Spanned>());

    private String nombre;
    private int max_level;
    private HashMap<Integer, Spanned> texto;


    ModoJuego(String nombre, int max_level, HashMap<Integer, Spanned> texto){
        this.nombre=nombre;
        this.max_level = max_level;
        this.texto = texto;
    }

    public String getNombre(){
        return this.nombre;
    }

    public Spanned getTextDadoNivel(int nivel){
        return texto.get(nivel);
    }

    public int getMax_level(){ return this.max_level;}

    public void rellena_cambios(String modoJuego, Context context){
        switch(modoJuego){
            case "Adivinar_Notas": {
                texto.put(2, Html.fromHtml(context.getString(R.string.cambiosNv2AdNotas)));
                texto.put(3, Html.fromHtml(context.getString(R.string.cambiosNv3AdNotas)));
                texto.put(4, Html.fromHtml(context.getString(R.string.cambiosNv4AdNotas)));
                texto.put(5, Html.fromHtml(context.getString(R.string.cambiosNv5AdNotas)));
                texto.put(6, Html.fromHtml(context.getString(R.string.cambiosNv6AdNotas)));
                texto.put(7, Html.fromHtml(context.getString(R.string.cambiosNv7AdNotas)));
                texto.put(8, Html.fromHtml(context.getString(R.string.cambiosNv8AdNotas)));
                texto.put(9, Html.fromHtml(context.getString(R.string.cambiosNv9AdNotas)));
                texto.put(10, Html.fromHtml(context.getString(R.string.cambiosNv10AdNotas)));
                break;
            }

            case "Adivinar_Intervalo":{
                texto.put(2, Html.fromHtml(context.getString(R.string.cambiosNv2AdIntervalos)));
                texto.put(3, Html.fromHtml(context.getString(R.string.cambiosNv3AdIntervalos)));
                texto.put(4, Html.fromHtml(context.getString(R.string.cambiosNv4AdIntervalos)));
                texto.put(5, Html.fromHtml(context.getString(R.string.cambiosNv5AdIntervalos)));
                texto.put(6, Html.fromHtml(context.getString(R.string.cambiosNv6AdIntervalos))); break;
            }

            case "Crear_Intervalo":{
                texto.put(2, Html.fromHtml(context.getString(R.string.cambiosNv2CrearIntervalos)));
                texto.put(3, Html.fromHtml(context.getString(R.string.cambiosNv3CrearIntervalos)));
                texto.put(4, Html.fromHtml(context.getString(R.string.cambiosNv4CrearIntervalos)));
                texto.put(5, Html.fromHtml(context.getString(R.string.cambiosNv5CrearIntervalos)));
                texto.put(6, Html.fromHtml(context.getString(R.string.cambiosNv6CrearIntervalos)));
                texto.put(7, Html.fromHtml(context.getString(R.string.cambiosNv7CrearIntervalos)));
                texto.put(8, Html.fromHtml(context.getString(R.string.cambiosNv8CrearIntervalos)));break;

            }
            case "Adivinar_Acordes":{
                texto.put(2, Html.fromHtml(context.getString(R.string.cambiosNv2AdAcordes)));
                texto.put(3, Html.fromHtml(context.getString(R.string.cambiosNv3AdAcordes)));
                texto.put(4, Html.fromHtml(context.getString(R.string.cambiosNv4AdAcordes)));
                texto.put(5, Html.fromHtml(context.getString(R.string.cambiosNv5AdAcordes)));
                texto.put(6, Html.fromHtml(context.getString(R.string.cambiosNv6AdAcordes))); break;

            }
            case "Crear_Acordes":{
                texto.put(2, Html.fromHtml(context.getString(R.string.cambiosNv2CrearAcordes)));
                texto.put(3, Html.fromHtml(context.getString(R.string.cambiosNv3CrearAcordes)));
                texto.put(4, Html.fromHtml(context.getString(R.string.cambiosNv4CrearAcordes)));
                texto.put(5, Html.fromHtml(context.getString(R.string.cambiosNv5CrearAcordes)));
                texto.put(6, Html.fromHtml(context.getString(R.string.cambiosNv6CrearAcordes))); break;

            }
            case "Halla_Ritmo":{
                texto.put(2, Html.fromHtml(context.getString(R.string.cambiosNv2HallaRitmos)));
                texto.put(3, Html.fromHtml(context.getString(R.string.cambiosNv3HallaRitmos)));
                texto.put(4, Html.fromHtml(context.getString(R.string.cambiosNv4HallaRitmos)));
                texto.put(5, Html.fromHtml(context.getString(R.string.cambiosNv5HallaRitmos)));
                texto.put(6, Html.fromHtml(context.getString(R.string.cambiosNv6HallaRitmos)));
                texto.put(7, Html.fromHtml(context.getString(R.string.cambiosNv7HallaRitmos)));
                texto.put(8, Html.fromHtml(context.getString(R.string.cambiosNv8HallaRitmos)));break;

            }
            case "Realiza_Ritmo":{
                texto.put(2, Html.fromHtml(context.getString(R.string.cambiosNv2RealizaRitmos)));
                texto.put(3, Html.fromHtml(context.getString(R.string.cambiosNv3RealizaRitmos)));
                texto.put(4, Html.fromHtml(context.getString(R.string.cambiosNv4RealizaRitmos)));
                texto.put(5, Html.fromHtml(context.getString(R.string.cambiosNv5RealizaRitmos)));
                texto.put(6, Html.fromHtml(context.getString(R.string.cambiosNv6RealizaRitmos)));
                texto.put(7, Html.fromHtml(context.getString(R.string.cambiosNv7RealizaRitmos)));
                texto.put(8, Html.fromHtml(context.getString(R.string.cambiosNv8RealizaRitmos)));break;

            }
            default:break;
            }
    }

    public static void mostrarPopUpNuevoNivel(LayoutInflater inflater, ModoJuego modoJuego, final View view){

        View popupView = inflater.inflate(R.layout.popup_nuevo_nivel_cambios, null);
        final View popupView2 = inflater.inflate(R.layout.popup_nuevo_nivel_cambios2, null);

        TextView cambios = (TextView) popupView2.findViewById(R.id.cambios_nivel_text);
        cambios.setText(modoJuego.getTextDadoNivel(Controlador.getInstance().getNivel()));

        TextView nivel_text = (TextView)popupView.findViewById(R.id.nivel_text_popup);
        nivel_text.setText(String.valueOf(Controlador.getInstance().getNivel()));



        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);
        final PopupWindow popupWindow2 = new PopupWindow(popupView2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken

        view.post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        // popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                popupWindow2.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.dismiss();

                return true;
            }
        });

        popupView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow2.dismiss();
                return true;
            }
        });

    }
}

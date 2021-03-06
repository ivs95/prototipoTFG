package com.example.prototipotfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipotfg.Singletons.GestorBBDD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (GestorBBDD.getInstance().esPrimeraVezApp())
            mostrarPopupRangos(findViewById(android.R.id.content).getRootView());
    }

    public void ejecutar_jugar(View view) {
        Intent i = new Intent(this, MenuJugar.class);
        startActivity(i);
    }

    public void ejecutar_Perfil(View view) {
        if (!GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo().equals("usuario@prueba.com")) {
            Intent i = new Intent(this, Perfil.class);
            startActivity(i);
        } else
            Toast.makeText(this, "No puedes editar el usuario de prueba", Toast.LENGTH_SHORT).show();
    }

    public void mostrarEstadisticas(View view) {
        Intent i = new Intent(this, Estadisticas.class);
        startActivity(i);
    }

    public void teoriaMusical(View view) {
        Intent i = new Intent(this, Teoria.class);
        startActivity(i);
    }

    public void tutoriales(View view) {
        Intent i = new Intent(this, Tutoriales.class);
        startActivity(i);
    }

    protected void onStop() {
        super.onStop();

    }

    public void informacion(View view) {
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView());
    }

    public void salir(View view) {
        GestorBBDD.getInstance().cerrarSesion();
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    public void mostrarPopupRangos(final View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        final View popupView = inflater.inflate(R.layout.popup_tutorial_rangos, null);
        final View popupView2 = inflater.inflate(R.layout.popup_tutorial_rangos2, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        final PopupWindow popupWindow2 = new PopupWindow(popupView2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        view.post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        Button cerrar = popupView.findViewById(R.id.boton_cerrar_tutorialrangos);
        cerrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow2.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.dismiss();
            }
        });

        Button cerrar2 = popupView2.findViewById(R.id.boton_cerrar_tutorial_2);
        cerrar2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow2.dismiss();
            }
        });
    }

    public void mostrarPopupTutorial(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.informacion_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        findViewById(R.id.linearLayout).post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(findViewById(R.id.linearLayout), Gravity.CENTER, 0, 0);
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}

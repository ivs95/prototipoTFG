package com.example.prototipotfg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class ReproducirImitar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_imitar);

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloImitar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        Button repetirNivel = (Button)findViewById(R.id.botonRepite);
        repetirNivel.setEnabled(false);
        repetirNivel.setVisibility(View.INVISIBLE);



        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        is_running = false;
        AudioDispatcherFactory1 factory = new AudioDispatcherFactory1();
        dispatcher = factory.fromDefaultMicrophone(22050,1024,0);

        int id = factory.getAudioRecord().getAudioSessionId();

        if(AcousticEchoCanceler.isAvailable()) {
            AcousticEchoCanceler echo = AcousticEchoCanceler.create(id);
            echo.setEnabled(true);
            Log.d("Echo", "Off");
        }

        if(NoiseSuppressor.isAvailable()) {
            NoiseSuppressor noise = NoiseSuppressor.create(id);
            noise.setEnabled(true);
            Log.d("Noise", "Off");
        }
        if(AutomaticGainControl.isAvailable()) {
            AutomaticGainControl gain = AutomaticGainControl.create(id);
            gain.setEnabled(false);
            Log.d("Gain", "Off");
        }

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final float pitchInHz = result.getPitch();
                ReproducirImitar.Notas nota = ReproducirImitar.Notas.Z;
                int octava = 0;
                final String resul = hallaNota(pitchInHz, nota,octava);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TextView text = (TextView) findViewById(R.id.textoFrecuencia);
                        text.setText("" + pitchInHz + "    " + resul);

                        int i = 5;
                        if(System.currentTimeMillis() - start_time/ 1000 == 0) {

                            TextView text1 = (TextView) findViewById(R.id.timer_id);
                            text1.setText("" + (System.currentTimeMillis() - start_time));

                        }
                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);


    }

    public void reproducir(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.VISIBLE);
        progresoGrabar.setVisibility(View.INVISIBLE);


    }

    public void grabar(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.INVISIBLE);
        progresoGrabar.setVisibility(View.VISIBLE);

    }

    public void comparar(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.INVISIBLE);
        progresoGrabar.setVisibility(View.INVISIBLE);
    }




    private AudioDispatcher dispatcher;

    enum Notas{Z,DO,DOS,RE,RES,MI,FA,FAS,SOL,SOLS,LA,LAS,SI}
    private boolean is_running;
    private long start_time;

    String hallaNota(float hz, Notas nota, int octava){
        String resul = "";

        while(hz > 62.74){
            hz /= 2;
            octava++;
        }
        if(hz >= 60.74 && hz <= 62.74){
            nota = Notas.SI;
        }
        if(hz >= 57.27 && hz <= 59.27){
            nota = ReproducirImitar.Notas.LAS;
        }
        if(hz >= 54 && hz <= 56){
            nota = ReproducirImitar.Notas.LA;
        }
        if(hz >= 50.91 && hz <= 52.91){
            nota = ReproducirImitar.Notas.SOLS;
        }
        if(hz >= 48 && hz <= 50){
            nota = ReproducirImitar.Notas.SOL;
        }
        if(hz >= 45.25 && hz <= 47.25){
            nota = ReproducirImitar.Notas.FAS;
        }
        if(hz >= 42.65 && hz <= 44.65){
            nota = ReproducirImitar.Notas.FA;
        }
        if(hz >= 40.2 && hz <= 42.2){
            nota = ReproducirImitar.Notas.MI;
        }
        if(hz >= 37.89 && hz <= 39.89){
            nota = ReproducirImitar.Notas.RES;
        }
        if(hz >= 35.71 && hz <= 37.71){
            nota = ReproducirImitar.Notas.RE;
        }
        if(hz >= 33.71 && hz <= 35.65){
            nota = ReproducirImitar.Notas.DOS;
        }
        if(hz >= 31.7 && hz <= 33.7){
            nota = ReproducirImitar.Notas.DO;
        }
        resul = nota.toString()+octava;

        return resul;
    }

    public void Grabar(View view) throws InterruptedException {
        Button botonGrabar = (Button)findViewById(R.id.botonGrabar);
        botonGrabar.setVisibility(View.INVISIBLE);
        botonGrabar.setEnabled(false);
        Button repetirNivel = (Button)findViewById(R.id.botonRepite);
        repetirNivel.setVisibility(View.VISIBLE);
        repetirNivel.setEnabled(true);
        class MiContador extends CountDownTimer {

            public MiContador(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onFinish() {

                final Thread dispatch_Thread = new Thread(dispatcher,"Audio Dispatcher");
                Toast.makeText(getApplicationContext(), "La grabación comenzó", Toast.LENGTH_LONG).show();
                is_running = true;
                dispatch_Thread.start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dispatcher.stop();
                        dispatch_Thread.interrupt();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView text2 = (TextView) findViewById(R.id.textoFrecuencia);
                                text2.setText("Pitch");

                                TextView text1 = (TextView) findViewById(R.id.timer_id);
                                text1.setText("Fin");

                            }

                        });

                        return;

                    }
                }).start();

            }

            @Override
            public void onTick(long millisUntilFinished) {
                //texto a mostrar en cuenta regresiva en un textview
                TextView countdownText = (TextView) findViewById(R.id.textoFrecuencia);
                countdownText.setText((((millisUntilFinished+1000)/1000)+""));
            }
        }
        final MiContador timer = new MiContador(3000,1000);
        timer.start();

    }

    public void repetirNivel(View view){
        startActivity(getIntent());
    }

}
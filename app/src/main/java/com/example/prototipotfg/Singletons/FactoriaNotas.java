package com.example.prototipotfg.Singletons;


import android.util.Pair;

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosVocales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.min;

public final class FactoriaNotas {

    private static final FactoriaNotas INSTANCE = new FactoriaNotas();
    private Random random = new Random();
    private Instrumentos instrumento = Instrumentos.Piano;
    private String rutaReferencia;
    private String rutaReferenciaDo;


    public static FactoriaNotas getInstance() {
        return INSTANCE;
    }

    public String getReferencia(){
        return rutaReferencia;
    }

    private FactoriaNotas() {}

    public String  getReferenciaDo(){ return rutaReferenciaDo;}

    private void setReferencia(String ruta){
        this.rutaReferencia=ruta;
    }

    public void setReferencia(Octavas o){
        this.rutaReferencia=instrumento.getPath()+o.getPath()+Notas.LA.getPath();
    }

    public void setReferenciaDo(Octavas o){
        this.rutaReferenciaDo=instrumento.getPath()+o.getPath()+Notas.DO.getPath();
    }


    public Instrumentos getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumentos instrumento) {
        this.instrumento = instrumento;
    }

    public HashMap<String,String> getNumNotasAleatorias(int numeroNotas, Instrumentos instrumento, ArrayList<Octavas> octavas) {
        /*
        * Funcion que devuelve num notas aleatorias en un array
        * La primera posicion del array se utilizara como la nota a adivinar
        * */
        String rutaInstrumento = instrumento.getPath();
        setReferencia(instrumento.getPath()+"cuatro/A.wav");
        setInstrumento(instrumento);

        //HashMap con clave nombre de nota y su octava y valor el path a su fichero
        HashMap<String,String> rutasFicherosAudio = new HashMap<>();
        Octavas octava = devuelveOctavaAleatoria(octavas);
        Notas nota = devuelveNotaAleatoria(Notas.values() ,0, 0, false);
        int tonoNotaAnt = getTonoNota(nota.getNombre());
        String ruta = rutaInstrumento + octava.getPath() + nota.getPath();
        rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), ruta);
        for (int i = 1 ; i < numeroNotas; i++){
            while (rutasFicherosAudio.containsKey(nota.getNombre()+octava.getOctava())){
                if(nota == Notas.SI) nota = devuelveNotaAleatoria(Notas.values(), i, tonoNotaAnt, true);
                else nota = devuelveNotaAleatoria(Notas.values(), i, tonoNotaAnt, false);
                tonoNotaAnt = getTonoNota(nota.getNombre());


            }
            rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), rutaInstrumento+octava.getPath()+nota.getPath());
        }
        return rutasFicherosAudio;
    }

    public Pair<Notas,Octavas> devuelveNotaCompletaIntervalo(Notas n, Octavas o, Intervalos i){
        int tono = n.getTono() + i.getDiferencia();
        Notas notaRetorno;
        Octavas octavaRetorno = o;
        if (tono < 1) {
            notaRetorno = Notas.devuelveNotaPorTono(tono + 12);
            octavaRetorno = o.devuelveAnteriorOctava(o);
        }
        else if (tono > 12){
            notaRetorno = Notas.devuelveNotaPorTono(tono - 12);
            octavaRetorno = o.devuelveSiguienteOctava(o);
        }
        else{
            notaRetorno = Notas.devuelveNotaPorTono(tono);
        }
        return new Pair<>(notaRetorno,octavaRetorno);
    }

    public HashMap<String,String> getNumNotasAleatorias(int numeroNotas, Instrumentos instrumento, Octavas octava) {
        /*
         * Funcion que devuelve num notas aleatorias en un array
         * La primera posicion del array se utilizara como la nota a adivinar
         */
        String rutaInstrumento = instrumento.getPath();
        setReferencia(instrumento.getPath()+"cuatro/A.wav");
        setInstrumento(instrumento);

        //HashMap con clave nombre de nota y su octava y valor el path a su fichero
        HashMap<String,String> rutasFicherosAudio = new HashMap<>();
        Notas nota = devuelveNotaAleatoria(Notas.values(), 0, 0, false);

        int tonoNotaAnt = getTonoNota(nota.getNombre());

        String ruta = rutaInstrumento+octava.getPath()+nota.getPath();
        rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), ruta);
        for (int i = 1 ; i < numeroNotas; i++){
            while (rutasFicherosAudio.containsKey(nota.getNombre()+octava.getOctava())){
                nota = devuelveNotaAleatoria(Notas.values(), i, tonoNotaAnt, false);
            }
            rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), rutaInstrumento+octava.getPath()+nota.getPath());
        }
        return rutasFicherosAudio;
    }
    //0, 1, , 2 --> 8, 9, 10

    private Notas devuelveNotaAleatoria(Notas[] notas, int i, int tonoNotaAnt, boolean ultNota) {
        if((Controlador.getInstance().getModo_juego() == ModoJuego.Adivinar_Intervalo || Controlador.getInstance().getModo_juego() == ModoJuego.Crear_Intervalo)&& i == 1 && !ultNota){
            return notas[random.nextInt((min((notas.length-tonoNotaAnt), (Controlador.getInstance().getRango()))))+ tonoNotaAnt];
        }

         else return notas[random.nextInt(notas.length)];
    }

    private Octavas devuelveOctavaAleatoria(ArrayList<Octavas> octavas) {
        return octavas.get(random.nextInt(octavas.size()));
    }

    private int getTonoNota(String name){
        boolean OK = false;
        int i = 0;
        Notas[] lista_notas;
        lista_notas = Notas.values();
        while(i < 11 && !OK){
            if(lista_notas[i].getNombre().equals(name)) OK = true;
            i++;

        }
        return lista_notas[i-1].getTono();
    }

    public Notas getNotaInicioIntervalo(Instrumentos piano, Octavas octavaInicio) {
        return Notas.values()[new Random().nextInt(Notas.values().length)];
    }

    public ArrayList<Pair<Notas,Octavas>> getNotasIntervalo(ArrayList<Octavas> octavas, int rango) {
        ArrayList<Pair<Notas,Octavas>> retorno = new ArrayList<>();
        Octavas o = devuelveOctavaAleatoria(octavas);
        Pair<Notas,Octavas> par = new Pair(getNotaInicioIntervalo(getInstrumento(), o),o);
        retorno.add(par);
        while (retorno.contains(par) || Math.abs(par.first.getTono()-retorno.get(0).first.getTono())>rango){
            par = new Pair(getNotaInicioIntervalo(getInstrumento(), o),o);
        }
        retorno.add(par);
        return retorno;

    }

    public HashMap<String,String> getNotasRV(RangosVocales rv, int n, Instrumentos instrumento){
        HashMap<String,String> resul = new HashMap<String,String>();
        Random r = new Random();
        int añadidas = 0;
        while(añadidas < n) {
            Octavas octava = Octavas.devuelveOctavaPorNumero(r.nextInt(rv.getOctavaFin() - rv.getOctavaIni() + 1) + rv.getOctavaIni());
            if (octava.getOctava() == rv.getOctavaFin()) {
                Notas nota = Notas.devuelveNotaPorTono(r.nextInt(rv.getTonoFin()-1+1)+1);
                resul.put(nota.getNombre()+octava.getOctava(),instrumento.getPath()+octava.getPath()+nota.getPath());
            } else if (octava.getOctava() == rv.getOctavaIni()) {
                Notas nota = Notas.devuelveNotaPorTono(r.nextInt(12-rv.getTonoIni()+1)+rv.getTonoIni());
                resul.put(nota.getNombre()+octava.getOctava(),instrumento.getPath()+octava.getPath()+nota.getPath());
            } else {
                Notas nota = Notas.devuelveNotaPorTono(r.nextInt(12-1+1)+1);
                resul.put(nota.getNombre()+octava.getOctava(),instrumento.getPath()+octava.getPath()+nota.getPath());
            }
            añadidas++;
        }
        return resul;
    }
}

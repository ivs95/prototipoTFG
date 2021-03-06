package com.example.prototipotfg.Enumerados;

import java.util.Arrays;

public enum Octavas {

    Primera("Primera","uno/", 1),
    Segunda("Segunda","dos/", 2),
    Tercera("Tercera","tres/", 3),
    Cuarta("Cuarta","cuatro/", 4),
    Quinta("Quinta","cinco/", 5),
    Sexta("Sexta","seis/", 6),
    Septima("Septima","siete/", 7);


    private String nombre;
    private String path;
    private int octava;

    Octavas(String nombre, String path,int octava){
        this.nombre=nombre;
        this.path=path;
        this.octava=octava;
    }

    public static Octavas devuelveSiguienteOctava(Octavas octava) {
        int ss = octava.devuelveIndiceOctava(octava);
        if(octava.devuelveIndiceOctava(octava) < 6){
            int indice = Arrays.asList(Octavas.values()).indexOf(octava)+1;
            return Octavas.values()[indice];
        }
        else return null;
    }

    public static Octavas devuelveAnteriorOctava(Octavas octava) {
        if(octava.devuelveIndiceOctava(octava) > 0) {
            int indice = Arrays.asList(Octavas.values()).indexOf(octava) - 1;
            return Octavas.values()[indice];
        }
        else return null;
    }

    public int devuelveIndiceOctava(Octavas o){
        return Arrays.asList(Octavas.values()).indexOf(o);
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }


    public int getOctava() {
        return octava;
    }

    public void setOctava(int octava) {
        this.octava = octava;
    }

    public static Octavas devuelveOctavaPorNombre(String nombre){
        for (Octavas o: Octavas.values()
        ) {
            if (o.getNombre().equals(nombre)) return o;
        }
        return null;
    }

    public static Octavas devuelveOctavaPorNumero(int numero){
        for (Octavas o: Octavas.values()
        ) {
            if (o.getOctava()== numero) return o;
        }
        return null;
    }


}

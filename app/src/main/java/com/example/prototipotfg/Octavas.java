package com.example.prototipotfg;

public enum Octavas {

    Primera("Primera","uno/", 1),
    Segunda("Segunda","dos/", 2),
    Tercera("Tercera","tres/", 3);
    /*Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),*/


    private String nombre;
    private String path;
    private int octava;

    private Octavas(String nombre, String path,int octava){
        this.nombre=nombre;
        this.path=path;
        this.octava=octava;
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

    public void setPath(String path) {
        this.path = path;
    }

    public int getOctava() {
        return octava;
    }

    public void setOctava(int octava) {
        this.octava = octava;
    }
}
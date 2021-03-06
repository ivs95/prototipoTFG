package com.example.prototipotfg.BBDD.Entidades;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"correoUsuario", "modoJuego", "nivel"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "correo", childColumns = "correoUsuario"))
public class NivelAdivinar {
    @NonNull
    private String modoJuego;
    @NonNull
    private int nivel;
    private boolean superado;
    @NonNull
    private String correoUsuario;
    private int numAciertos;
    private int numFallos;

    public NivelAdivinar(@NonNull String modoJuego, @NonNull int nivel, boolean superado, @NonNull String correoUsuario, int numAciertos, int numFallos) {
        this.modoJuego = modoJuego;
        this.nivel = nivel;
        this.superado = superado;
        this.correoUsuario = correoUsuario;
        this.numAciertos = numAciertos;
        this.numFallos = numFallos;
    }

    public int getNumAciertos() {
        return numAciertos;
    }

    public int getNumFallos() {
        return numFallos;
    }

    public String getModoJuego() {
        return modoJuego;
    }

    public void setModoJuego(String modoJuego) {
        this.modoJuego = modoJuego;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Boolean getSuperado() {
        return superado;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void actualizaEstadisticas(boolean superado) {
        if (superado) {
            this.numAciertos++;
            this.superado = superado;
        } else {
            this.numFallos++;
        }
    }
}

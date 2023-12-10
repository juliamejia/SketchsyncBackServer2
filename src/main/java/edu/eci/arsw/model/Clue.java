package edu.eci.arsw.model;

public class Clue {

    private String contenido;
    private boolean tomada;

    /*Crea la pista*/
    public Clue(){}

    public Clue(String contenido, boolean tomada){
        this.contenido = contenido;
        this.tomada = tomada;
    }

    public boolean getTomada(){
        return tomada;
    }

    public void setTomada(boolean ganadorPista){
        this.tomada = ganadorPista;
    }

    public String getContenido(){
        return contenido;
    }

    public void setContenido(String contenido){
        this.contenido = contenido;
    }
}

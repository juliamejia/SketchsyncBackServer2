package edu.eci.arsw.persistence;

public class SketchsyncPersistenceException extends Exception{

    public static final String NO_USER = "No se ha encontrado el usuario dentro de la partida";

    public SketchsyncPersistenceException(String message){
        super(message);
    }

    public SketchsyncPersistenceException(String message, Throwable cause){
        super(message,cause);
    }
}

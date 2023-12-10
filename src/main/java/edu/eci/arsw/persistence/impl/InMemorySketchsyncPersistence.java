package edu.eci.arsw.persistence.impl;

import edu.eci.arsw.persistence.SketchsyncPersistence;
import edu.eci.arsw.persistence.SketchsyncPersistenceException;
import edu.eci.arsw.model.Clue;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemorySketchsyncPersistence implements SketchsyncPersistence {

    private final Map<String, User> participantes = new ConcurrentHashMap<>();

    private User masterName = null;
    private Clue nuevaClue = new Clue();

    public InMemorySketchsyncPersistence(){
    }


    @Override
    public void saveUser(User user) {
        if(!participantes.containsKey(user.getName())){
            if(user.getName().contains("Organizer")){
                masterName = user;
            }else{
                participantes.put(user.getName(), user);
            }

        }
    }

    @Override
    public User getUser(String name) throws SketchsyncPersistenceException {
        Set<String> keys = participantes.keySet();
        if(!participantes.containsKey(name)){
            throw new SketchsyncPersistenceException(SketchsyncPersistenceException.NO_USER);
        }
        return participantes.get(name);
    }

    @Override
    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<>();
        Set<String> keys = participantes.keySet();

        for (String name: keys){
            users.add(participantes.get(name));
        }
        return users;
    }

    @Override
    public ArrayList<Point> getPointsByUser(String name) {
        return participantes.get(name).getPoints();
    }

    @Override
    public void addPointToUser(User user) {
        participantes.get(user.getName()).addPoint(user.getPoints().get(0));
    }

    @Override
    public void delteAllPointsUser(String name) {
        participantes.get(name).deletePoints();
    }

    @Override
    public User getOrganizerName() {
        return masterName;
    }

    @Override
    public User getWinner() {
        Set<User> users = new HashSet<>();
        Set<String> keys = participantes.keySet();
        User ganador = null;

        for (String name: keys){
            if(participantes.get(name).isGanador()){
                ganador = participantes.get(name);
            }
        }
        return ganador;
    }

    @Override
    public void setWinner(String name) {
        participantes.get(name).setGanador(true);
    }

    @Override
    public void deleteParticipantes() {
        participantes.clear();
    }

    @Override
    public void saveClue(Clue clue) throws SketchsyncPersistenceException {
        nuevaClue = new Clue(clue.getContenido(), clue.getTomada());
    }

    @Override
    public String TakeClue(){
        synchronized (nuevaClue){

            if (!nuevaClue.getTomada()){
                nuevaClue.setTomada(true);
                return nuevaClue.getContenido();
            }else {
                return "Pista no disponible!";
            }
        }
    }

}
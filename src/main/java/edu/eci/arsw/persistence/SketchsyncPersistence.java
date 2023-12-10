package edu.eci.arsw.persistence;

import edu.eci.arsw.model.Clue;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public interface SketchsyncPersistence {

    public void saveUser(User user);

    public User getUser(String name) throws SketchsyncPersistenceException;

    public Set<User> getAllUsers();

    public ArrayList<Point> getPointsByUser(String name);

    public void addPointToUser(User user);

    public void delteAllPointsUser(String name);

    public  User getOrganizerName();

    public User getWinner();

    public void setWinner(String name);

    public void deleteParticipantes();

    public void saveClue(Clue clue) throws SketchsyncPersistenceException;

    public String TakeClue();
}

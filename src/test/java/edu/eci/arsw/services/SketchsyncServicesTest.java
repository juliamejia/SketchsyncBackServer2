package edu.eci.arsw.services;

import edu.eci.arsw.model.Clue;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.model.User;


import edu.eci.arsw.persistence.SketchsyncPersistenceException;
import edu.eci.arsw.persistence.impl.InMemorySketchsyncPersistence;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class SketchsyncServicesTest {
    InMemorySketchsyncPersistence dp;
    User julia, cristian, diego, result;

    @Before
    public void before(){
        dp = new InMemorySketchsyncPersistence();
    }

    @Test
    public void newMaster(){
        ArrayList<Point> testPoints  = new ArrayList();
        julia = new User("testUserOrganizer", testPoints);
        dp.saveUser(julia);
        assertEquals(julia, dp.getOrganizerName());
    }

    @Test
    public void noSaveMaster(){
        ArrayList<Point> testPoints  = new ArrayList();
        julia = new User("testUser", testPoints);
        dp.saveUser(julia);
        assertEquals(null, dp.getOrganizerName());
    }

    @Test
    public void saveUser() throws SketchsyncPersistenceException {
        ArrayList<Point> testPoints  = new ArrayList();
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(5, 6);
        testPoints.add(p1);
        testPoints.add(p2);
        testPoints.add(p3);
        julia = new User("testUser", testPoints);
        dp.saveUser(julia);
        result = dp.getUser("testUser");
        assertEquals(julia, result);
    }

    @Test
    public void saveAllUser(){
        ArrayList<Point> testPoints  = new ArrayList();
        julia = new User("testUser1", testPoints);
        cristian = new User("testUser2", testPoints);
        diego = new User("testUser3", testPoints);
        dp.saveUser(julia);
        dp.saveUser(cristian);
        dp.saveUser(diego);
        Set<User> usuarios = dp.getAllUsers();
        assertEquals(usuarios.size(), 3);
    }

    @Test
    public void deleteUser(){
        ArrayList<Point> testPoints  = new ArrayList();
        julia = new User("testUser1", testPoints);
        cristian = new User("testUser2", testPoints);
        diego = new User("testUser3", testPoints);
        dp.saveUser(julia);
        dp.saveUser(cristian);
        dp.saveUser(diego);
        dp.deleteParticipantes();
        assertEquals(0, dp.getAllUsers().size());
    }

    @Test
    public void noSaveUserTwice() throws SketchsyncPersistenceException {
        ArrayList<Point> testPoints1  = new ArrayList();
        Point p1 = new Point(1, 2);
        ArrayList<Point> testPoints2  = new ArrayList();
        Point p2 = new Point(3, 4);
        testPoints1.add(p1);
        testPoints2.add(p2);
        julia = new User("testUser", testPoints1);
        cristian = new User("testUser", testPoints2);
        dp.saveUser(julia);
        dp.saveUser(cristian);
        result = dp.getUser("testUser");
        assertNotEquals(cristian, result);
    }

    @Test
    public void getPoints() throws SketchsyncPersistenceException {
        ArrayList<Point> testPoints  = new ArrayList();
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(5, 6);
        testPoints.add(p1);
        testPoints.add(p2);
        testPoints.add(p3);
        julia = new User("testUser", testPoints);
        dp.saveUser(julia);
        result = dp.getUser("testUser");
        assertEquals(testPoints, result.getPoints());
    }

    @Test
    public void getUserPoints(){
        ArrayList<Point> testPoints1  = new ArrayList();
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        testPoints1.add(p1);
        testPoints1.add(p2);
        ArrayList<Point> testPoints2  = new ArrayList();
        Point p3 = new Point(5, 6);
        Point p4 = new Point(7, 8);
        testPoints2.add(p3);
        testPoints2.add(p4);
        julia = new User("testUser1", testPoints1);
        cristian = new User("testUser2", testPoints2);
        dp.saveUser(julia);
        dp.saveUser(cristian);
        assertEquals(testPoints2, dp.getPointsByUser("testUser2"));
    }

    @Test
    public void savePoint(){
        ArrayList<Point> testPoints  = new ArrayList();
        julia = new User("testUser", testPoints);
        dp.saveUser(julia);
        Point p1 = new Point(1, 2);
        testPoints.add(p1);
        julia = new User("testUser", testPoints);
        dp.addPointToUser(julia);
        assertEquals(testPoints, dp.getPointsByUser("testUser"));
    }

    @Test
    public void deleteAllPoints() throws SketchsyncPersistenceException {
        ArrayList<Point> testPoints  = new ArrayList();
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(5, 6);
        testPoints.add(p1);
        testPoints.add(p2);
        testPoints.add(p3);
        julia = new User("testUser", testPoints);
        dp.saveUser(julia);
        dp.delteAllPointsUser("testUser");
        assertEquals(0, dp.getUser("testUser").getPoints().size());
    }

    @Test
    public void returnWinner(){
        ArrayList<Point> testPoints  = new ArrayList();
        julia = new User("testUser", testPoints);
        dp.saveUser(julia);
        dp.setWinner("testUser");
        assertEquals(julia, dp.getWinner());
    }

    @Test
    public void newClue() throws SketchsyncPersistenceException {
        Clue pist = new Clue("good game", false);
        dp.saveClue(pist);
        assertEquals("good game", pist.getContenido());
    }

    @Test
    public void takeClue() throws SketchsyncPersistenceException {
        Clue pist = new Clue("good game", false);
        dp.saveClue(pist);
        assertEquals("good game", dp.TakeClue());
    }

    @Test
    public void onlyOneWinner(){
        ArrayList<Point> testPoints  = new ArrayList();
        julia = new User("testUser1", testPoints);
        dp.saveUser(julia);
        dp.setWinner("testUser1");
        cristian = new User("testUser2", testPoints);
        dp.saveUser(cristian);
        dp.setWinner("testUser2");
        assertNotEquals(cristian, dp.getWinner());
    }

    @Test
    public void changeClue() throws SketchsyncPersistenceException {
        Clue pist1 = new Clue("good game", false);
        dp.saveClue(pist1);
        Clue pist2 = new Clue("bad game", false);
        dp.saveClue(pist2);
        assertEquals("bad game", dp.TakeClue());
    }
}
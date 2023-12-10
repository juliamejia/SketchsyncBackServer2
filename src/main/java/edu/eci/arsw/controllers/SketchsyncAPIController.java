package edu.eci.arsw.controllers;

import com.google.gson.Gson;
import edu.eci.arsw.model.Clue;
import edu.eci.arsw.model.User;
import edu.eci.arsw.persistence.SketchsyncPersistenceException;
import edu.eci.arsw.services.SketchsyncServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="/Sketchsync")
public class SketchsyncAPIController {

    @Autowired
    SketchsyncServices ds = null;

    @RequestMapping(path = "/OrganizerName/OrganizerName" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrganizerName() {
        User Organizer = null;
        try {
            Organizer = ds.getOrganizerName();
        } catch (Exception e) {
            return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
        }
        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(Organizer), HttpStatus.ACCEPTED);

    }

    @RequestMapping(path = "/{name}" , method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setWinner(@PathVariable String name) {
        try {
            ds.setWinner(name);
        } catch (Exception e) {
            return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @RequestMapping(path= "/clean", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteParticipantes() {
        try {
            ds.deleteParticipantes();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @RequestMapping(path = "/{name}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBlueprintByAuthorName(@PathVariable String name) {
        User user = null;
        try {
            user = ds.getUser(name);
        } catch (SketchsyncPersistenceException e) {
            return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
        }
        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(user), HttpStatus.ACCEPTED);

    }



    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postUser(@RequestBody User user){
        try {
            ds.addNewUser(user);
            //registrar dato
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(SketchsyncAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error",HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {
        try {
            Gson gson = new Gson();
            return new ResponseEntity<>(gson.toJson(ds.getAllUsers()), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(SketchsyncAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Error al buscar a los participantes", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/Clue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveClue(@RequestBody Clue clue){
        try {
            ds.addNewClue(clue);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex){
            Logger.getLogger(SketchsyncAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se ha podido crear la pista", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = "/TakeClue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> TakeClue() {
        try {
            Gson gson = new Gson();
            return new ResponseEntity<>(gson.toJson(ds.TakeClue()), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(SketchsyncAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Error al buscar a los participantes", HttpStatus.NOT_FOUND);
        }
    }
}

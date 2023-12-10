package edu.eci.arsw.controllers;

import edu.eci.arsw.model.Point;
import edu.eci.arsw.model.User;
import edu.eci.arsw.services.SketchsyncServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class STOMPMessage {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    SketchsyncServices ds = null;

    @MessageMapping("/{name}")
    public synchronized void handlePointEvent(Point point, @DestinationVariable String name) throws Exception{
        msgt.convertAndSend("/topic/"+name, point);
        ArrayList<Point> points = new ArrayList<>();
        points.add(point);
        User user = new User(name, points);
        ds.addPointToUser(user);
    }

    @MessageMapping("/delete.{name}")
    public synchronized void handleDeletePointsEvent(@DestinationVariable String name) throws Exception{
        msgt.convertAndSend("/topic/"+name,"delete");
        ds.delteAllPointsUser(name);
    }



}
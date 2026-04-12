package com.akshat.streaming.websocket.model;

import org.springframework.web.socket.WebSocketSession;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Room {

    private final String name;
    private final Integer id;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public Room(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }
}
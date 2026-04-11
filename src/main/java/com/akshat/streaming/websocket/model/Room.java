package com.akshat.streaming.websocket.model;

import java.util.Set;
import org.springframework.web.socket.WebSocketSession;

public class Room {
    private final String name;
    private final int id;
    private Set<WebSocketSession> sessions;

    public Room(String name, int id) {
        this.name = name;
        this.id = id;
        this.sessions = new java.util.HashSet<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }
}
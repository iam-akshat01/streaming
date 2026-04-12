package com.akshat.streaming.websocket.manager;

import com.akshat.streaming.websocket.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomManager {

    private final Map<Integer, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Integer> sessionRoomMap = new ConcurrentHashMap<>();

    // 🔥 CREATE ROOM (called from REST API)
    public Room createRoom(int roomId, String name) {
        Room room = new Room(name, roomId);
        rooms.put(roomId, room);
        return room;
    }

    public boolean roomExists(int roomId) {
        return rooms.containsKey(roomId);
    }

    // 🔥 JOIN ROOM (called from WebSocket)
    public void joinRoom(int roomId, WebSocketSession session) {

        leaveRoom(session);

        Room room = rooms.get(roomId);

        if (room == null) {
            throw new RuntimeException("Room does not exist");
        }

        room.getSessions().add(session);
        sessionRoomMap.put(session.getId(), roomId);

        System.out.println("Session " + session.getId() + " joined room " + roomId);
    }

    public void leaveRoom(WebSocketSession session) {

        Integer roomId = sessionRoomMap.get(session.getId());

        if (roomId != null) {
            Room room = rooms.get(roomId);

            if (room != null) {
                room.getSessions().remove(session);

                if (room.getSessions().isEmpty()) {
                    rooms.remove(roomId);
                    System.out.println("Room " + roomId + " deleted (empty)");
                }
            }

            sessionRoomMap.remove(session.getId());
        }
    }

    public Room getRoom(WebSocketSession session) {
        Integer roomId = sessionRoomMap.get(session.getId());
        return roomId != null ? rooms.get(roomId) : null;
    }
}
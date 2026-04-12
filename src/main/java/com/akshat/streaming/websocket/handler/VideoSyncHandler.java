package com.akshat.streaming.websocket.handler;

import com.akshat.streaming.websocket.manager.RoomManager;
import com.akshat.streaming.websocket.model.Room;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;

@Component
public class VideoSyncHandler extends TextWebSocketHandler {

    private final RoomManager roomManager;

    public VideoSyncHandler(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connected: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        System.out.println("Received: " + payload);

        // 🔥 JOIN FORMAT → "JOIN:123456"
        if (payload.startsWith("JOIN:")) {
            int roomId = Integer.parseInt(payload.split(":")[1]);
            roomManager.joinRoom(roomId, session);
            return;
        }

        // ❌ Ignore if not in room
        Room room = roomManager.getRoom(session);
        if (room == null) {
            System.out.println("User not in room. Ignoring.");
            return;
        }

        // 🔥 Broadcast inside room
        for (WebSocketSession s : room.getSessions()) {
            if (s.isOpen() && !s.getId().equals(session.getId())) {
                s.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        roomManager.leaveRoom(session);
        System.out.println("Disconnected: " + session.getId());
    }
}
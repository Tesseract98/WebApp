package org.chat.service;

import org.chat.service.api.ChatService;
import org.chat.websocket.ChatWebSocket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ChatServiceImpl implements ChatService {
    private final Queue<ChatWebSocket> socketDeq;

    public ChatServiceImpl() {
        this.socketDeq = new ConcurrentLinkedDeque<>();
    }

    public void sendMessage(String data) {
        for (ChatWebSocket webSocket : socketDeq) {
            try {
                webSocket.sendString(data);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public void add(ChatWebSocket webSocket) {
        socketDeq.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket) {
        socketDeq.remove(webSocket);
    }

}

package org.chat.service.api;

import org.chat.websocket.ChatWebSocket;

public interface ChatService {
    void sendMessage(String data);

    void add(ChatWebSocket webSocket);

    void remove(ChatWebSocket webSocket);
}

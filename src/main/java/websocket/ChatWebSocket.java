package websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.api.ChatService;

import java.io.IOException;

@WebSocket
public class ChatWebSocket {
    private ChatService chatServiceImpl;
    private Session session;

    public ChatWebSocket(ChatService chatServiceImpl) {
        this.chatServiceImpl = chatServiceImpl;
    }

    @OnWebSocketConnect
    public void onOpen(Session session){
        chatServiceImpl.add(this);
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String data){
        chatServiceImpl.sendMessage(data);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        chatServiceImpl.remove(this);
    }

    public void sendString(String data){
        try {
            session.getRemote().sendString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import service.ChatServiceImpl;
import service.api.ChatService;
import templater.PageGenerator;
import websocket.ChatWebSocket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = "{/chat}")
public class WebSocketChatServlet extends WebSocketServlet {
    private static final int LOGOUT_TIME = 60 * 60 * 100;
    private final ChatService chatServiceImpl;

    public WebSocketChatServlet() {
        this.chatServiceImpl = new ChatServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPage("chat/chat.html", new Hashtable<>()));
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        webSocketServletFactory.setCreator((req, resp) -> new ChatWebSocket(chatServiceImpl));
    }


}

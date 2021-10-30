package org.chat.servlets;

import org.chat.service.ChatServiceImpl;
import org.chat.service.api.ChatService;
import org.chat.templater.PageGenerator;
import org.chat.websocket.ChatWebSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = "{/chat}")
public class WebSocketChatServlet extends WebSocketServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChatServlet.class);

    private static final long LOGOUT_TIME = TimeUnit.SECONDS.toMillis(60);
    private final ChatService chatServiceImpl;

    public WebSocketChatServlet() {
        this.chatServiceImpl = new ChatServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("doGet");
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPage("chat/chat.html", new Hashtable<>()));
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        LOGGER.debug("configure");
        webSocketServletFactory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        webSocketServletFactory.setCreator((req, resp) -> new ChatWebSocket(chatServiceImpl));
    }


}

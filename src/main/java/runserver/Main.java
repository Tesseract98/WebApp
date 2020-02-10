package runserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import servlets.WebSocketChatServlet;

public class Main {
    public static void main(String []args) throws Exception{
        Server server = new Server(8080);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(contextHandler);
        SignInServlet signInServlet = new SignInServlet();
        SignUpServlet signUpServlet = new SignUpServlet();
        WebSocketChatServlet webSocketChatServlet = new WebSocketChatServlet();
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        contextHandler.addServlet(new ServletHolder(webSocketChatServlet), "/chat");

//        addSiteTemplate(contextHandler, server);

        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }

    @SuppressWarnings("unused")
    private static void addSiteTemplate(ServletContextHandler contextHandler, Server server){
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("site");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, contextHandler});
        server.setHandler(handlers);
    }

}

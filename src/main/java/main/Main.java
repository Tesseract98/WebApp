package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.Frontend;

public class Main {
    public static void main(String []args) throws Exception{
        Frontend frontend = new Frontend();
        Server server = new Server(8080);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(contextHandler);
        contextHandler.addServlet(new ServletHolder(frontend), "/mirror");
        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
//        System.out.println("Server started");
        server.join();
    }
}

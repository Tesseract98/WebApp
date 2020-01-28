package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignUpServlet;
import servlets.SignInServlet;

public class Main {
    public static void main(String []args) throws Exception{
        Server server = new Server(8080);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(contextHandler);
        SignInServlet signInServlet = new SignInServlet();
        SignUpServlet signUpServlet = new SignUpServlet();
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }

}

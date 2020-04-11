package runserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.account.AccountServer;
import server.account.AccountServerController;
import server.account.AccountServerControllerMBean;
import server.account.AccountServerImpl;
import servlets.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        int port = 8080;
        Server server = new Server(port);
        LOGGER.info("Starting port: " + port);

        AccountServer accountServer = new AccountServerImpl();
        addJMXManipulationForJConsole(accountServer);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(contextHandler);

        contextHandler.addServlet(new ServletHolder(new SignUpServlet()), "/signup");
        contextHandler.addServlet(new ServletHolder(new SignInServlet()), "/signin");
        contextHandler.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
        contextHandler.addServlet(new ServletHolder(new AdminServlet(accountServer)), "/admin");
        contextHandler.addServlet(new ServletHolder(new ResourcesServlet()), "/resources");

//        addSiteTemplate(contextHandler, server);

        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }

    private static void addJMXManipulationForJConsole(AccountServer accountServer) throws Exception {
//        Object mBean = clazz.getConstructor(obj.getClass()).newInstance(obj);
        AccountServerControllerMBean mBean = new AccountServerController(accountServer);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController.usersLimit");
        mBeanServer.registerMBean(mBean, name);
    }

    @SuppressWarnings("unused")
    private static void addSiteTemplate(ServletContextHandler contextHandler, Server server) {
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("site");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, contextHandler});
        server.setHandler(handlers);
    }

}

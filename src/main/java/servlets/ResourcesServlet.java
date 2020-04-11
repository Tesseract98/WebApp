package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.TestResource;
import server.resources.TestResourceServerController;
import server.resources.TestResourceServerControllerMBean;
import server.sax.ReadXMLFileSAX;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;

public class ResourcesServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("ResourcesServlet doPost");
        String path = req.getParameter("path");

        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(path);
        try {
            addJMXManipulationForJConsole(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addJMXManipulationForJConsole(TestResource serverController) throws Exception {
        LOGGER.debug("ResourcesServlet addJMXManipulationForJConsole");
        TestResourceServerControllerMBean mBean = new TestResourceServerController(serverController);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=ResourceServerController");
        mBeanServer.registerMBean(mBean, name);
    }
}

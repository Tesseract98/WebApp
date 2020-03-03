package servlets;

import accountServer.AccountServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServlet.class);
    private final AccountServer accountServer;

    public AdminServlet(AccountServer accountServer) {
        LOGGER.debug("constructor");
        this.accountServer = accountServer;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.debug("doGet");
        resp.setContentType("text/html");
        resp.getWriter().println(accountServer.getUsersLimit());
        resp.setStatus(200);
    }

}

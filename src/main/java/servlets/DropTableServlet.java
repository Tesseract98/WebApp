package servlets;

import org.eclipse.jetty.http.HttpStatus;
import service.DBService;
import service.exceptions.DbException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DropTableServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        DBService dbService = new DBService();
        try {
            dbService.cleanup();
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println("drop done!");
        } catch (DbException | IOException e) {
            e.printStackTrace();
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }

}

package servlets;

import dto.UserDto;
import dto.exceptions.DtoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBService;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignInServlet.class);

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.debug("doPost");
        DBService dbService = new DBService();
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        Object login = pageVariables.get("login");
        Object password = pageVariables.get("password");
        resp.setContentType("text/html;charset=utf-8");
        UserDto userDto = new UserDto(login, password);
        try {
            if (userDto.validate()) {
                if (dbService.userInDB(userDto.getName(), userDto.getPassword())) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println(String.format("Authorized: %s", login));
                } else {
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.getWriter().println("Unauthorized");
                }
            }
        } catch (DtoException e) {
            LOGGER.debug("doPost DtoException {}", (Object) e.getStackTrace());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.debug("doGet");
        Map<String, Object> request = new HashMap<>();
        request.put("link", "signin");
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPage("user.html", request));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        LOGGER.debug("createPageVariablesMap");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", request.getParameter("login"));
        pageVariables.put("password", request.getParameter("password"));
        return pageVariables;
    }

}

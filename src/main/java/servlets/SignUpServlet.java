package servlets;

import dto.UserDto;
import dto.exceptions.DtoException;
import model.User;
import service.DBService;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    public SignUpServlet(){
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        Object login = pageVariables.get("login");
        Object password = pageVariables.get("password");
        resp.setContentType("text/html;charset=utf-8");
        try {
            UserDto userDto = new UserDto(login, password);
            if (userDto.validate()) {
                User user = new User(userDto.getName(), userDto.getPassword());
                DBService dbService = new DBService();
                String userInDB = dbService.addUser(user);
                resp.getWriter().println(String.format("%s", userInDB));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().println("confidential information");
            }
        }
        catch (DtoException e) {
            e.printStackTrace();
        }
    }

    public void  doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Map<String, Object> request = new HashMap<>();
        request.put("link", "signup");
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPage("user.html", request));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request){
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", request.getParameter("login"));
        pageVariables.put("password", request.getParameter("password"));
        return pageVariables;
    }

}

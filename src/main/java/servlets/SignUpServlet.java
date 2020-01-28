package servlets;

import service.AccountService;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        Object login = pageVariables.get("login");
        Object password = pageVariables.get("password");
        resp.setContentType("text/html;charset=utf-8");
        if((login == null || login.toString().isEmpty()) || (password == null || password.toString().isEmpty())){
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().println("confidential information");
        }else{
            AccountService accountService = AccountService.getImplement();
            accountService.setName(login.toString());
            accountService.setPassword(password.toString());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(String.format("%s %s", login, password));
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

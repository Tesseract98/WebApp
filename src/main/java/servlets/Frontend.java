package servlets;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Frontend extends HttpServlet {
    private String login = "";

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        String message = req.getParameter("message");
        resp.setContentType("text/html;charset=utf-8");
        if(message == null || message.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else{
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("key", message == null? "" : message);
        resp.getWriter().println(PageGenerator.instance().getPage("user.html", pageVariables));
    }

    public void  doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        //pageVariables.put("key", "");
//        resp.getWriter().println(PageGenerator.instance().getPage("user.html", pageVariables));
        resp.getWriter().println(pageVariables.get("key"));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request){
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("key", request.getParameter("key"));
//        pageVariables.put("URL", request.getRequestURL().toString());
//        pageVariables.put("pathInfo", request.getPathInfo());
//        pageVariables.put("sessionId", request.getSession().getId());
//        pageVariables.put("parameters", request.getParameterMap().toString());
        return pageVariables;
    }
}

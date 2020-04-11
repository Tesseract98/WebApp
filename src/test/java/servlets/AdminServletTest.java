package servlets;


import org.junit.jupiter.api.Test;
import server.account.AccountServer;
import server.account.AccountServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminServletTest {

    private AccountServer accountServer = spy(new AccountServerImpl());

    private HttpServletResponse getMockedResponse(StringWriter stringWriter) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);

        final PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        return response;
    }

    private HttpServletRequest getMockedRequest() {
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getPathInfo()).thenReturn("/admin");

        return request;
    }

    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        AdminServlet adminServlet = new AdminServlet(accountServer);

        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse(stringWriter);

        adminServlet.doGet(request, response);

        assertEquals("10", stringWriter.toString().trim());
        verify(accountServer, times(1)).getUsersLimit();
    }

}

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/startSession")
public class StartSessionServlet extends HttpServlet {
    private static boolean sessionActive = false;

    public static boolean isSessionActive() {
        return sessionActive;
    }

    public static void setSessionActive(boolean active) {
        sessionActive = active;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setSessionActive(true); // Use the setter method
        response.getWriter().println("Session started");
    }
}
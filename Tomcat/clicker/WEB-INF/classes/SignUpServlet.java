import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker?useSSL=false";
    private static final String DB_USER = "myuser";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");

        response.setContentType("text/plain");

        // Validate inputs
        if (username == null || password == null || mobile == null ||
                username.trim().isEmpty() || password.trim().isEmpty() || mobile.trim().isEmpty()) {
            response.getWriter().write("error: Missing parameters");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO users (username, password, mobile) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, mobile);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } catch (SQLException e) {
            response.getWriter().write("error: " + e.getMessage());
        }
    }
}

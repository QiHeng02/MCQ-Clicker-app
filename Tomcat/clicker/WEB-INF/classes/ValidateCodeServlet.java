import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/validateCode")
public class ValidateCodeServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "myuser";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enteredCode = request.getParameter("code");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (enteredCode != null && enteredCode.length() == 4 && enteredCode.matches("\\d+")) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM session_codes WHERE code = ? AND is_active = TRUE")) { // Adjust query if you don't use is_active
                pstmt.setString(1, enteredCode);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    out.println("success"); // Code is valid
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("error: Database error");
                return;
            }
        }

        out.println("invalid"); // Code is invalid
    }
}
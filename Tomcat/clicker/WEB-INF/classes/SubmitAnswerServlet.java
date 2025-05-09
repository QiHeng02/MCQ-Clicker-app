import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/select")
public class SubmitAnswerServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "myuser";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String choice = request.getParameter("choice");
        String userId = request.getParameter("userId");
        int questionId = Integer.parseInt(request.getParameter("questionId"));

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Check if the session is active
        if (!StartSessionServlet.isSessionActive()) {
            out.println("Session is not active. Answer not recorded.");
            return;
        }

        if (choice != null && (choice.equalsIgnoreCase("a") || choice.equalsIgnoreCase("b") || choice.equalsIgnoreCase("c") || choice.equalsIgnoreCase("d"))) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO answers (user_id, question_id, selected_option) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, userId);
                    pstmt.setInt(2, questionId);
                    pstmt.setString(3, choice.toUpperCase());

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        out.println("Answer submitted successfully for choice: " + choice);
                    } else {
                        out.println("Failed to submit answer for choice: " + choice);
                    }
                }
            } catch (SQLException e) {
                out.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            out.println("Invalid choice received: " + choice);
        }
    }
}
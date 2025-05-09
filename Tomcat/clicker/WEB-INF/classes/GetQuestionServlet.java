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

@WebServlet("/getQuestions")
public class GetQuestionServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "myuser";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id, question_text, option_a, option_b, option_c, option_d, correct_option FROM questions";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                StringBuilder jsonResponse = new StringBuilder();
                jsonResponse.append("[");

                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        jsonResponse.append(",");
                    }
                    jsonResponse.append(String.format(
                            "{\"id\":%d,\"question_text\":\"%s\",\"option_a\":\"%s\",\"option_b\":\"%s\",\"option_c\":\"%s\",\"option_d\":\"%s\",\"correct_option\":\"%s\"}",
                            rs.getInt("id"),
                            escapeJson(rs.getString("question_text")),
                            escapeJson(rs.getString("option_a")),
                            escapeJson(rs.getString("option_b")),
                            escapeJson(rs.getString("option_c")),
                            escapeJson(rs.getString("option_d")),
                            escapeJson(rs.getString("correct_option"))
                    ));
                    first = false;
                }

                jsonResponse.append("]");

                out.print(jsonResponse.toString());
            }
        } catch (SQLException e) {
            out.print("{\"error\":\"Database error: " + escapeJson(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
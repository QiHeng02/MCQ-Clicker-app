import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword"; // change this!

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Get the latest question (you can adjust this logic)
            String sql = "SELECT * FROM questions ORDER BY id DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String question = rs.getString("question_text");
                String a = rs.getString("option_a");
                String b = rs.getString("option_b");
                String c = rs.getString("option_c");
                String d = rs.getString("option_d");

                out.println("<html><head><title>Clicker Question</title></head><body>");
                out.println("<h2>" + question + "</h2>");
                out.println("<ul>");
                out.println("<li>A. " + a + "</li>");
                out.println("<li>B. " + b + "</li>");
                out.println("<li>C. " + c + "</li>");
                out.println("<li>D. " + d + "</li>");
                out.println("</ul>");
                out.println("</body></html>");
            } else {
                out.println("<html><body><p>No question available.</p></body></html>");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<html><body><p>Error: " + e.getMessage() + "</p></body></html>");
        }
    }
}

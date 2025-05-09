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

@WebServlet("/display")
public class DisplayStatsServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "myuser";
    private static final String DB_PASSWORD = "password";
    //function to ensure it does not go past the total number of question in SQL
    private int getTotalNumberOfQuestions() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM questions");
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0; // Default to 0 if there's an error
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Fetch the question ID from the request (default to 1 if not specified)
        String questionIdStr = request.getParameter("questionId");
        int questionId = (questionIdStr != null) ? Integer.parseInt(questionIdStr) : 1;

        // Query to get the data for the selected question
        String query = "SELECT selected_option, COUNT(*) as count FROM answers WHERE question_id = ? GROUP BY selected_option";

        // Store statistics for the question
        StringBuilder labels = new StringBuilder();
        StringBuilder data = new StringBuilder();
        int totalQuestions = 0;

        try {
            totalQuestions = getTotalNumberOfQuestions();
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, questionId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String option = rs.getString("selected_option");
                    int count = rs.getInt("count");

                    labels.append("'").append(option).append("',");
                    data.append(count).append(",");
                }

                // Remove trailing commas
                if (labels.length() > 0) labels.setLength(labels.length() - 1);
                if (data.length() > 0) data.setLength(data.length() - 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error connecting to database: " + e.getMessage() + "</p>");
            return;
        }

        // Generate HTML with Chart.js for the selected question
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Clicker Statistics</title>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>");
        out.println("<style>");
        out.println("    body { display: flex; flex-direction: column; align-items: center; background-color: #E0BBE4;}");
        out.println("    .chart-container { text-align: center; margin: 20px auto; background-color: white; }");
        out.println("    .chart-title { font-size: 24px; font-weight: bold; }");
        out.println("    .chart-labels { font-size: 18px; font-weight: bold; }");
        out.println("    .chart-legend { font-size: 16px; font-weight: bold; }");
        out.println("    .chart-button { font-size: 18px; font-weight: bold; padding: 10px 20px; margin: 10px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h2 class='chart-title'>Answer Statistics for Question " + questionId + "</h2>");

        // Chart.js code for the current question
        out.println("<div class='chart-container'>");
        out.println("<canvas id='myChart' width='1200' height='800' style='width:100%;max-width:1500px;max-height: calc(1500px * 0.85);'></canvas>");
        out.println("<script>");
        out.println("const ctx = document.getElementById('myChart').getContext('2d');");
        out.println("const myChart = new Chart(ctx, {");
        out.println("    type: 'bar',");
        out.println("    data: {");
        out.println("        labels: [" + labels.toString() + "],");
        out.println("        datasets: [{");
        out.println("            label: 'Number of Responses',");
        out.println("            data: [" + data.toString() + "],");
        out.println("            backgroundColor: [");
        out.println("                'rgba(255, 99, 132, 0.6)',");
        out.println("                'rgba(54, 162, 235, 0.6)',");
        out.println("                'rgba(255, 206, 86, 0.6)',");
        out.println("                'rgba(75, 192, 192, 0.6)'");
        out.println("            ],");
        out.println("            borderWidth: 1");
        out.println("        }]");
        out.println("    },");
        out.println("    options: {");
        out.println("        responsive: false, // Disable responsiveness");
        out.println("        maintainAspectRatio: false, // Allow changing aspect ratio");
        out.println("        scales: {");
        out.println("            y: { beginAtZero: true },");
        out.println("            x: {");
        out.println("                ticks: {");
        out.println("                    font: { size: 18, weight: 'bold' }"); // Increase font size of X-axis labels
        out.println("                }");
        out.println("            }");
        out.println("        },");
        out.println("        plugins: {");
        out.println("            legend: {");
        out.println("                labels: {");
        out.println("                    font: { size: 16, weight: 'bold' }"); // Increase font size of legend labels
        out.println("                }");
        out.println("            }");
        out.println("        }");
        out.println("    }");
        out.println("});");
        out.println("</script>");
        out.println("</div>");

        // Navigation buttons for Next and Previous
        out.println("<div style='text-align: center; margin-top: 20px;'>");

        if (questionId > 1) {
            out.println("<a href='/clicker/display?questionId=" + (questionId - 1) + "'>");
            out.println("<button class='chart-button'>Previous</button>");
            out.println("</a>");
        }

        // Only show the "Next" button if the current questionId is less than the total number of questions
        if (questionId < totalQuestions) {
            out.println("<a href='/clicker/display?questionId=" + (questionId + 1) + "'>");
            out.println("<button class='chart-button'>Next</button>");
            out.println("</a>");
        }

        out.println("</div>");
        out.println("</body></html>");
    }
}
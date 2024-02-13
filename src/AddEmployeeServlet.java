import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve data from the request
        String name = request.getParameter("name");
        String position = request.getParameter("position");

        // Perform validation if needed

        // Save the employee to the database (you can use JDBC or an ORM like Hibernate)
        // Example JDBC code:
        // Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_db", "username", "password");
        // PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (name, position) VALUES (?, ?)");
        // stmt.setString(1, name);
        // stmt.setString(2, position);
        // stmt.executeUpdate();

        // Redirect back to a confirmation page or list page
        response.sendRedirect("WebContent/JSP/employeeAdded.jsp");
    }
}

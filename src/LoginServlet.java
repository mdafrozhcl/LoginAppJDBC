import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			PreparedStatement statement= con.prepareStatement("select * from users where username='"+username+"' and password='"+password+"'");
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				JOptionPane.showMessageDialog(null, "Login Successful...");
				response.sendRedirect("/LoginApplication/login.html");
			}else{
				JOptionPane.showMessageDialog(null, "Login not Successful...");
				RequestDispatcher rd = request.getRequestDispatcher("/RegisterServlet");
				rd.forward(request, response);
				
			}
			statement.close();
			con.close();
		}catch(Exception e){
			out.print(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

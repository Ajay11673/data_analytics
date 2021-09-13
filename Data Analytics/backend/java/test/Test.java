package test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;

import dao.DaoUser;
import model.DataSource;
import model.DataSourceColumnHeader;
import model.DataSourceRowData;
import model.Roles;
import model.User;
import util.HibernateUtil;

@WebServlet("/hello")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			User user = new User();
			user.setUser_name("admin");
			user.setPassword("asdasdasd");
			user.setRole(Roles.ADMIN);
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			user.setAdded_date(dateFormat.format(date));
			new DaoUser().saveUser(user);
		}
		
		response.getWriter().print("Admin is added");

	}

}

package dao;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import model.DataSource;
import model.Roles;
import model.User;
import util.HibernateUtil;

public class DaoLogin {
	public static final String SELECT_ALL_FROM_USER = "SELECT * FROM users WHERE user_name = ? AND password = ?";
	public static User login(String username,String password) {
		User user = new User();
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("user_name",username));
			criteria.add(Restrictions.eq("password",password));
			user = (User) criteria.list().get(0);
			if(user.getRole()==Roles.ADMIN) {
				Criteria getDataSource = session.createCriteria(DataSource.class);
				List<DataSource> dataSources= getDataSource.list();
				user.setDataSource(dataSources);
			}else {
				Criteria getDataSource = session.createCriteria(DataSource.class);
				Criterion userIdCriterion = Restrictions.eq("allowed_to", user.getId());
				Criterion allowedByAdmin = Restrictions.eq("allowed_to", 0);
				LogicalExpression orExpression = Restrictions.or(userIdCriterion, allowedByAdmin);
				getDataSource.add(orExpression);
				
				List<DataSource> dataSources= getDataSource.list();
				user.setDataSource(dataSources);
			}
			transaction.commit();
			session.close();
		}
		return user;
	}
}

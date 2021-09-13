package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;

import model.DataSource;
import model.Roles;
import model.User;
import util.HibernateUtil;

public class DaoUser {
	public void saveUser(User user) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			System.out.println("Saving user: "+user);
			session.save(user);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			Criteria criteria = session.createCriteria(User.class);
			users = criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	public JSONObject getUsersWithLimit(int pageNo, int rowCount) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query =  session.createQuery("select user_name,role,id,added_date from User where id>:start and id<=:end");
		int end = (rowCount*pageNo);
		int start = end-rowCount;
		query.setParameter("start", start);
		query.setParameter("end", end);
		List<Object[]> students = (List<Object[]>) query.list();
		JSONObject jsonObject = new JSONObject();
		for(Object[] student : students) {
			String data = "{\""+"userName"+"\""+":"+"\""+student[0]+"\""
					+","+"\""+"role"+"\""+":"+"\""+student[1]+"\""
					+","+"\""+"userId"+"\""+":"+"\""+student[2]+"\""
					+","+"\""+"addedDate"+"\""+":"+"\""+student[3]+"\"}";
			jsonObject.accumulate("data", data);
		}
		Criteria criteria = session.createCriteria(User.class);
		criteria.setProjection(Projections.rowCount());
		long size = (long) criteria.uniqueResult();
		jsonObject.put("size", size);
		
		return jsonObject;
	}
	
	public User getUserById(int id) {
		User user= new User();
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id",id));
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
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUserWithPasswordAndUserName(String username,String password) {
		User user = new User();
		try(Session session =  HibernateUtil.getSessionFactory().openSession()){
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
			session.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}

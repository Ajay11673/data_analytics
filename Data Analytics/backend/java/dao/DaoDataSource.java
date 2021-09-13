package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import model.DataSource;
import util.HibernateUtil;

public class DaoDataSource {
	
	public void saveDataSource(DataSource dataSource) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			
			System.out.println(dataSource);
			session.save(dataSource);
			
			transaction.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean updateDataSource(DataSource dataSource) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			
			System.out.println(dataSource);
			session.update(dataSource);	
			transaction.commit();
			session.close();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public boolean deleteFromDataSource(int id) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			Transaction transaction = session.beginTransaction();
			DataSource dataSource = getDataSource(id);
			System.out.println(dataSource);
			session.delete(dataSource);
			transaction.commit();
			session.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	public DataSource getDataSource(int id){
		DataSource ds = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			Criteria criteria = session.createCriteria(DataSource.class);
			criteria.add(Restrictions.eq("id",id));
			ds = (DataSource) criteria.list().get(0);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ds;
	}

}

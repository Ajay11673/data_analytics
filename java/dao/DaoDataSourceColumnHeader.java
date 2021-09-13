package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import model.DataSource;
import model.DataSourceColumnHeader;
import model.DataSourceRowData;
import util.HibernateUtil;

public class DaoDataSourceColumnHeader {
	
	public void saveDSColumnHeader(DataSource dataSource,List<DataSourceColumnHeader> columnHeaders) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			for(DataSourceColumnHeader dataSourceColumnHeader: columnHeaders) {
				dataSourceColumnHeader.setDataSource(dataSource);
				session.save(dataSourceColumnHeader);
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<DataSourceColumnHeader> getColumnHeaders(int id){
		List<DataSourceColumnHeader> dataSourceColumnHeaders = new ArrayList<>();
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			Criteria criteria = session.createCriteria(DataSourceColumnHeader.class);
			criteria.add(Restrictions.eq("dataSource",new DaoDataSource().getDataSource(id)));
			dataSourceColumnHeaders = criteria.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSourceColumnHeaders;
	}
}

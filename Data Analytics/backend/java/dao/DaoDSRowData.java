package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import model.DataSource;
import model.DataSourceColumnHeader;
import model.DataSourceRowData;
import util.HibernateUtil;

public class DaoDSRowData {
	private static final int BATCH_COUNT=100;
	public int saveDRowData(BufferedReader reader,List<DataSourceColumnHeader> columnHeaders,DataSource dataSource) {
		int current_count=0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			Transaction transaction = session.beginTransaction();
			for(String ln;(ln = reader.readLine())!=null;) {
				String datas[] = ln.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				int i=0;
				StringBuffer buffer = new StringBuffer();
				buffer.append("{");
				//System.out.println("\n\n");
				DataSourceRowData rowData = new DataSourceRowData();
					for(String data:datas) {
						String columnName= columnHeaders.get(i++).getColumn_name().replace(" ", "_");
						if(data.contains("\""))
							data = data.replaceAll("\"","");
						if(columnName.contains("\""))
							columnName = columnName.replace("\"","");
							buffer.append("\""+columnName+"\""+":"+"\""+ data +"\",");

					}
					buffer.replace(buffer.length()-1,buffer.length(),"");
					buffer.append("}");
					rowData.setData(buffer.toString());
					rowData.setRow_index(current_count++);
					rowData.setDataSource(dataSource);
					//System.out.println(rowData);
					session.save(rowData);
					if(current_count % BATCH_COUNT==0) {
						System.out.println("\n\n");
						System.out.println("Flushing and clearing");
						System.out.println("\n\n");
						session.flush();
						session.clear();
					}
					//System.out.println("\n\n");
					//session.flush();
					
				}
			session.close();
				 
		} catch (IOException e) {

			e.printStackTrace();
		}
		return current_count;
	}
	
	public List<DataSourceRowData> getPaginatedDataSourceData(int id,int pageNo, int recordCount){
		System.out.println(id+" ,"+pageNo+" ,"+recordCount);
		int end = (recordCount*pageNo);
		int start = end-recordCount;
		List<DataSourceRowData> dataSourceRowDatas = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			Criteria criteria = session.createCriteria(DataSourceRowData.class);
			criteria.add(Restrictions.eq("dataSource",new DaoDataSource().getDataSource(id)));
			Criterion greatThan = Restrictions.ge("row_index", start);
			Criterion lessThan = Restrictions.lt("row_index", end);
			LogicalExpression andExpression = Restrictions.and(greatThan, lessThan);
			criteria.add(andExpression);
			dataSourceRowDatas = criteria.list();
		} catch (Exception e) {

		}
		return dataSourceRowDatas;
	}

}

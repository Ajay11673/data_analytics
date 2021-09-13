package model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name="ds_column_header")
public class DataSourceColumnHeader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique = true)
	private int id;
	
	@ManyToOne
	private DataSource dataSource;
	

	
	@Column(name = "column_name")
	private String column_name;
	
	public DataSourceColumnHeader() {

	}
	
	public DataSourceColumnHeader(DataSource dataSource, String column_name) {
		this.dataSource = dataSource;
		this.column_name = column_name;
	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	

	@Override
	public String toString() {
		Map<String, Object> jsonMap = new HashMap<>();
		String name = column_name;
		if(name.contains("\"")) {
			jsonMap.put("\""+ "title" +"\"", column_name );
			jsonMap.put("\""+ "field" +"\"", column_name.replace(" ","_") );
		}
		else {
			jsonMap.put("\""+ "title" +"\"","\""+column_name+"\"");
			jsonMap.put("\""+ "field" +"\"","\""+column_name.replace(" ","_")+"\"");
		}
		return jsonMap.toString().replace("=",":");
	}
	
}

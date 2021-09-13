package model;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.HashMap;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ds_row_data")
public class DataSourceRowData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique = true)
	private int id;
	
	@Column(name = "data", length = 2048)
	private String data;
	
	@Column(name = "row_index")
	private int row_index;
	
	@ManyToOne
	private DataSource dataSource;
	
	public DataSourceRowData() {

	}
	

	public DataSourceRowData(String data, int row_index, DataSource dataSource) {
		super();
		this.data = data;
		this.row_index = row_index;
		this.dataSource = dataSource;
	}


	public int getRow_index() {
		return row_index;
	}


	public void setRow_index(int row_index) {
		this.row_index = row_index;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		Map<String, Object> jsonMap = new HashMap<>();
		//jsonMap.put("\""+ "id" +"\"", "\""+ id +"\"");
		jsonMap.put("\""+ "data" +"\"", data );
//		jsonMap.put("\""+ "row_index" +"\"", "\""+ row_index +"\"");
		return ""+data+"";
	}

	
	
	
}

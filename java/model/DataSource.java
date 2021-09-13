package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "ds")
public class DataSource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique = true)
	private int id;
	
	@Column(name = "file_name")
	private String file_name;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "dataSource", orphanRemoval = true)
	private List<DataSourceColumnHeader> data_source_column_headers = new ArrayList<>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "dataSource", orphanRemoval = true)
	private List<DataSourceRowData> data_source_row_datas = new ArrayList<>();
	
	@Column(name = "created_date")
	private String created_date;
	
	@Column(name = "allowed_to")
	private int allowed_to;
	
	@ManyToOne
	private User user;
	
	@Column(name = "row_count")
	private int row_count;
	
	public DataSource() {
		
	}
	
	public DataSource(String file_name) {
		this.file_name = file_name;
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public List<DataSourceColumnHeader> getData_source_column_headers() {
		return data_source_column_headers;
	}

	public void setData_source_column_headers(List<DataSourceColumnHeader> data_source_column_headers) {
		this.data_source_column_headers = data_source_column_headers;
	}

	public List<DataSourceRowData> getData_source_row_datas() {
		return data_source_row_datas;
	}

	public void setData_source_row_datas(List<DataSourceRowData> data_source_row_datas) {
		this.data_source_row_datas = data_source_row_datas;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public int getAllowed_to() {
		return allowed_to;
	}

	public void setAllowed_to(int allowed_to) {
		this.allowed_to = allowed_to;
	}

	public int getRow_count() {
		return row_count;
	}

	public void setRow_count(int row_count) {
		this.row_count = row_count;
	}

	
	@Override
	public String toString() {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("\""+ "id" +"\"", "\""+ id +"\"");
		jsonMap.put( "\""+ "file_name" +"\"", "\""+ file_name +"\"");
		jsonMap.put( "\""+ "created_date" +"\"", "\""+ created_date +"\"");
		jsonMap.put( "\""+ "row_count" +"\"",row_count);
		jsonMap.put( "\""+ "allowed_to" +"\"","\""+ allowed_to +"\"");
		jsonMap.put( "\""+ "created_by" +"\"", "\""+ user.getUser_name() +"\"");
		return jsonMap.toString().replace("=",":");
		
	}

	
	
}

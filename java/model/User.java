package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique = true)
	private int id;
	
	@Column(name = "user_name")
	private String user_name;
	
	@Column(name = "password")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Roles role;
	
	@OneToMany(mappedBy = "user" , fetch = FetchType.EAGER)
	private List<DataSource> dataSource = new ArrayList<>();
	
	@Column(name="added_date")
	private String added_date;
	public User() {
		
	}
	
	public User(String user_name, String password, Roles role) {
		this.user_name = user_name;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public List<DataSource> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<DataSource> dataSource) {
		this.dataSource = dataSource;
	}

	public String getAdded_date() {
		return added_date;
	}

	public void setAdded_date(String added_date) {
		this.added_date = added_date;
	}
	public String getBasicDetails() {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put( "\""+ "user_id" +"\"", "\""+ id +"\"");
		jsonMap.put( "\""+ "user_name" +"\"", "\""+ user_name +"\"");
		jsonMap.put( "\""+ "role" +"\"", "\""+ role +"\"");
		jsonMap.put( "\""+ "added_date" +"\"", "\""+ added_date +"\"");
		return jsonMap.toString().replace("=",":");
	}
	@Override
	public String toString() {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put( "\""+ "user_id" +"\"", "\""+ id +"\"");
		jsonMap.put( "\""+ "user_name" +"\"", "\""+ user_name +"\"");
		jsonMap.put( "\""+ "password" +"\"", "\""+ password +"\"");
		jsonMap.put( "\""+ "role" +"\"", "\""+ role +"\"");
		jsonMap.put( "\""+ "added_date" +"\"", "\""+ added_date +"\"");
		jsonMap.put( "\""+ "data_source" +"\"",dataSource);
		return jsonMap.toString().replace("=",":");
	}
	
	
	
	
}

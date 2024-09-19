package jmaster.io.restapi.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "DBUser")
public class User {
	@Id
	//@GeneratedValue(strategy = GenerationType.) Bỏ đi vì cái này là sinh khóa chính tự động => ta tự tạo id nên chx cần
	private int user_id;
	@Column(unique = true)
	private String username;
	private String password;
	private String role_id;
	@Transient
	private Set<Integer> role_set = new HashSet<>();	//Set ko có kiểu int
    @ManyToMany(fetch = FetchType.EAGER)
    //joinColums: cột nhập vào hay cột khóa ngoại, name: tên của cột khóa ngoại cho bảng tg, ref..: tên cột của user mà bảng tg tham chiếu tới
	@JoinTable(name = "DBUserRole", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id") )
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Set<Integer> getRole_set() {
		return Arrays.stream(role_id.split(",")).map(Integer::parseInt).collect(Collectors.toSet());
	}
	public void setRole_set(Set<Integer> role_id) {
		this.role_set = role_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(Set<Integer> role_set) {
		this.role_id = role_set.stream()							//phân luồng
								.map(String::valueOf)				//Thực hiện chuyển giá trị sang String tại mỗi luồng
								.collect(Collectors.joining(","));	//nhặt thêm dấu , 
	}
	@Override
	public String toString() {
		return "username: "+username+", role_id: "+role_id+", role_set: "+role_set;
	}
	public User(int user_id, String username, String password, Set<Integer> role_set) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.role_set = role_set;
		this.role_id = role_set.stream()							//phân luồng
								.map(String::valueOf)				//Thực hiện chuyển giá trị sang String tại mỗi luồng
								.collect(Collectors.joining(","));	//nhặt thêm dấu , 
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
		this.user_id = 0;
		this.username = "username";
		this.password = "password";
		this.role_set = null;
	}
	
}

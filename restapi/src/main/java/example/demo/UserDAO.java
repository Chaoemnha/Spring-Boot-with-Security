package example.demo;

import jmaster.io.restapi.model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	private static Connection conn;
	public UserDAO() {
		try {
			String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=QLGioHang;user=sa;password=123123123123;encrypt=true;trustServerCertificate=true;";
			conn = DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
//Ta thấy cứ mỗi một phương thức ta đều phải try conn catch... Tại sao không cho nó try catch thành công luôn khi khởi tạo class và thu được conn
	public static List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		try{
			String sql = "SELECT [username] ,[password] FROM [QLGioHang].[dbo].[User]";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String name = resultSet.getNString("username");
				String pass = resultSet.getNString("password");
				users.add(new User(name,pass));
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return users;
	}
	
	public static void addAUser(User user) {
		try{
			String sql = String.format("INSERT INTO [dbo].[User]([username],[password]) VALUES('%s','%s')",user.getUsername(),user.getPassword());
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void delAUser(int id) {
		try{
			String sql = String.format("DELETE FROM [dbo].[User] WHERE [user_id] = %d",id);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void updateAUser(User username) {
		try{
			String sql = String.format("UPDATE [dbo].[User] SET [password] = '%s' WHERE [username] = '%s'",username.getPassword(), username.getUsername());
			System.out.println(sql);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static List<Product> getAllProduct(){
		List<Product> products = new ArrayList<>();
		try {
			String sql = "select PID, PName, PPrice from Product";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String id = resultSet.getString("PID");
				String name = resultSet.getNString("PName");
				Double price = resultSet.getDouble("PPrice");
				products.add(new Product(id, name, price));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return products;
	}	
	public static User findAUser(String username) {
		try {
			String sql = String.format("select * from [dbo].[User] WHERE [username] like '%s'",username);
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String password = resultSet.getNString("password");
				return new User(username, password);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	public static List<CartItem> getAllCartItem(String username){
		List<CartItem> cartItems = new ArrayList<CartItem>();
		try{
			String sql = "select p.PName, p.PPrice, c.quantity, p.PID from CartItem c join Product p on c.PID = p.PID where c.username = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String pname = resultSet.getString("PName");
				String pid = resultSet.getString("PID");
				Double price = resultSet.getDouble("PPrice");
				Product p = new Product(pid, pname, price);
				int quantity = resultSet.getInt("quantity");
				cartItems.add(new CartItem(p, quantity));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cartItems;
	}
	
}
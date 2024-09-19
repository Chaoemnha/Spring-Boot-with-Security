package example.demo;

import java.util.List;

import jmaster.io.restapi.model.User;

public class MenuDisplay {
	public static void DisplayUser() {
		UserDAO userDAO = new UserDAO();
		List<User> users = userDAO.getAllUsers();
		for (User d : users) {
			System.out.println(d.toString());
		}
		System.out.println("Connected success");
	}	
}

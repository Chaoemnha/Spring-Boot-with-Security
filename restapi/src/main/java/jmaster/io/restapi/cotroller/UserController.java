package jmaster.io.restapi.cotroller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.restapi.model.User;
import jmaster.io.restapi.service.UserService;

@RestController
public class UserController {
//  before: sử dụng DAO viết thủ công lệnh sql	
//	@PostMapping("/register")
//	public List<User> create(@RequestBody User user) {
//		List<User> use = new ArrayList<>();	
//		UserDAO.addAUser(user);
//		use = UserDAO.getAllUsers();
//		return use;							
//	}
	@Autowired 
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody CreateCtrl requser){
		Set<Integer> roleId = new HashSet<>();
		for (int role : requser.getRoleId()) {
			roleId.add(role);
		}
		User user = userService.createUser(requser.getUser_id(), requser.getUsername(), requser.getPassword(), roleId);
		return ResponseEntity.ok(user); 	//pthuc này trả về ResponseEnity<User> nếu biến kiểu User trong ngoặc hợp lệ
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<User> isSuccess(@RequestBody String[] unAndPw) {
		User user = userService.findAUser(unAndPw);
		return ResponseEntity.ofNullable(user);
	}
	
	@DeleteMapping("/delete")
	public List<User> delete(@RequestParam int user_id) {
		return userService.delAUser(user_id);
	}
	
	@PostMapping("/update")
	public ResponseEntity<User> update(@RequestBody UpdateCtrl user) {
		return ResponseEntity.ofNullable(userService.updateAUser(user));							
	}
	
	@GetMapping("/register")
	public String respondR(){
		return "OK";
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUsers() {
		System.out.println("67");
		return ResponseEntity.ok(userService.getAllUsers());	
	}
	
	@GetMapping("/update")
	public String respondU(){
		return "OK";
	}

	
	
}

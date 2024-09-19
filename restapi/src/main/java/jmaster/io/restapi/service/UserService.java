package jmaster.io.restapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jmaster.io.restapi.cotroller.UpdateCtrl;
import jmaster.io.restapi.model.Role;
import jmaster.io.restapi.model.User;
import jmaster.io.restapi.repository.RoleRepository;
import jmaster.io.restapi.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public User createUser(int user_id, String userName, String passWord, Set<Integer> roleId) {
		Set<Integer> role_id = new HashSet<>();
		for(int roleid:roleId) {											//Lúc đầu ta truyền vào 1 user có n quyền, tạo roles ms và thêm vào các
			Optional<Role> role = roleRepository.findById(roleid);			//Role hợp lệ cho riêng user đó
			role.ifPresent(value->role_id.add(value.getRole_set()));									//<=> roles.add(role) vì role là Optional
		}
		User user = new User(user_id, userName, passWord, role_id);
		return userRepository.save(user);
	}
	
	public User findAUser(String[] unAndPw) {
		Optional<User> user = userRepository.findByUsername(unAndPw[0]);
		boolean checkSame = user.filter(value->value.getPassword().equals(unAndPw[1])).isPresent();
		if(checkSame)  return userRepository.save(user.get());			//Optional có get để convert sang User =)), đọc gợi ý code ms bt =))
		else return null;
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<User> delAUser(int user_id) {
		userRepository.deleteById(user_id);
		return userRepository.findAll();
	}
	
	public User updateAUser(UpdateCtrl userUpdate) {
		Optional<User> user = userRepository.findById(userUpdate.getUser_id());
		Set<Integer> roleSet = new HashSet<>();
		for(int roleId:userUpdate.getRole_id()) {								//Lúc đầu ta truyền vào 1 user có n quyền, tạo roles ms và thêm vào các
			Optional<Role> role = roleRepository.findById(roleId);			//Role hợp lệ cho riêng user đó
			role.ifPresent(value->roleSet.add(value.getRole_set()));
		}//<=>user.setRoles(roles)
		user.ifPresent(value->{value.setUsername(userUpdate.getUsername());value.setRole_id(roleSet);value.setPassword(userUpdate.getPassword());});					//Bởi vì trong ifPresent ktra gtri của user, nếu khác null thì nó
																		//cho phép giá trị đó đc thực hiện pthuc, lấy biến, v.v....
		//<=>userRepository.save(user);
		return userRepository.save(user.get());
	}
	
	public boolean isAdmin(User user) {
		return user.getRole_set().stream().anyMatch(role->role.equals(1));
		//user.getRoles() là 1 stream, anyMatch sẽ nhận tham số là mỗi ptu trong stream và lấy ptu thực hiện biểu thức điều kiện 
		//chỉ cần bất kì ptu nào đúng thì câu lệnh trên trả về true
	}
	
	public boolean isUser(User user) {
		return user.getRole_set().stream().anyMatch(role->role.equals(2));
	}
	
}

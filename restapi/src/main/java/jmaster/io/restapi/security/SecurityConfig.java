package jmaster.io.restapi.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import jmaster.io.restapi.model.Role;
import jmaster.io.restapi.model.User;
import jmaster.io.restapi.repository.RoleRepository;
import jmaster.io.restapi.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig{//Cũ implements UserDetailsService=> gây lỗi vì gặp bải 1 bean overrideed=>ko rõ nguồn gốc và tham số, ý nói là username
	@Autowired UserRepository userRepository;
	@Autowired RoleRepository roleRepository;
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("26"));
				Set<String> role_str = new HashSet<String>();
				for (int roleid : user.getRole_set()) {
					Role role = roleRepository.findById(roleid).orElseThrow();
					role_str.add(role.getRole());
					System.out.println(role.getRole()+39);
				}
				String[] roles = role_str.toArray(new String[0]);
				System.out.println(roles+" 42");
				UserDetails userDetails = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder().username(user.getUsername()).password(user.getPassword())
						.roles(roles).build();//<=>.authorities("ROLE_USER","ROLE_ADMIN")
				System.out.println(userDetails.getAuthorities());
				return userDetails;
			}
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests(
	            auth -> auth.requestMatchers("/home","/login","/register").permitAll()
	            .requestMatchers("/update", "/delete", "/list").hasAuthority("ROLE_ADMIN") //Ở hàm bên trên thì có .roles()<=>authorities(ROLE_) nên cẩn thận
	            .requestMatchers("/list").hasAuthority("ROLE_USER") 	//dòng này để chỉ cho request từ user
	            .anyRequest().authenticated()					//mọi req khác permitAll đều cần xác thực
	           )
	            .formLogin(formLogin -> formLogin
	                    //.loginPage()					//Thực ra ko càn vì mặc đ là /login
	                    //.usernameParameter() 			//mặc định là username => ko cần dòng này, nếu đn tham số tên ngdung là cái khác thì ms cần
	                    .defaultSuccessUrl("/hello", true)
	                    .permitAll()
	            )
	            //.rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl...")) chưa biết ... làm sau
	            .logout(logout -> logout.permitAll());
	    		
	 
	    return http.build();
	}
}

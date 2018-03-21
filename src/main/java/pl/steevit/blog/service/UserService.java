package pl.steevit.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import pl.steevit.blog.entity.User;

public interface UserService extends UserDetailsService {

	List<User> findAll();
	Page<User> findAll(Pageable pageable);
	User find(Long id);
	User create(User user);
	User edit(User user);
	void delete(Long id);
	User findByUsername(String username);

}

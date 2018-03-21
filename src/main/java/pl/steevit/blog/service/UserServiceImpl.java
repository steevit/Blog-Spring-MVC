package pl.steevit.blog.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.steevit.blog.dao.UserDAO;
import pl.steevit.blog.entity.User;
import pl.steevit.blog.entity.UserRole;

@Service
@Primary
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public List<User> findAll() {
		return this.userDAO.findAll();
	}
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		return this.userDAO.findAll(pageable);
	}
	
	@Override
	public User find(Long id) {
		return this.userDAO.findOne(id);
	}
	
	@Override
	public User create(User user) {
		return this.userDAO.save(user);
	}
	
	@Override
	public User edit(User user) {
		return this.userDAO.save(user);
	}
	
	@Override
	public void delete(Long id) {
		this.userDAO.delete(id);
	}
	
	@Override
	public User findByUsername(String username) {
		return this.userDAO.findByUsername(username);
	}
	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username)
		throws UsernameNotFoundException {

		User user = userDAO.findByUsername(username);
		List<GrantedAuthority> authorities =
                                      buildUserAuthority(user.getRoles());

		return buildUserForAuthentication(user, authorities);

	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(
			User user,
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(),
			user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}


}

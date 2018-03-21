package pl.steevit.blog.dao;

import pl.steevit.blog.entity.User;

public interface UserFinder {

	public User findByUsername(String username);
	
}

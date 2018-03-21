package pl.steevit.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.steevit.blog.dao.UserDAO;
import pl.steevit.blog.entity.User;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {

	private UserDAO userDAO;
	
	@Autowired
	public UsersApiController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> findAll() {
		return userDAO.findAll();
	}
	
}

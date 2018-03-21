package pl.steevit.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.steevit.blog.entity.User;
import pl.steevit.blog.service.UserService;
import pl.steevit.blog.utils.PageWrapper;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
    private UserService userService;

    @RequestMapping("/")
    public String list(Model model, Pageable pageable) {
    	PageWrapper<User> page = new PageWrapper<User>(userService.findAll(pageable), "/users/");
    	model.addAttribute("page", page);
        return "users/list";
    }

}

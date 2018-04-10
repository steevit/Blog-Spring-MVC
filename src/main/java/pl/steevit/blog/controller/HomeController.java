package pl.steevit.blog.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.steevit.blog.entity.Post;
import pl.steevit.blog.service.PostService;
import pl.steevit.blog.utils.PageWrapper;

@Controller
public class HomeController {
	
	@Autowired
	private PostService postService;

	@RequestMapping("/")
	public String index(Model model, Pageable pageable) {
		List<Post> latest5Posts = postService.findAll().stream()
				.limit(5).collect(Collectors.toList());;
		model.addAttribute("latest5posts", latest5Posts);
		
		Sort sort = new Sort(new Order(Direction.DESC, "date"));
		if (pageable.getPageSize() == 20) {
			pageable = new PageRequest(0, 3, sort);
		}
    	PageWrapper<Post> page = new PageWrapper<Post>(postService.getAllPosts(pageable), "/");
    	model.addAttribute("page", page);
		
		return "index";
	}
	
	@RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
	public String logoutDo(HttpServletRequest request, HttpServletResponse response){
	HttpSession session= request.getSession(false);
	    SecurityContextHolder.clearContext();
	         session= request.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        for(Cookie cookie : request.getCookies()) {
	            cookie.setMaxAge(0);
	        }

	    return "logout";
	}
}

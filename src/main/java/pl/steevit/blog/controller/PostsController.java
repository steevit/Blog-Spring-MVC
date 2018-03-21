package pl.steevit.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.steevit.blog.entity.Post;
import pl.steevit.blog.service.NotificationService;
import pl.steevit.blog.service.PostService;
import pl.steevit.blog.service.UserService;
import pl.steevit.blog.utils.PageWrapper;

@Controller
@RequestMapping("/posts")
public class PostsController {
	
        @Autowired
        private PostService postService;
        
        @Autowired
        private UserService userService;

        @Autowired
        private NotificationService notifyService;

        @RequestMapping("/")
        public String list(Model model, Pageable pageable) {
        	PageWrapper<Post> page = new PageWrapper<Post>(postService.getAllPosts(pageable), "/posts/");
        	model.addAttribute("page", page);
            return "posts/list";
        }
        
        @RequestMapping("/view/{id}")
        public String view(@PathVariable("id") Long id, Model model) {
            Post post = postService.find(id);
            if (post == null) {
                notifyService.addErrorMessage("Cannot find post #" + id);
                return "redirect:/";
            }
            model.addAttribute("post", post);
            return "posts/view";
        }
        
        @RequestMapping(value="/create")
        public String create(Model model) {
            Post post = new Post();
            model.addAttribute("post", post);
            return "posts/create";
        }
        
        @RequestMapping(value="/create", method=RequestMethod.POST)
        public String processCreate(@ModelAttribute(value="post") Post post) {
            post.setAuthor(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            postService.create(post);
            return "redirect:/";
        }
        
        @RequestMapping("/delete/{id}")
        public String delete(@PathVariable("id") Long id, Model model) {
            Post post = postService.find(id);
            if (post == null) {
                notifyService.addErrorMessage("Cannot find post #" + id);
                return "redirect:/";
            }
            model.addAttribute("post", post);
            return "posts/delete";
        }
        
        @RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
        public String processDelete(@PathVariable("id") Long id) {
            postService.delete(id);
            return "redirect:/posts/";
        }
        
        @RequestMapping("/edit/{id}")
        public String edit(@PathVariable("id") Long id, Model model) {
            Post post = postService.find(id);
            if (post == null) {
                notifyService.addErrorMessage("Cannot find post #" + id);
                return "redirect:/";
            }
            model.addAttribute("post", post);
            return "posts/edit";
        }
        
        @RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
        public String processEdit(@PathVariable("id") Long id, 
        		@ModelAttribute(value="post") Post post) {
        	Post prevPost = postService.find(id);
        	post.setDate(prevPost.getDate());
            postService.edit(post);
            return "redirect:/posts/";
        }
}
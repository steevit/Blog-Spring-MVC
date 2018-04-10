package pl.steevit.blog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.steevit.blog.entity.User;
import pl.steevit.blog.service.UserService;

@RestController
public class UsersApiController {
	
	public static final Logger logger = LoggerFactory.getLogger(UsersApiController.class);

	@Autowired
    private UserService userService;
	
	 @RequestMapping(value="/user", method=RequestMethod.POST)
	 public ResponseEntity<?> save(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);
	    
	    HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	 }
	 
	 @GetMapping("/user/{id}")
	 public ResponseEntity<User> get(@PathVariable("id") long id) {
	    User user = userService.find(id);
	    return ResponseEntity.ok().body(user);
	 }
	
	 @GetMapping(value = "/user")
	 public ResponseEntity<List<User>> list() {
		List<User> users = userService.findAll();
		return ResponseEntity.ok().body(users);
	 }
	
	 @PutMapping("/user/{id}")
	 public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody User user) {
	    userService.edit(user);
	    return ResponseEntity.ok().body("User has been updated successfully.");
	 }

	   /*---Delete a book by id---*/
	 @DeleteMapping("/user/{id}")
	 public ResponseEntity<?> delete(@PathVariable("id") long id) {
	    userService.delete(id);
	    return ResponseEntity.ok().body("User has been deleted successfully.");
	 }
	
}

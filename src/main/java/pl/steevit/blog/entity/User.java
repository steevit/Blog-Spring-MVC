package pl.steevit.blog.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query="select u from User u order by u.id desc")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(length = 60)
	private String passwordHash;
	
	@Column(length = 100)
	private String fullName;
	
	@Column(nullable = false)
	private boolean enabled;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRole> roles = new HashSet<UserRole>();
	
	@OneToMany(mappedBy = "author")
	private Set<Post> posts = new HashSet<Post>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Set<UserRole> getRoles() {
		return roles;
	}
		
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
	
	public Set<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	public User() {
	}
	
	public User(String username, String fullName, boolean enabled) {
		this.username = username;
		this.fullName = fullName;
		this.enabled = enabled;
	}
	
	public User(Long id, String username, String fullName, boolean enabled) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username +
				", passwordHash='" + passwordHash +
				", fullName='" + fullName +
			"}";
	}

}
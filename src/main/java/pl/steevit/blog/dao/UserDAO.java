package pl.steevit.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.steevit.blog.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long>, UserFinder {
	
	//@Query("SELECT u FROM User u LEFT JOIN u.author ORDER BY p.date DESC")	
	//Page<Post> findPosts(Pageable pageable);
	
	@Override
    @Query
    public List<User> findAll();
	
	@Override
    @Query
    public Page<User> findAll(Pageable pageable);
	
}

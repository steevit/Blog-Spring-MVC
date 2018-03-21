package pl.steevit.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pl.steevit.blog.entity.Post;

public interface PostService {
	
    List<Post> findAll();
    /*List<Post> findLatest5();*/
    Page<Post> getAllPosts(Pageable pageable);
    Post find(Long id);
    Post create(Post post);
    Post edit(Post post);
    void delete(Long id);
}
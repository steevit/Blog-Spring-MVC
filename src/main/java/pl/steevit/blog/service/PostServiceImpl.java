package pl.steevit.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pl.steevit.blog.dao.PostDAO;
import pl.steevit.blog.entity.Post;

@Service
@Primary
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;

	@Override
	public List<Post> findAll() {
		return this.postDAO.findAll();
	}

	/*@Override
	public List<Post> findLatest5() {
		return this.postDAO.findLatest5Posts(new PageRequest(0, 5));
	}*/
	
	@Override
	public Page<Post> getAllPosts(Pageable pageable) {
	    Page<Post> blogList = postDAO.findPosts(pageable);
	    return blogList;
	}
	
	@Override
	public Post find(Long id) {
		return this.postDAO.findOne(id);
	}
	
	@Override
	public Post create(Post post) {
		return this.postDAO.save(post);
	}
	
	@Override
	public Post edit(Post post) {
		return this.postDAO.save(post);
	}
	
	@Override
	public void delete(Long id) {
		this.postDAO.delete(id);
	}

}

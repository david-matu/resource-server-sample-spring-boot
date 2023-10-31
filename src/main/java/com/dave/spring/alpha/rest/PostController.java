package com.dave.spring.alpha.rest;

import java.net.URI;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//import com.dave.spring.alpha.AppBeans;
import com.dave.spring.alpha.IDGen;
import com.dave.spring.alpha.model.Post;
import com.dave.spring.alpha.repo.PostRepository;
import com.dave.spring.alpha.repo.PostRepositoryPaginator;
//import com.dave.spring.alpha.user.UserRepository;

@RestController
@RequestMapping(path="/api/posts", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class PostController {
	RestTemplate rest = new RestTemplate();
	
	//@Autowired
	private final IDGen idGen;
	
	private final PostRepository postRepo;
	private final PostRepositoryPaginator postRepoPager; 
	
	
	public PostController(IDGen idGen, PostRepository postRepo, PostRepositoryPaginator postRepoPager) {	//UserRepository userRepo	//PostMessagingService postMessagingService
		this.idGen = idGen;
		this.postRepo = postRepo;
		this.postRepoPager = postRepoPager;
		//this.userRepo = userRepo;
	}
	
	@GetMapping
	public Iterable<Post> allPosts() {
		return postRepo.findAll();
	}
	
	@GetMapping(params="recent")
	public Iterable<Post> recentPosts() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("datePosted").descending());
		
		//Page<Post> postPage = postRepo.findAll(page);
		
		return postRepoPager.findAll(page).getContent();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> postById(@PathVariable("id") long id) {
		Optional<Post> optPost = postRepo.findById(id);
		
		if(optPost.isPresent()) {
			return new ResponseEntity<>(optPost.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Post createPost(@RequestBody Post post) {
		post.setPostId(this.idGen.generatePostID());
		post.setDatePosted(new Timestamp(System.currentTimeMillis()));
		
		System.out.println("RECEIVED BLOG POST. These are details:\n" + post.toString());
		
		//Send to message broker
		//postMessageService.sendPost(post);
		
		Post savedPost = postRepo.save(post); 
		return savedPost;
		//return postRepo.save(post);
	}
	
	@PutMapping(path="/{postId}", consumes="application/json")
	public Post updatePost(@PathVariable("postId") Long postId, @RequestBody Post post) {
		post.setPostId(postId);
		return postRepo.save(post);
	}
	
	@PatchMapping(path="/{postId}", consumes="application/json")
	public Post patchBlogPost(@PathVariable("postId") Long postId, @RequestBody Post patch) {
		Post blogPost = postRepo.findById(postId).get();
		
		if(patch.getTitle() != null) {
			blogPost.setTitle(patch.getTitle());
		}
		
		if(patch.getDescription() != null) {
			blogPost.setDescription(patch.getDescription());
		}
		
		return postRepo.save(blogPost);
	}
	
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePost(@PathVariable("postId") Long postId) {
		try {
			postRepo.deleteById(postId);
		} catch(EmptyResultDataAccessException e) {}
	}
	
	@GetMapping("/rest/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable("postId") long postId) {
		//return rest.getForObject("http://localhost:8080/api/posts/{id}", Post.class, postId);
		
		/*
		Post optPost = rest.getForObject("http://localhost:8080/api/posts/{id}", Post.class, postId);
		
		System.out.println(optPost.toString());
		
		return new ResponseEntity<>(optPost, HttpStatus.OK);
		*/
		
		Map<String, Long> urlVariables = new HashMap<>();
		urlVariables.put("id", postId);
		URI url = UriComponentsBuilder
					.fromHttpUrl("http://localhost:8080/api/posts/{id}")
					.build(urlVariables);		
		
		//return new ResponseEntity<>(rest.getForObject(url, Post.class), HttpStatus.OK);
		//return new ResponseEntity<>(rest.getForObject(url, Post.class), HttpStatus.OK);
		ResponseEntity<Post> responseEntity = rest.getForEntity(url, Post.class);
		
		System.out.println("Fetched time: " + responseEntity.getHeaders().getDate());
		return responseEntity;
	}
	
	public void updatePost(Post blogPost) {
		rest.put("http://localhost:8080/api/posts/{postid}", blogPost, blogPost.getPostId());
	}
	
	public void deletePost(Post post) {
		rest.delete("http://localhost:8080/api/posts/{postId}", post.getPostId());
	}
	
	public Post createBlogPost(Post post) {
		return rest.postForObject("http://localhost:8080/api/posts/{postId}", post, Post.class);
	}
	
	public java.net.URI createBloggPost(Post post) {
		return rest.postForLocation("http://localhost:8080/api/posts/{postId}", post);
	}
	
	public Post postBlogPost(Post post) {
		ResponseEntity<Post> responseEntity = rest.postForEntity("http://localhost:8080/api/posts/{postId}", post, Post.class);
		
		System.out.println("New resource created at: " + responseEntity.getHeaders().getLocation());
		return responseEntity.getBody();
	}
	/*
	public Comment getCommentsByPostId(long postId) {
		
	}
	*/
	/*
	@Bean
	public IDGen initIDGen() {
		return new IDGen(this.postRepo);
	}
	*/
}
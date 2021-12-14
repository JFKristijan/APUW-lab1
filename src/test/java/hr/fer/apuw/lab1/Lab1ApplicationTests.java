package hr.fer.apuw.lab1;

import hr.fer.apuw.lab1.dto.PostDTO;
import hr.fer.apuw.lab1.dto.UserDTO;
import hr.fer.apuw.lab1.models.Post;
import hr.fer.apuw.lab1.models.User;
import hr.fer.apuw.lab1.repository.PostRepository;
import hr.fer.apuw.lab1.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lab1ApplicationTests {

	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  PostRepository postRepository;
	@Autowired
	private  BCryptPasswordEncoder bCryptPasswordEncoder;

	private final RestTemplate restTemplate = new RestTemplate();

	private final String url = "http://localhost:8080";


	@Test
	void contextLoads() {
	}


	@Test
	@Order(1)
	public void createUserTest(){
		String username = "user1";
		String password = "pass";
		HttpEntity<?> entity = new HttpEntity<>(new User(username,password));
		ResponseEntity<UserDTO> responseEntity =
				restTemplate.exchange(url+"/users",
					HttpMethod.POST,
					entity,
					UserDTO.class);

		assertEquals(201,responseEntity.getStatusCodeValue());
		assertTrue(responseEntity.hasBody());
		UserDTO userDTO = responseEntity.getBody();
		assertEquals(username,userDTO.getUsername());
		assertEquals(new ArrayList<>(),userDTO.getPosts());

	}

	@Test
	@Order(2)
	public void getUsers(){
		String username = "user1";
		ResponseEntity<UserDTO[]> responseEntity =
				restTemplate.exchange(
						url+"/users",
						HttpMethod.GET,
						null,
						UserDTO[].class);

		assertEquals(200,responseEntity.getStatusCodeValue());
		assertTrue(responseEntity.hasBody());
		UserDTO[] userDTO = responseEntity.getBody();
		assertEquals(1, userDTO.length);
		assertEquals(username,userDTO[0].getUsername());

	}

	@Test
	@Order(3)
	public void getOneUser(){
		String username = "user1";
		ResponseEntity<UserDTO> responseEntity =
				restTemplate.exchange(
						url+"/users/1",
						HttpMethod.GET,
						null,
						UserDTO.class);

		assertEquals(200,responseEntity.getStatusCodeValue());
		assertTrue(responseEntity.hasBody());
		UserDTO userDTO = responseEntity.getBody();
		assertEquals(username,userDTO.getUsername());
	}

	@Test
	@Order(4)
	public void updateUser(){
		String username = "user1user";
		String password = "pass";
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("user1",password);
		ResponseEntity<Void> responseEntity =
				restTemplate.exchange(
						url+"/users/1",
						HttpMethod.PUT,
						new HttpEntity<>(new User(username,null), headers),
						Void.class);

		assertEquals(204, responseEntity.getStatusCodeValue());
		User user = userRepository.findByUsername(username).orElse(null);
		assert user != null;
		assertEquals(1,user.getId());

	}


	@Test
	@Order(5)
	public void deleteUser(){
		String username = "user1user";
		String password = "pass";
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username,password);
		ResponseEntity<Void> responseEntity =
				restTemplate.exchange(
						url+"/users/1",
						HttpMethod.DELETE,
						new HttpEntity<>(headers),
						Void.class);

		assertEquals(204, responseEntity.getStatusCodeValue());
		Optional<User> user = userRepository.findByUsername(username);
		assertFalse(user.isPresent());
		user = userRepository.findById(1L);
		assertFalse(user.isPresent());

	}

	@Test
	@Order(6)
	public void createPost(){
		createUserTest();
		String username = "user1";
		String password = "pass";
		String hello = "Hello.";
		Post post = new Post();
		post.setText(hello);
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username,password);
		ResponseEntity<PostDTO> responseEntity =
				restTemplate.exchange(
						url+"/posts",
						HttpMethod.POST,
						new HttpEntity<>(post,headers),
						PostDTO.class);

		assertEquals(201, responseEntity.getStatusCodeValue());
		Post posted = postRepository.findById(1L).orElse(null);
		assertNotNull(posted);;
		assertEquals(hello,posted.getText());
		assertEquals(username,posted.getPoster().getUsername());
	}

	@Test
	@Order(7)
	public void getPosts(){
		String username = "user1";
		String password = "pass";
		String hello = "Hello.";
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username,password);
		ResponseEntity<PostDTO[]> responseEntity =
				restTemplate.exchange(
						url+"/posts",
						HttpMethod.GET,
						new HttpEntity<>(headers),
						PostDTO[].class);

		assertEquals(200,responseEntity.getStatusCodeValue());
		assertTrue(responseEntity.hasBody());
		PostDTO[] postDTO = responseEntity.getBody();
		assertEquals(1, postDTO.length);
		assertEquals(username,postDTO[0].getPoster().getUsername());
		assertEquals(hello,postDTO[0].getText());
	}

	@Test
	@Order(8)
	public void updatePost(){
		String username = "user1";
		String password = "pass";
		String hellohello = "Hello. Hello.";
		Post post = new Post();
		post.setText(hellohello);
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username,password);
		ResponseEntity<Void> responseEntity =
				restTemplate.exchange(
						url+"/posts/1",
						HttpMethod.PUT,
						new HttpEntity<>(post,headers),
						Void.class);

		assertEquals(204, responseEntity.getStatusCodeValue());
		Post posted = postRepository.findById(1L).orElse(null);
		assertNotNull(posted);
		assertEquals(hellohello,posted.getText());

	}

	@Test
	@Order(9)
	public void getPostsByUser(){
		String username = "user1";
		String password = "pass";
		String hello = "Hello. Hello.";
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username,password);
		ResponseEntity<PostDTO[]> responseEntity =
				restTemplate.exchange(
						url+"/users/2/posts",
						HttpMethod.GET,
						new HttpEntity<>(headers),
						PostDTO[].class);

		assertEquals(200,responseEntity.getStatusCodeValue());
		assertTrue(responseEntity.hasBody());
		PostDTO[] postDTO = responseEntity.getBody();
		assertEquals(1, postDTO.length);
		assertEquals(username,postDTO[0].getPoster().getUsername());
		assertEquals(hello,postDTO[0].getText());
	}

	@Test
	@Order(10)
	public void deletePost(){
		String username = "user1";
		String password = "pass";
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username,password);
		ResponseEntity<Void> responseEntity =
				restTemplate.exchange(
						url+"/posts/1",
						HttpMethod.DELETE,
						new HttpEntity<>(headers),
						Void.class);

		assertEquals(204, responseEntity.getStatusCodeValue());
		Optional<Post> post = postRepository.findById(1L);
		assertFalse(post.isPresent());

		responseEntity =
				restTemplate.exchange(
						url+"/posts/1",
						HttpMethod.DELETE,
						new HttpEntity<>(headers),
						Void.class);

		assertEquals(204, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(11)
	public void getNonExistentPost(){
		String username = "user1";
		String password = "pass";

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username,password);
		try {
			ResponseEntity<Void> responseEntity =
					restTemplate.exchange(
							url + "/posts/15",
							HttpMethod.GET,
							new HttpEntity<>(headers),
							Void.class);
		}catch (HttpClientErrorException exc){
			//dirty fix for not implmenting custom ResponseErrorHandler
		}

	}




}

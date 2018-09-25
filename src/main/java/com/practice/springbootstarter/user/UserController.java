package com.practice.springbootstarter.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.File;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

	@Autowired
	private UserDaoService userDaoService;

	@GetMapping("/users")
	public List<User> retrieveAllUser() {
		return userDaoService.findAll();
	}

	@GetMapping("/users/{id}")
	public Resource<User> retrieveOne(@PathVariable int id) {
		User user = userDaoService.findOne(id);

		if (user == null)
			throw new UserNotFoundException("id- " + id);

		Resource<User> resource = new Resource<>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUser());
		resource.add(linkTo.withRel("all-users"));

		return resource;
	}

	@PostMapping("/users/save")
	public ResponseEntity<Object> save(@Valid @RequestBody User user) {
		User savedUser = userDaoService.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/users/delete/{id}")
	public User deleteById(@PathVariable int id) {
		User user = userDaoService.deleteById(id);

		if (user == null)
			throw new UserNotFoundException("id- " + id);

		return user;
	}

	@GetMapping("/users/csv")
	@Produces("text/csv")
	public Response usersCSV() {
		String filePath = "D:\\result.csv";
		CSVUtility.convertFileToCSV(filePath);
		javax.ws.rs.core.Response.ResponseBuilder response = Response.ok((Object) new File(filePath));
		response.header("Content-Disposition", "attachment; filename=\"result1.csv\"");
		return response.build();
	}
}

package com.laramaricordeiro.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.laramaricordeiro.workshopmongo.domain.Post;
import com.laramaricordeiro.workshopmongo.domain.User;
import com.laramaricordeiro.workshopmongo.dto.UserDTO;
import com.laramaricordeiro.workshopmongo.services.UserService;

@RestController
@RequestMapping(value ="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(method=RequestMethod.GET)
	//ou @GetMapping
	public ResponseEntity<java.util.List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	//ou @GetMapping
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);	
		return ResponseEntity.ok().body(new UserDTO(obj));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	//ou @PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
		User obj = service.fromDTO(objDto);	
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	//ou @GetMapping
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);	
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	//ou @PostMapping
	public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id) {
		User obj = service.fromDTO(objDto);	
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
	//ou @GetMapping
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User obj = service.findById(id);	
		return ResponseEntity.ok().body(obj.getPosts());
	}

}

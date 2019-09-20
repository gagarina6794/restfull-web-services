package com.in28minutes.rest.webservices.restfulwebservices.controller;

import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.model.Post;
import com.in28minutes.rest.webservices.restfulwebservices.model.User;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PostJpaRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repository.UserJpaRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJpaController {
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public UserJpaController(UserJpaRepository userJpaRepository, PostJpaRepository postJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.postJpaRepository = postJpaRepository;
    }

    @GetMapping("/jpa/users")
    public List<User> getAll() {
        return userJpaRepository.findAll();
    }

    @GetMapping(path = "/jpa/users/{id}")
    public Resource<User> getUser(@PathVariable int id) {
        final User user = userJpaRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity saveUser(@Valid @RequestBody User user) {
        final User savedUser = userJpaRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userJpaRepository.deleteById(id);
    }

    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable int id) {
        final User user = userJpaRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        return user.getPosts();
    }

    @GetMapping(path = "/jpa/users/{id}/posts/{postid}")
    public Post getUserPost(@PathVariable int id, @PathVariable int postid) {
        final User user = userJpaRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        return postJpaRepository.findById(postid).orElse(null);
    }

    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity<Object> saveUserPost(@PathVariable int id, @RequestBody Post post) {
        final User user = userJpaRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        post.setUser(user);
        postJpaRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}

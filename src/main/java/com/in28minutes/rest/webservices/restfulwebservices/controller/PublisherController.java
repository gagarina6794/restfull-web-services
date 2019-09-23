package com.in28minutes.rest.webservices.restfulwebservices.controller;

import com.in28minutes.rest.webservices.restfulwebservices.model.Book;
import com.in28minutes.rest.webservices.restfulwebservices.model.Publisher;
import com.in28minutes.rest.webservices.restfulwebservices.repository.BookRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PublisherRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublisherController {
    private final BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public PublisherController(BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @DeleteMapping("books/{id}")
    public void removeBook(@PathVariable int id) {
        final Book book = bookRepository.findById(id).orElse(new Book());
        bookRepository.delete(book);
    }

    @GetMapping("books/{id}")
    public Book getBook(@PathVariable int id) {
        return bookRepository.findById(id).orElse(new Book());
    }

    @DeleteMapping("publisher/{id}")
    public void removePublisher(@PathVariable int id) {
        final Publisher publisher = publisherRepository.findById(id).orElse(new Publisher());
        publisherRepository.delete(publisher);
    }

    @GetMapping("publisher/{id}")
    public Publisher getPublisher(@PathVariable int id) {
        return publisherRepository.findById(id).orElse(new Publisher());
    }
}

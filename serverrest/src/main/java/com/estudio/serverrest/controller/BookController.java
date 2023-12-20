package com.estudio.serverrest.controller;

import com.estudio.serverrest.client.BookGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {
    
    @Autowired
    BookGrpcClient bookGrpcClient;
    
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(this.bookGrpcClient.addBook(book));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(this.bookGrpcClient.getBooks());
    }
}

package com.estudio.servergrpc.service;

import com.estudio.servergrpc.model.Book;
import com.estudio.servergrpc.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getBooks() {
        return bookRepository.findAll(); 
    }
    public Book addBook(Book book) {
        return this.bookRepository.save(book);
    }
}

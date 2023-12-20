package com.estudio.servergrpc.controller;

import com.estudio.miprotobuf.BookObject;
import com.estudio.miprotobuf.BookObjectList;
import com.estudio.miprotobuf.Empty;
import com.estudio.miprotobuf.booksServiceGrpcGrpc;
import com.estudio.servergrpc.model.Book;
import com.estudio.servergrpc.service.BookService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceGrpc extends booksServiceGrpcGrpc.booksServiceGrpcImplBase {

    @Autowired
    private BookService bookService;

    @Override
    public void getAllBookObjects(Empty request, StreamObserver<BookObjectList> responseObserver) {
        List<BookObject> bookObjects = new ArrayList<>();

        List<Book> listBooks = this.bookService.getBooks();

        for (Book book : listBooks) {
            BookObject bookObject = BookObject.newBuilder()
                    .setId(book.getId())
                    .setName(book.getName())
                    .setAuthor(book.getAuthor())
                    .build();
            bookObjects.add(bookObject);
        }

        BookObjectList listToReturn = BookObjectList.newBuilder()
                .addAllBookObjects(bookObjects)
                .build();

        responseObserver.onNext(listToReturn);
        responseObserver.onCompleted();
    }

    @Override
    public void addBookObject(BookObject request, StreamObserver<BookObject> responseObserver) {
        Book book = new Book();
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());

        book = this.bookService.addBook(book);

        BookObject bookObject = BookObject.newBuilder()
                .setId(book.getId())
                .setName(book.getName())
                .setAuthor(book.getAuthor())
                .build();

        responseObserver.onNext(bookObject);
        responseObserver.onCompleted();
    }
}

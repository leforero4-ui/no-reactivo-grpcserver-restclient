package com.estudio.serverrest.client;

import com.estudio.miprotobuf.BookObject;
import com.estudio.miprotobuf.BookObjectList;
import com.estudio.miprotobuf.Empty;
import com.estudio.miprotobuf.booksServiceGrpcGrpc;
import com.estudio.serverrest.controller.Book;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookGrpcClient {

    public Book addBook(Book book) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 4444).usePlaintext().build();
        //ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("dns:///localhost:4444").usePlaintext().build();

        BookObject bookObject = BookObject.newBuilder()
                .setName(book.getName())
                .setAuthor(book.getAuthor())
                .build();
        
        booksServiceGrpcGrpc.booksServiceGrpcBlockingStub booksServiceGrpcBlockingStub = booksServiceGrpcGrpc.newBlockingStub(managedChannel);
        bookObject = booksServiceGrpcBlockingStub.addBookObject(bookObject);
        managedChannel.shutdown();

        book = new Book();
        book.setId(bookObject.getId());
        book.setName(bookObject.getName());
        book.setAuthor(bookObject.getAuthor());
        
        return book;
        
    }

    public List<Book> getBooks() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 4444).usePlaintext().build();
        //ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("dns:///localhost:4444").usePlaintext().build();

        Empty empty = Empty.newBuilder().build();
        
        booksServiceGrpcGrpc.booksServiceGrpcBlockingStub booksServiceGrpcBlockingStub = booksServiceGrpcGrpc.newBlockingStub(managedChannel);
        BookObjectList bookObjectList = booksServiceGrpcBlockingStub.getAllBookObjects(empty);
        managedChannel.shutdown();

        List<BookObject> bookObjects = bookObjectList.getBookObjectsList();
        List<Book> books = new ArrayList<>();
        for(BookObject bookObject : bookObjects) {
            Book book = new Book();
            book.setId(bookObject.getId());
            book.setName(bookObject.getName());
            book.setAuthor(bookObject.getAuthor());

            books.add(book);
        }
        
        return books;
    }
}

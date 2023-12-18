package com.example.epam_ai_task_2_1.service;

import com.example.epam_ai_task_2_1.repository.AuthorRepository;
import com.example.epam_ai_task_2_1.repository.BookRepository;
import com.example.epam_ai_task_2_1.repository.GenreRepository;
import com.example.epam_ai_task_2_1.repository.entity.Author;
import com.example.epam_ai_task_2_1.repository.entity.Book;
import com.example.epam_ai_task_2_1.repository.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    // Create
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Read
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Update
    public Book updateBook(Long id, Book updatedBook) {
        if (bookRepository.existsById(id)) {
            updatedBook.setId(id);
            return bookRepository.save(updatedBook);
        } else {
            // Handle the case where the book with the given id does not exist
            return null;
        }
    }

    // Delete
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Search by Title
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }

    // Search by Genre
    public List<Book> searchByGenre(String genreName) {
        Optional<Genre> genre = genreRepository.findByNameIgnoreCase(genreName);
        return genre.map(bookRepository::findByGenre).orElse(null);
    }

    // Search by Author
    public List<Book> searchByAuthor(String authorName) {
        Optional<Author> author = authorRepository.findByNameIgnoreCase(authorName);
        return author.map(bookRepository::findByAuthor).orElse(null);
    }
}

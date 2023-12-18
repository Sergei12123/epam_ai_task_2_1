package com.example.epam_ai_task_2_1;

import com.example.epam_ai_task_2_1.repository.AuthorRepository;
import com.example.epam_ai_task_2_1.repository.BookRepository;
import com.example.epam_ai_task_2_1.repository.GenreRepository;
import com.example.epam_ai_task_2_1.repository.entity.Author;
import com.example.epam_ai_task_2_1.repository.entity.Book;
import com.example.epam_ai_task_2_1.repository.entity.Genre;
import com.example.epam_ai_task_2_1.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void getAllBooks() {
        List<Book> books = new ArrayList<>();
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(books, result);
    }

    @Test
    void getBookById() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        Optional<Book> optionalBook = Optional.of(book);

        Mockito.when(bookRepository.findById(bookId)).thenReturn(optionalBook);

        Optional<Book> result = bookService.getBookById(bookId);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    void saveBook() {
        Book bookToSave = new Book();
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(bookToSave);

        Book result = bookService.saveBook(new Book());

        assertNotNull(result);
        assertEquals(bookToSave, result);
    }

    @Test
    void updateBook() {
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setId(bookId);

        Book updatedBook = new Book();

        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        Mockito.when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNotNull(result);
        assertEquals(updatedBook, result);
    }

    @Test
    void deleteBook() {
        Long bookId = 1L;

        bookService.deleteBook(bookId);

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }

    @Test
    void searchByTitle() {
        String title = "Sample Title";
        List<Book> books = new ArrayList<>();

        Mockito.when(bookRepository.findByTitleIgnoreCase(title)).thenReturn(books);

        List<Book> result = bookService.searchByTitle(title);

        assertEquals(books, result);
    }

    @Test
    void searchByGenre() {
        String genreName = "Sample Genre";
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName(genreName);

        List<Book> books = new ArrayList<>();

        Mockito.when(genreRepository.findByNameIgnoreCase(genreName)).thenReturn(Optional.of(genre));
        Mockito.when(bookRepository.findByGenre(genre)).thenReturn(books);

        List<Book> result = bookService.searchByGenre(genreName);

        assertEquals(books, result);
    }

    @Test
    void searchByAuthor() {
        String authorName = "Sample Author";
        Author author = new Author();
        author.setId(1L);
        author.setName(authorName);

        List<Book> books = new ArrayList<>();

        Mockito.when(authorRepository.findByNameIgnoreCase(authorName)).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.findByAuthor(author)).thenReturn(books);

        List<Book> result = bookService.searchByAuthor(authorName);

        assertEquals(books, result);
    }
}


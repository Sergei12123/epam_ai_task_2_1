package com.example.epam_ai_task_2_1;

import com.example.epam_ai_task_2_1.controller.BookController;
import com.example.epam_ai_task_2_1.repository.entity.Book;
import com.example.epam_ai_task_2_1.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(books.size()));
    }

    @Test
    void getBookById() throws Exception {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        Optional<Book> optionalBook = Optional.of(book);

        Mockito.when(bookService.getBookById(bookId)).thenReturn(optionalBook);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId));
    }

    @Test
    void createBook() throws Exception {
        Book bookToCreate = new Book();
        bookToCreate.setId(1L); // Set an ID for the created book

        Mockito.when(bookService.saveBook(any(Book.class))).thenReturn(bookToCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L)); // Ensure the returned JSON contains the correct ID
    }

    @Test
    void updateBook() throws Exception {
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setId(bookId);

        Mockito.when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedBook))) // Provide a valid JSON content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId)); // Ensure the returned JSON contains "id"
    }

    @Test
    void deleteBook() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(bookService, Mockito.times(1)).deleteBook(bookId);
    }

    @Test
    void searchByTitle() throws Exception {
        String title = "Sample Title";
        List<Book> books = new ArrayList<>();

        Mockito.when(bookService.searchByTitle(title)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/title").param("title", title))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(books.size()));
    }

    @Test
    void searchByGenre() throws Exception {
        String genreName = "Sample Genre";
        List<Book> books = new ArrayList<>();

        Mockito.when(bookService.searchByGenre(genreName)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/genre").param("genreName", genreName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(books.size()));
    }

    @Test
    void searchByAuthor() throws Exception {
        String authorName = "Sample Author";
        List<Book> books = new ArrayList<>();

        Mockito.when(bookService.searchByAuthor(authorName)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search/author").param("authorName", authorName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(books.size()));
    }
}

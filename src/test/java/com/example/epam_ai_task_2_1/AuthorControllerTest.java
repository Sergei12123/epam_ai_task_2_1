package com.example.epam_ai_task_2_1;

import com.example.epam_ai_task_2_1.controller.AuthorController;
import com.example.epam_ai_task_2_1.repository.entity.Author;
import com.example.epam_ai_task_2_1.service.AuthorService;
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

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void getAllAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        Mockito.when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(authors.size()));
    }

    @Test
    void getAuthorById() throws Exception {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        Optional<Author> optionalAuthor = Optional.of(author);

        Mockito.when(authorService.getAuthorById(authorId)).thenReturn(optionalAuthor);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/{id}", authorId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorId));
    }

    @Test
    void createAuthor() throws Exception {
        Author authorToCreate = new Author();
        authorToCreate.setId(1L);
        Mockito.when(authorService.saveAuthor(any(Author.class))).thenReturn(authorToCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    void updateAuthor() throws Exception {
        Long authorId = 1L;
        Author updatedAuthor = new Author();
        updatedAuthor.setId(authorId);

        Mockito.when(authorService.updateAuthor(eq(authorId), any(Author.class))).thenReturn(updatedAuthor);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/authors/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"otherProperty\": \"updatedValue\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorId));
    }

    @Test
    void deleteAuthor() throws Exception {
        Long authorId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/{id}", authorId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(authorService, Mockito.times(1)).deleteAuthor(authorId);
    }

    @Test
    void searchByName() throws Exception {
        String authorName = "Sample Author";
        Author author = new Author();
        author.setId(1L);
        author.setName(authorName);

        Mockito.when(authorService.searchByName(authorName)).thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/search/name").param("name", authorName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(author.getId()));
    }
}

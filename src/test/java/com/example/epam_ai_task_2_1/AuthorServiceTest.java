package com.example.epam_ai_task_2_1;

import com.example.epam_ai_task_2_1.repository.AuthorRepository;
import com.example.epam_ai_task_2_1.repository.entity.Author;
import com.example.epam_ai_task_2_1.service.AuthorService;
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
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAllAuthors();

        assertEquals(authors, result);
    }

    @Test
    void getAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        Optional<Author> optionalAuthor = Optional.of(author);

        Mockito.when(authorRepository.findById(authorId)).thenReturn(optionalAuthor);

        Optional<Author> result = authorService.getAuthorById(authorId);

        assertTrue(result.isPresent());
        assertEquals(author, result.get());
    }

    @Test
    void saveAuthor() {
        Author authorToSave = new Author();
        Mockito.when(authorRepository.save(any(Author.class))).thenReturn(authorToSave);

        Author result = authorService.saveAuthor(new Author());

        assertNotNull(result);
        assertEquals(authorToSave, result);
    }

    @Test
    void updateAuthor() {
        Long authorId = 1L;
        Author existingAuthor = new Author();
        existingAuthor.setId(authorId);

        Author updatedAuthor = new Author();

        Mockito.when(authorRepository.existsById(authorId)).thenReturn(true);
        Mockito.when(authorRepository.save(updatedAuthor)).thenReturn(updatedAuthor);

        Author result = authorService.updateAuthor(authorId, updatedAuthor);

        assertNotNull(result);
        assertEquals(updatedAuthor, result);
    }

    @Test
    void deleteAuthor() {
        Long authorId = 1L;

        authorService.deleteAuthor(authorId);

        Mockito.verify(authorRepository, Mockito.times(1)).deleteById(authorId);
    }

    @Test
    void searchByName() {
        String name = "Sample Author";
        Author author = new Author();
        author.setId(1L);
        author.setName(name);

        Mockito.when(authorRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.searchByName(name);

        assertTrue(result.isPresent());
        assertEquals(author, result.get());
    }
}


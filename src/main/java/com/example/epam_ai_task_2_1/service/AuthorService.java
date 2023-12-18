package com.example.epam_ai_task_2_1.service;

import com.example.epam_ai_task_2_1.repository.AuthorRepository;
import com.example.epam_ai_task_2_1.repository.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Create
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Read
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    // Update
    public Author updateAuthor(Long id, Author updatedAuthor) {
        if (authorRepository.existsById(id)) {
            updatedAuthor.setId(id);
            return authorRepository.save(updatedAuthor);
        } else {
            // Handle the case where the author with the given id does not exist
            return null;
        }
    }

    // Delete
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    // Search by Name
    public Optional<Author> searchByName(String name) {
        return authorRepository.findByNameIgnoreCase(name);
    }
}

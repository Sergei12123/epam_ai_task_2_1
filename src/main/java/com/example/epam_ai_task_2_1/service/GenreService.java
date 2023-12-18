package com.example.epam_ai_task_2_1.service;

import com.example.epam_ai_task_2_1.repository.GenreRepository;
import com.example.epam_ai_task_2_1.repository.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // Create
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    // Read
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    // Update
    public Genre updateGenre(Long id, Genre updatedGenre) {
        if (genreRepository.existsById(id)) {
            updatedGenre.setId(id);
            return genreRepository.save(updatedGenre);
        } else {
            // Handle the case where the genre with the given id does not exist
            return null;
        }
    }

    // Delete
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    // Search by Name
    public Optional<Genre> searchByName(String name) {
        return genreRepository.findByNameIgnoreCase(name);
    }
}

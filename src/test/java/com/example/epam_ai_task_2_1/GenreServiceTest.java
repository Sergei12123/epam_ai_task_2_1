package com.example.epam_ai_task_2_1;

import com.example.epam_ai_task_2_1.repository.GenreRepository;
import com.example.epam_ai_task_2_1.repository.entity.Genre;
import com.example.epam_ai_task_2_1.service.GenreService;
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
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        Mockito.when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> result = genreService.getAllGenres();

        assertEquals(genres, result);
    }

    @Test
    void getGenreById() {
        Long genreId = 1L;
        Genre genre = new Genre();
        genre.setId(genreId);
        Optional<Genre> optionalGenre = Optional.of(genre);

        Mockito.when(genreRepository.findById(genreId)).thenReturn(optionalGenre);

        Optional<Genre> result = genreService.getGenreById(genreId);

        assertTrue(result.isPresent());
        assertEquals(genre, result.get());
    }

    @Test
    void saveGenre() {
        Genre genreToSave = new Genre();
        Mockito.when(genreRepository.save(any(Genre.class))).thenReturn(genreToSave);

        Genre result = genreService.saveGenre(new Genre());

        assertNotNull(result);
        assertEquals(genreToSave, result);
    }

    @Test
    void updateGenre() {
        Long genreId = 1L;
        Genre existingGenre = new Genre();
        existingGenre.setId(genreId);

        Genre updatedGenre = new Genre();

        Mockito.when(genreRepository.existsById(genreId)).thenReturn(true);
        Mockito.when(genreRepository.save(updatedGenre)).thenReturn(updatedGenre);

        Genre result = genreService.updateGenre(genreId, updatedGenre);

        assertNotNull(result);
        assertEquals(updatedGenre, result);
    }

    @Test
    void deleteGenre() {
        Long genreId = 1L;

        genreService.deleteGenre(genreId);

        Mockito.verify(genreRepository, Mockito.times(1)).deleteById(genreId);
    }

    @Test
    void searchByName() {
        String name = "Sample Genre";
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName(name);

        Mockito.when(genreRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.searchByName(name);

        assertTrue(result.isPresent());
        assertEquals(genre, result.get());
    }
}

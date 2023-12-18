package com.example.epam_ai_task_2_1;

import com.example.epam_ai_task_2_1.controller.GenreController;
import com.example.epam_ai_task_2_1.repository.entity.Genre;
import com.example.epam_ai_task_2_1.service.GenreService;
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

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    void getAllGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        Mockito.when(genreService.getAllGenres()).thenReturn(genres);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(genres.size()));
    }

    @Test
    void getGenreById() throws Exception {
        Long genreId = 1L;
        Genre genre = new Genre();
        genre.setId(genreId);
        Optional<Genre> optionalGenre = Optional.of(genre);

        Mockito.when(genreService.getGenreById(genreId)).thenReturn(optionalGenre);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/genres/{id}", genreId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(genreId));
    }

    @Test
    void createGenre() throws Exception {
        Genre genreToCreate = new Genre();
        genreToCreate.setId(1L);
        Mockito.when(genreService.saveGenre(any(Genre.class))).thenReturn(genreToCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    void updateGenre() throws Exception {
        Long genreId = 1L;
        Genre updatedGenre = new Genre();
        updatedGenre.setId(genreId);

        Mockito.when(genreService.updateGenre(eq(genreId), any(Genre.class))).thenReturn(updatedGenre);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/genres/{id}", genreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(genreId));
    }

    @Test
    void deleteGenre() throws Exception {
        Long genreId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/genres/{id}", genreId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(genreService, Mockito.times(1)).deleteGenre(genreId);
    }

    @Test
    void searchByName() throws Exception {
        String genreName = "Sample Genre";
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName(genreName);

        Mockito.when(genreService.searchByName(genreName)).thenReturn(Optional.of(genre));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/genres/search/name").param("name", genreName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(genre.getId()));
    }
}

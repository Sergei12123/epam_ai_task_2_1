package com.example.epam_ai_task_2_1.repository;

import com.example.epam_ai_task_2_1.repository.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByNameIgnoreCase(String genreName);
}

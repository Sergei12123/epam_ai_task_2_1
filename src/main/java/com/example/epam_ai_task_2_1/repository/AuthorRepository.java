package com.example.epam_ai_task_2_1.repository;

import com.example.epam_ai_task_2_1.repository.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameIgnoreCase(String authorName);
}

package com.example.epam_ai_task_2_1.repository;

import com.example.epam_ai_task_2_1.repository.entity.Author;
import com.example.epam_ai_task_2_1.repository.entity.Book;
import com.example.epam_ai_task_2_1.repository.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleIgnoreCase(String title);

    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthor(Author author);
}
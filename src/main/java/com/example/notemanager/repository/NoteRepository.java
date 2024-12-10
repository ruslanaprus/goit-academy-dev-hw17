package com.example.notemanager.repository;

import com.example.notemanager.model.Note;
import com.example.notemanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findByUser(User user, Pageable pageable);
    Optional<Note> findByIdAndUser(Long id, User user);
}

package com.example.notemanager.service;

import com.example.notemanager.exception.ExceptionMessages;
import com.example.notemanager.exception.NoteServiceException;
import com.example.notemanager.model.Note;
import com.example.notemanager.model.User;
import com.example.notemanager.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;

    public Page<Note> listAll(PageRequest pageRequest) {
        User currentUser = userService.getAuthenticatedUser();
        return noteRepository.findByUser(currentUser, pageRequest);
    }

    public Note getById(long id) {
        User currentUser = userService.getAuthenticatedUser();
        return noteRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new NoteServiceException(ExceptionMessages.NOTE_NOT_FOUND.getMessage()));
    }

    public Note create(Note note) {
        User currentUser = userService.getAuthenticatedUser();
        if(note.getTitle() == null || note.getTitle().isEmpty()) {
            throw new NoteServiceException(ExceptionMessages.INVALID_NOTE_DATA.getMessage());
        }
        note.setUser(currentUser);
        return noteRepository.save(note);
    }

    @Transactional
    public Note update(Note note) {
        User currentUser = userService.getAuthenticatedUser();
        Note existingNote = noteRepository.findByIdAndUser(note.getId(), currentUser)
                .orElseThrow(() -> new NoteServiceException(ExceptionMessages.NOTE_NOT_FOUND.getMessage()));
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        return noteRepository.save(existingNote);
    }

    public void delete(long id) {
        User currentUser = userService.getAuthenticatedUser();
        Note note = noteRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new NoteServiceException(ExceptionMessages.NOTE_NOT_FOUND.getMessage()));
        noteRepository.delete(note);
    }

    public Page<Note> search(String keyword, PageRequest pageRequest) {
        User currentUser = userService.getAuthenticatedUser();
        return noteRepository.findByUserAndKeyword(currentUser, keyword, pageRequest);
    }
}

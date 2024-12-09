package com.keyin.service;

import com.keyin.model.Note;
import com.keyin.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow();
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    // Update note
    public Note updateNote(Long id, Note updatedNote) {
        Note existingNote = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        return noteRepository.save(existingNote);
    }

}

package com.keyin.service;

import com.keyin.model.Note;
import com.keyin.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotes() {
        // Arrange
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("Title 1");
        note1.setContent("Content 1");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Title 2");
        note2.setContent("Content 2");

        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        // Act
        List<Note> notes = noteService.getAllNotes();

        // Assert
        assertEquals(2, notes.size());
        assertEquals("Title 1", notes.get(0).getTitle());
        assertEquals("Title 2", notes.get(1).getTitle());
    }

    @Test
    void testGetNoteById() {
        // Arrange
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Title 1");
        note.setContent("Content 1");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        // Act
        Note result = noteService.getNoteById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Title 1", result.getTitle());
    }

    @Test
    void testGetNoteById_NotFound() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> noteService.getNoteById(1L));
    }

    @Test
    void testCreateNote() {
        // Arrange
        Note note = new Note();
        note.setTitle("New Note");
        note.setContent("New Content");

        when(noteRepository.save(note)).thenReturn(note);

        // Act
        Note result = noteService.createNote(note);

        // Assert
        assertNotNull(result);
        assertEquals("New Note", result.getTitle());
    }

    @Test
    void testDeleteNoteById() {
        // Arrange
        doNothing().when(noteRepository).deleteById(1L);

        // Act
        noteService.deleteNoteById(1L);

        // Assert
        verify(noteRepository, times(1)).deleteById(1L);
    }
}


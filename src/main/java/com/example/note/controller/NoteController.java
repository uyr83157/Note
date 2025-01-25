package com.example.note.controller;

import com.example.note.dto.NoteRequestDto;
import com.example.note.dto.NoteResponseDto;
import com.example.note.entity.Note;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final Map<Long, Note> notesList = new HashMap<>();

    @PostMapping
    public NoteResponseDto creatNote(@RequestBody NoteRequestDto noteRequestDto) {

        // 식별자가 1씩 증가
        Long noteId = notesList.isEmpty() ? 1 : Collections.max(notesList.keySet()) + 1;

        // 요청받은 데이터로 Note 객체 생성
        Note note = new Note(noteId, noteRequestDto.getTitle(), noteRequestDto.getContent());

        // Inmemory DB에 Note 저장
        notesList.put(noteId, note);

        return new NoteResponseDto(note);

    }

    @GetMapping("/{id}")
    public NoteResponseDto getNoteById(@PathVariable Long id) {
        Note note = notesList.get(id);

        return new NoteResponseDto(note);

    }

    @PutMapping("/{id}")
    public NoteResponseDto updateNoteById(@PathVariable Long id, @RequestBody NoteRequestDto noteRequestDto) {

        Note note = notesList.get(id);

        note.update(noteRequestDto);

        return new NoteResponseDto(note);
    }

    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable Long id) {

        notesList.remove(id);
    }


}

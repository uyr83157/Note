package com.example.note.controller;

import com.example.note.dto.NoteRequestDto;
import com.example.note.dto.NoteResponseDto;
import com.example.note.entity.Note;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final Map<Long, Note> notesList = new HashMap<>();

    @PostMapping
    public ResponseEntity<NoteResponseDto> creatNote(@RequestBody NoteRequestDto noteRequestDto) {

        // 식별자가 1씩 증가
        Long noteId = notesList.isEmpty() ? 1 : Collections.max(notesList.keySet()) + 1;

        // 요청받은 데이터로 Note 객체 생성
        Note note = new Note(noteId, noteRequestDto.getTitle(), noteRequestDto.getContent());

        // Inmemory DB에 Note 저장
        notesList.put(noteId, note);

        return new ResponseEntity<>(new NoteResponseDto(note), HttpStatus.CREATED);

    }

    @GetMapping
    public List<NoteResponseDto> getAllNotes() {

        List<NoteResponseDto> noteResponseDtoList = new ArrayList<>();

        for (Note note : notesList.values()) {
            NoteResponseDto noteResponseDto = new NoteResponseDto(note);
            noteResponseDtoList.add(noteResponseDto);
        }

//        noteResponseDtoList = noteResponseDtoList.stream().map(NoteResponseDto::new).toList();

        return noteResponseDtoList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        Note note = notesList.get(id);

        if (note == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new NoteResponseDto(note), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long id, @RequestBody NoteRequestDto noteRequestDto) {

        Note note = notesList.get(id);

        if (note == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (noteRequestDto.getTitle() == null || noteRequestDto.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        note.update(noteRequestDto);

        return new ResponseEntity<>(new NoteResponseDto(note), HttpStatus.OK);
    }

    // 제목만 수정
    @PatchMapping("/{id}")
    public ResponseEntity<NoteResponseDto> udpateTitleById(@PathVariable Long id, @RequestBody NoteRequestDto noteRequestDto) {
        Note note = notesList.get(id);

        if (note == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (noteRequestDto.getTitle() == null || noteRequestDto.getContent() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        note.updateTitle(noteRequestDto);

        return new ResponseEntity<>(new NoteResponseDto(note), HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {

        if (notesList.containsKey(id)) {
            notesList.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

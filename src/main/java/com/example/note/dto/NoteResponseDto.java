package com.example.note.dto;

import com.example.note.entity.Note;
import lombok.Getter;

@Getter
public class NoteResponseDto {

    private Long id;
    private String title;
    private String content;

    public NoteResponseDto(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
    }
}

package com.example.note.entity;


import com.example.note.dto.NoteRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Note {

    private Long id;
    private String title;
    private String content;

    public void update(NoteRequestDto noteRequestDto) {
        this.title = noteRequestDto.getTitle();
        this.content = noteRequestDto.getContent();
    }
}

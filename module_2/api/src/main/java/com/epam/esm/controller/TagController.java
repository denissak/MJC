package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> readTag(@PathVariable long id) {
        TagDto tagDto = tagService.readById(id);
        return ResponseEntity.status(HttpStatus.OK).body(tagDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> readsAllTags() {
        return tagService.readAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.delete(id);
    }
}

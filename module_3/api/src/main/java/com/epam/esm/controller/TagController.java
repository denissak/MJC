package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for working with tagEntities.
 *
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Reads tag with passed id.
     *
     * @param id id of tag to be read
     * @return tag with passed id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> readTag(@PathVariable long id) {
        TagDto tagDto = tagService.readById(id);
        return ResponseEntity.status(HttpStatus.OK).body(tagDto);
    }

    /**
     * Read all tagEntities.
     *
     * @return all tagEntities
     */
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<TagDto> readsAllTags() {
            return tagService.readAll();
        }

    /**
     * Creates and saves the passed tag.
     *
     * @param tagDto the tag to be saved
     * @return saved tag
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    /**
     * Deletes tag with passed id.
     *
     * @param id the id of tag to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.delete(id);
    }
}

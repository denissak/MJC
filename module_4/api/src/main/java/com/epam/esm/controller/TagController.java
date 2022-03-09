package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller for working with tags.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

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
    @ResponseStatus(HttpStatus.OK)
    public TagDto readTagById(@PathVariable long id) {
        Link link = linkTo(TagController.class).withSelfRel();
        TagDto tagDto = tagService.readById(id);
        tagDto.add(link);
        return tagDto;
    }

    /**
     * Read all tagEntities.
     *
     * @return all tagEntities
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> readsAllTags(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<TagDto> tagDtoList = tagService.readAll(page, size);
        return addLinksToTag(tagDtoList);
    }

//    @GetMapping("/popular-tag")
//    @ResponseStatus(HttpStatus.OK)
//    public TagDto getMostPopularTag() {
//        Link link = linkTo(TagController.class).withSelfRel();
//        TagDto tagDto = tagService.getMostPopularTag();
//        tagDto.add(link);
//        return tagDto;
//    }

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

    private CollectionModel<TagDto> addLinksToTag(List<TagDto> tagDtoList) {
        for (final TagDto tagDto : tagDtoList) {
            Link selfLink = linkTo(methodOn(TagController.class)
                    .readTagById(tagDto.getId())).withSelfRel();
            tagDto.add(selfLink);
        }
        Link link = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(tagDtoList, link);
    }
}

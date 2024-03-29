package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller for working with tags.
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    @Value("${upload.pathtag}")
    private String uploadPath;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Read tag with passed id.
     *
     * @param id id of tag to be read
     * @return tag with passed id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto readTagById(@PathVariable long id) {
        try {
            Link link = linkTo(TagController.class).withSelfRel();
            TagDto tagDto = tagService.readById(id);
            tagDto.add(link);
            return tagDto;
        } catch (RuntimeException e) {
            throw NotFoundException.notFoundWithTagId(id).get();
        }
    }

    /**
     * Read all tagEntities.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all tagEntities
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> readsAllTags(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                @RequestParam(value = "size", defaultValue = "30", required = false) int size) {
        List<TagDto> tagDtoList = tagService.readAll(page, size);
        return addLinksToTag(tagDtoList);
    }

    /**
     * Read most popular tag by max cost order.
     *
     * @return TagDto which meet passed parameters
     */
    @GetMapping("/popular-tag")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getMostPopularTag() {
        Link link = linkTo(TagController.class).withSelfRel();
        TagDto tagDto = tagService.getMostPopularTag();
        tagDto.add(link);
        return tagDto;
    }

    /**
     * Create and save the passed tag.
     *
     * @param tagName the name to be saved
     * @param file the image to be saved
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag(HttpServletResponse response,
                          @RequestParam("name") String tagName,
                          @RequestParam("image") MultipartFile file) throws IOException {
        try {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));

            TagDto tagDto = TagDto.builder()
                    .name(tagName)
                    .image(resultFilename)
                    .build();

            tagService.create(tagDto);
            response.sendRedirect("mainpage");
        } catch (RuntimeException e) {
            throw DuplicateException.tagExists().get();
        }
    }

    /**
     * Delete tag with passed id.
     *
     * @param id the id of tag to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        try {
            tagService.delete(id);
        } catch (RuntimeException e) {
            throw NotFoundException.notFoundWithTagId(id).get();
        }
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

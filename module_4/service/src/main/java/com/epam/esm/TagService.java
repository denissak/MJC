package com.epam.esm;

import com.epam.esm.dto.TagDto;

import java.util.List;

/**
 * Contains methods for working mostly with {@code TagDto} entity.
 */
public interface TagService {

    /**
     * Create and save the passed tag.
     *
     * @param tagDto the tag to be saved
     * @return saved tag
     */
    TagDto create(TagDto tagDto);

    /**
     * Read tag with passed id.
     *
     * @param tagId id of tag to be read
     * @return tag with passed id
     */
    TagDto readById(Long tagId);

    /**
     * Read all tags.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all tags.
     */
    List<TagDto> readAll(int page, int size);

    /**
     * Read most popular tag by max cost order.
     *
     * @return TagEntity which meet passed parameters
     */
    TagDto getMostPopularTag();

    /**
     * Delete tag with passed id.
     *
     * @param tagId the id of tag to be deleted
     */
    void delete(Long tagId);
}

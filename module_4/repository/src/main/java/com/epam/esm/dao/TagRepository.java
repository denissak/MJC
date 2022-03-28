package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains methods for working mostly with {@code Tag} entity.
 */

public interface TagRepository extends JpaRepository<TagEntity, Long>, PopularTagRepository {

    /**
     * Reads TagEntity with passed name.
     *
     * @param name the name of tag to be read
     * @return tag with passed name
     */
    TagEntity readByName (String name);
}

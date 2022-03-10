package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;

/**
 * Contains methods for working mostly with {@code Tag} entity.
 */

public interface TagRepository extends AbstractRepository<TagEntity> {

    /**
     * Read most popular tag by max cost order.
     *
     * @return TagEntity which meet passed parameters
     */
    TagEntity getMostPopularTag();
}

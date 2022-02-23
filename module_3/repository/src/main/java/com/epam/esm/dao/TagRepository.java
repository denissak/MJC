package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;
import org.springframework.stereotype.Repository;

/**
 *
 * Contains methods for working mostly with {@code Tag} entity.
 *
 */

public interface TagRepository extends AbstractRepository<TagEntity> {

    TagEntity getMostPopularTag();
}

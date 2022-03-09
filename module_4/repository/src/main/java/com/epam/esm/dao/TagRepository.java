package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains methods for working mostly with {@code Tag} entity.
 */

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    TagEntity readByName (String name);
    /**
     * Read most popular tag by max cost order.
     *
     * @return TagEntity which meet passed parameters
     */
//    TagEntity getMostPopularTag();

}

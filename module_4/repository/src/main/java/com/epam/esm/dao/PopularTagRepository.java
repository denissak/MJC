package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;

/**
 * Contains method for the most popular tag by the user with the highest value of all orders {@code Tag} entity.
 */
public interface PopularTagRepository {

    /**
     * Read most popular tag by max cost order.
     *
     * @return TagEntity which meet passed parameters
     */
    TagEntity getMostPopularTag();
}

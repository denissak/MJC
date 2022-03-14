package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;

public interface PopularTagRepository {

    TagEntity getMostPopularTag();
}

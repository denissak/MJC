package com.epam.esm.dao;

import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;

/**
 * Contains methods for working pagination with readAll methods.
 */
@Component
public interface PaginationHandler {

    void setPageToQuery(TypedQuery<?> typedQuery, int page, int size);
}

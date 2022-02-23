package com.epam.esm.dao;

import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;

@Component
public interface PaginationHandler {

    void setPageToQuery(TypedQuery<?> typedQuery, int page, int size);

}

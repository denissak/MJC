package com.epam.esm.dao.impl;

import com.epam.esm.dao.PaginationHandler;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;

@Component
public class PaginationHandlerImpl implements PaginationHandler {

    public void setPageToQuery(TypedQuery<?> typedQuery, int page, int size) {
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
    }
}

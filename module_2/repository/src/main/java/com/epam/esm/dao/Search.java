package com.epam.esm.dao;

import org.springframework.stereotype.Component;

@Component
public class Search {

    private static final String SELECT = "SELECT * from gift_certificate gc ";
    private static final String TAG_CONDITION = "JOIN gift_certificate_m2m_tag m2m on gc.id = m2m.gift_certificate_id\n" +
            "                  JOIN tag on m2m.tag_id = tag.id\n" +
            "WHERE tag.name = '%s' ";
    private static final String SEARCH_BY_NAME = "gc.name ";
    private static final String SEARCH_BY_DESCRIPTION = "gc.description ";
    private static final String AND = "AND ";
    private static final String SORT_BY_NAME_ASC = " ORDER BY gc.name ASC";
    private static final String SORT_BY_NAME_DESC = " ORDER BY gc.name DESC";
    private static final String SORT_BY_DATE_ASC = " ORDER BY gc.create_date ASC";
    private static final String SORT_BY_DATE_DESC = " ORDER BY gc.create_date DESC";
    private static final String PROCENT = "%";
    private static final String QUOTATION = "'";
    private static final String LIKE = "LIKE ";
    private static final String VALUE = "%s";

    public String buildSearchCertificate (String tagName, String name, String description, String sortBy, String sortOrder){
        StringBuilder query = new StringBuilder();
        query.append(SELECT);

        if (!tagName.equals("")) {
            query.append(String.format(TAG_CONDITION, tagName));
        }
        if (!name.equals("")) {
            query.append(AND);
            query.append(SEARCH_BY_NAME);
            query.append(LIKE);
            query.append(QUOTATION);
            query.append(PROCENT);
            query.append(String.format(VALUE, name));
            query.append(PROCENT);
            query.append(QUOTATION);
        } else if (!description.equals("")) {
            query.append(AND);
            query.append(SEARCH_BY_DESCRIPTION);
            query.append(LIKE);
            query.append(QUOTATION);
            query.append(PROCENT);
            query.append(String.format(VALUE, description));
            query.append(PROCENT);
            query.append(QUOTATION);
        }
        if (!(sortBy.equals("") && sortOrder.equals(" "))){
            if (sortBy.equals("name") && sortOrder.equals("asc")){
                query.append(SORT_BY_NAME_ASC);
            } else if (sortBy.equals("name") && sortOrder.equals("desc")){
                query.append(SORT_BY_NAME_DESC);
            } else if (sortBy.equals("date") && sortOrder.equals("asc")){
                query.append(SORT_BY_DATE_ASC);
            } else if (sortBy.equals("date") && sortOrder.equals("desc")){
                query.append(SORT_BY_DATE_DESC);
            }
        }
        return query.toString();
    }
}

//package com.epam.esm.dao;
//
//import java.util.List;
//
///**
// * Contains common methods for working with all entities during the interaction
// * with database.
// */
//public interface AbstractRepository<T> {
//
//    /**
//     * Saves the passed entity.
//     *
//     * @param model the entity to be saved
//     * @return saved entity
//     */
//    T create(T model);
//
//    /**
//     * Reads entity with passed id.
//     *
//     * @param id the id of entity to be read
//     * @return entity with passed id
//     */
//    T readById(Long id);
//
//    /**
//     * Reads entity with passed name.
//     *
//     * @param name the name of entity to be read
//     * @return entity with passed name
//     */
//    T readByName(String name);
//
//    /**
//     * Reads all entities according to passed parameters.
//     *
//     * @return entities which meet passed parameters
//     */
//    List<T> readAll(int page, int size);
//
//    /**
//     * Deletes entity with passed id.
//     *
//     * @param id the id of entity to be deleted
//     */
//    void delete(Long id);
//}

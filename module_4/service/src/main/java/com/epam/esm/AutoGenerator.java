package com.epam.esm;

/**
 * Methods for database auto-generation
 */
public interface AutoGenerator {

    /**
     * Auto-create Certificates in DataBase
     */
    void createAutoCertificate();

    /**
     * Auto-create Orders in DataBase
     */
    void createAutoOrder();

    /**
     * Auto-create Tags in DataBase
     */
    void createAutoTag();

    /**
     * Auto-create Users in DataBase
     */
    void createAutoUser();
}

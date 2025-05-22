package jm.task.core.jdbc.dao;

public class SQLconst {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id SERIAL PRIMARY KEY, " +
            "name VARCHAR(50), " +
            "lastName VARCHAR(50), " +
            "age SMALLINT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    public static final String INSERT_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    static final String SELECT_ALL = "SELECT * FROM users";
    static final String CLEAN_TABLE = "TRUNCATE TABLE users";
}
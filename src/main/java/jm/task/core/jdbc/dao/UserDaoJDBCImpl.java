package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoJDBCImpl.class);
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLconst.CREATE_TABLE);
            log.info("Таблица создана");
        } catch (SQLException e) {
            log.error("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQLconst.DROP_TABLE);
            log.info("Таблица удалена");
        } catch (SQLException e) {
            log.error("Не удалось создать таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLconst.INSERT_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            log.info("User с именем – {} добавлен в базу данных", name);
        } catch (SQLException e) {
            log.error("Не удалось добавить User");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLconst.DELETE_USER);
            log.info("User удален");
        } catch (SQLException e) {
            log.error("Не удалось удалить User ");

        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLconst.SELECT_ALL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

        } catch (
                Exception e) {
            log.error("Не удалось вывести всех User");

        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLconst.CLEAN_TABLE);
            log.info("Таблица очищена");
        } catch (SQLException e) {
            log.error("Не удалось очистить таблицу");
        }

    }
}
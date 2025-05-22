package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceHiberImpl;
import jm.task.core.jdbc.service.UserServiceJDBCImpl;
import jm.task.core.jdbc.util.PropertiesUtil;

public class Main {
    public static void main(String[] args) {
        String daoType = PropertiesUtil.get("dao");
        UserDao userDao;
        UserService userService = switch (daoType) {
            case "hibernate" -> {
                userDao = new UserDaoHibernateImpl();
                yield new UserServiceHiberImpl(userDao);
            }
            case "jdbc" -> {
                userDao = new UserDaoJDBCImpl();
                yield new UserServiceJDBCImpl(userDao);
            }
            default -> throw new IllegalArgumentException("Unknown DAO type in properties: " + daoType);
        };

        userService.createUsersTable();

        userService.saveUser("Alexey", "Sidorov", (byte) 20);
        userService.saveUser("Maria", "Smirnova", (byte) 23);
        userService.saveUser("Dmitry", "Suvorov", (byte) 33);
        userService.saveUser("Petr", "Ivanov", (byte) 25);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

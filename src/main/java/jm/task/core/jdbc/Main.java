package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
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
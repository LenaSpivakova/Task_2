package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

    private void executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            log.error("Ошибка при выполнении транзакции", e);
        }
    }

    private <R> R executeWithResult(Function<Session, R> function) {
        try (Session session = Util.getSessionFactory().openSession()) {
            return function.apply(session);
        } catch (Exception e) {
            log.error("Ошибка при выполнении запроса", e);
            return null;
        }
    }

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        executeInsideTransaction(session ->
                session.createNativeQuery(SQLconst.CREATE_TABLE).executeUpdate()
        );
        log.info("Таблица пользователей создана (если отсутствовала)");
    }

    @Override
    public void dropUsersTable() {
        executeInsideTransaction(session ->
                session.createNativeQuery(SQLconst.DROP_TABLE).executeUpdate()
        );
        log.info("Таблица пользователей удалена ");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        executeInsideTransaction(session -> {
            session.save(new User(name, lastName, age));
            log.info("User с именем – {} добавлен в базу данных", name);
        });
    }

    @Override
    public void removeUserById(long id) {
        executeInsideTransaction(session -> {
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                log.info("User с id {} удалён из базы данных", id);
            } else {
                log.warn("User с id {} не найден для удаления", id);
            }
        });
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = executeWithResult(session ->
                session.createQuery(SQLconst.SELECT_ALL, User.class).list()
        );
        log.info("Получено {} пользователей из базы данных", users != null ? users.size() : 0);
        return users != null ? users : List.of();
    }

    @Override
    public void cleanUsersTable() {
        executeInsideTransaction(session ->
                session.createNativeQuery(SQLconst.CLEAN_TABLE).executeUpdate()
        );
        log.info("Таблица пользователей очищена");
    }
}
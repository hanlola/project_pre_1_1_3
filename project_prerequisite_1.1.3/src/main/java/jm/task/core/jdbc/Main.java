package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {
        new UserDaoHibernateImpl().createUsersTable();
        new UserDaoHibernateImpl().saveUser("Artem", "Tsoy", (byte) 1);
        new UserDaoHibernateImpl().saveUser("Vlad", "Tsoy", (byte) 28);
        new UserDaoHibernateImpl().saveUser("Lola", "Han", (byte) 26);
        new UserDaoHibernateImpl().saveUser("Artur", "Han", (byte) 20);
        System.out.print(new UserDaoHibernateImpl().getAllUsers());
        new UserDaoHibernateImpl().removeUserById(2);
        new UserDaoHibernateImpl().cleanUsersTable();
        new UserDaoHibernateImpl().dropUsersTable();

    }
}

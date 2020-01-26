package by.epam.mtlcwtchr.pfa.client.dao;

import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;

import java.util.HashMap;

public interface UserDAO {

    HashMap<Integer, User> getUsersList() throws DAOException;
    User signUp(User user) throws DAOException;
    User signIn(User user) throws DAOException;
    void setBanned(int id, boolean isBanned) throws DAOException;

}

package by.epam.mtlcwtchr.pfa.client.service.impl;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.dao.AccountDAO;
import by.epam.mtlcwtchr.pfa.client.dao.UserDAO;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;
import by.epam.mtlcwtchr.pfa.client.dao.factory.DAOFactory;
import by.epam.mtlcwtchr.pfa.client.dao.type.DAOType;
import by.epam.mtlcwtchr.pfa.client.service.AdminRules;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public class AdminService extends Profile implements AdminRules {

    @Override
    public HashMap<Integer, Account> getAccounts(int ownerID) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        AccountDAO accountDAO = daoFactory.getAccountDAO();
        try {
            return accountDAO.readAllForOwner(ownerID);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public HashMap<Integer, User> getUsersList() throws ServiceException{
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        UserDAO userDAO = daoFactory.getUserDAO();
        try{
            return userDAO.getUsersList();
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public void setBanned(int userID, boolean value) throws ServiceException{
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            userDAO.setBanned(userID, value);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public void setBlocked(int ownerID, int accountID, boolean value) throws ServiceException{
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        AccountDAO accountDAO = daoFactory.getAccountDAO();
        try {
            accountDAO.setBlocked(ownerID, accountID, value);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

}

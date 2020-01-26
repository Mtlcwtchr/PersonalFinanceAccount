package by.epam.mtlcwtchr.pfa.client.service;

import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.dao.AccountDAO;
import by.epam.mtlcwtchr.pfa.client.dao.TransactionHistoryDAO;
import by.epam.mtlcwtchr.pfa.client.dao.UserDAO;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;
import by.epam.mtlcwtchr.pfa.client.dao.factory.DAOFactory;
import by.epam.mtlcwtchr.pfa.client.dao.type.DAOType;
import by.epam.mtlcwtchr.pfa.client.service.exception.AuthenticationException;

public class Profile {

    private User user;

    public void signIn(User user) throws AuthenticationException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            this.user = userDAO.signIn(user);
        } catch (DAOException ex){
            throw new AuthenticationException(ex);
        }
    }

    public void signUp(User user) throws AuthenticationException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        UserDAO userDAO = daoFactory.getUserDAO();
        AccountDAO accountDAO = daoFactory.getAccountDAO();
        TransactionHistoryDAO transactionHistoryDAO = daoFactory.getTransactionHistoryDAO();
        try {
            this.user = userDAO.signUp(user);
            accountDAO.createAccount(user.getId());
            transactionHistoryDAO.create(user.getId());
        } catch (DAOException ex){
            throw new AuthenticationException(ex);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(user!=null)
        this.user = user;
    }

}

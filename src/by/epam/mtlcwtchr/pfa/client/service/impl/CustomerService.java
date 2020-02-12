package by.epam.mtlcwtchr.pfa.client.service.impl;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.dao.AccountDAO;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;
import by.epam.mtlcwtchr.pfa.client.dao.factory.DAOFactory;
import by.epam.mtlcwtchr.pfa.client.dao.type.DAOType;
import by.epam.mtlcwtchr.pfa.client.service.CustomerRules;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public class CustomerService extends Profile implements CustomerRules {

    @Override
    public HashMap<Integer, Account> getAccounts() throws ServiceException {
        AccountDAO accountDAO = prepareDAO();
        try {
            return accountDAO.readAllForOwner(getUser().getId());
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public void createAccount() throws ServiceException {
        AccountDAO accountDAO = prepareDAO();
        try{
            accountDAO.create(getUser().getId());
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public void replenish(int accountID, int value) throws ServiceException {
        AccountDAO accountDAO = prepareDAO();
        try {
            accountDAO.replenish(getUser().getId(), accountID, value);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public void withdraw(int accountID, int value) throws ServiceException {
        AccountDAO accountDAO = prepareDAO();
        try {
            accountDAO.withdraw(getUser().getId(), accountID, value);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public void getDebt(int accountID, int value) throws ServiceException {
        AccountDAO accountDAO = prepareDAO();
        try{
            accountDAO.borrow(getUser().getId(), accountID, value);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    @Override
    public void payOff(int accountID, int value) throws ServiceException {
        AccountDAO accountDAO = prepareDAO();
        try{
            accountDAO.payOff(getUser().getId(), accountID, value);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

    private AccountDAO prepareDAO(){
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        return daoFactory.getAccountDAO();
    }

}
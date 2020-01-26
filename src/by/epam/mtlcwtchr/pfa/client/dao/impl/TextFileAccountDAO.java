package by.epam.mtlcwtchr.pfa.client.dao.impl;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.dao.AccountDAO;
import by.epam.mtlcwtchr.pfa.client.dao.connection.Connection;
import by.epam.mtlcwtchr.pfa.client.dao.connection.impl.TextFileConnection;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOAccessException;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;

import java.io.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFileAccountDAO implements AccountDAO {

    private Properties properties;
    private String fileName;
    private boolean propertiesLoaded = false;
    private String isBlockedMessage = "Account is blocked";

    @Override
    public void createAccount(int ownerID) throws DAOException {

        loadProperties("accountFileSubName");
        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.create();

    }

    @Override
    public void create(int ownerID) throws DAOException {

        loadProperties("accountFileSubName");
        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getReader()));
        int lastKey=0;
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                Matcher keyMatcher = Pattern.compile("\\[account number: [a-z0-9]+?,").matcher(line);
                if(keyMatcher.find())
                    lastKey = Integer.parseInt(line.substring(keyMatcher.start()+17,keyMatcher.end()-1),16)+21;
            }
        } catch (IOException ex){
            throw new DAOException(ex);
        }

        PrintWriter writer = new PrintWriter(connection.getWriter());
        writer.write(new Account(lastKey).toString()+"\n");
        writer.flush();

        connection.close();

    }

    @Override
    public void replenish(int ownerID, int keyNumber, int amount) throws DAOException {

        loadProperties("accountFileSubName");
        HashMap<Integer, Account> map = readAllForOwner(ownerID);
        Account account = map.get(keyNumber);
        if(account.isBanned())
            throw new DAOException(isBlockedMessage);
        account.setAmount(account.getAmount()+amount);

        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(false);

        rewrite(new PrintWriter(connection.getWriter()), map);

    }

    @Override
    public void withdraw(int ownerID, int keyNumber, int amount) throws DAOException {

        loadProperties("accountFileSubName");
        HashMap<Integer, Account> map = readAllForOwner(ownerID);
        Account account = map.get(keyNumber);
        if(account.isBanned())
            throw new DAOException(isBlockedMessage);

        int accAmount = account.getAmount();
        if(accAmount>=amount) {
            account.setAmount(account.getAmount() - amount);
        } else {
            account.setDebt(account.getDebt()+(amount-accAmount));
            account.setAmount(0);
        }

        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(false);

        rewrite(new PrintWriter(connection.getWriter()), map);

        connection.close();

    }

    @Override
    public void payOff(int ownerID, int keyNumber, int amount) throws DAOException {

        loadProperties("accountFileSubName");
        HashMap<Integer, Account> map = readAllForOwner(ownerID);
        Account account = map.get(keyNumber);
        if(account.isBanned())
            throw new DAOException("Account is banned");

        if(account.getDebt()<amount){
            account.setAmount(account.getAmount()+(amount-account.getDebt()));
            account.setDebt(0);
        }
        else{
            account.setDebt(account.getDebt()-amount);
        }

        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(false);

        rewrite(new PrintWriter(connection.getWriter()), map);

        connection.close();

    }

    @Override
    public void borrow(int ownerID, int keyNumber, int amount) throws DAOException {

        loadProperties("accountFileSubName");
        HashMap<Integer, Account> map = readAllForOwner(ownerID);
        Account account = map.get(keyNumber);
        if(account.isBanned())
            throw new DAOException(isBlockedMessage);

        account.setDebt(account.getDebt()+amount);

        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(false);

        rewrite(new PrintWriter(connection.getWriter()), map);

        connection.close();

    }

    @Override
    public void setBlocked(int ownerID, int keyNumber, boolean isBanned) throws DAOException {

        loadProperties("accountFileSubName");
        HashMap<Integer, Account> map = readAllForOwner(ownerID);

        Account account = map.get(keyNumber);
        if(account!=null)
        account.setBanned(isBanned);

        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(false);

        rewrite(new PrintWriter(connection.getWriter()), map);

        connection.close();

    }

    @Override
    public Account read(int ownerID, int keyNumber) throws DAOException {

        loadProperties("accountFileSubName");
        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getReader()));
        String line;
        try{
            while ((line=reader.readLine())!=null){
                if(lineMatchesKeyNumber(line, keyNumber)){
                    return Account.fromString(line);
                }
            }
        } catch (IOException ex){
            throw new DAOException(ex);
        }

        connection.close();
        return null;
    }

    @Override
    public HashMap<Integer, Account> readAllForOwner(int ownerID) throws DAOException {

        loadProperties("accountFileSubName");
        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getReader()));
        HashMap<Integer, Account> map = new HashMap<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                Matcher keyMatcher = Pattern.compile("\\[account number: [a-z0-9]+?,").matcher(line);
                if(keyMatcher.find())
                map.put(Integer.parseInt(line.substring(keyMatcher.start()+17,keyMatcher.end()-1), 16), Account.fromString(line));
            }
        } catch (IOException ex){
            throw new DAOException(ex);
        }

        connection.close();
        return map;

    }

    private void loadProperties(String propForFileName) throws DAOAccessException {
        if(propertiesLoaded) return;
        properties = new Properties();
        try {
            properties.load(new FileReader("properties/DATABASES_PATHS.properties"));
            fileName = properties.getProperty(propForFileName);
            propertiesLoaded = true;
        } catch (IOException ex){
            throw new DAOAccessException(ex);
        }
    }

    private boolean lineMatchesKeyNumber(String line, int keyNumber){
        return line.contains(new Formatter().format("[account number: %s", Integer.toHexString(keyNumber)).toString());
    }

    private void rewrite(PrintWriter writer, HashMap<Integer, Account> map){
        map.forEach((i,a)->{
            writer.write(a.toString()+"\n");
            writer.flush();
        });

    }

}

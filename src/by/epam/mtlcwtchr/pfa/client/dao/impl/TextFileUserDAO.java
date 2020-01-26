package by.epam.mtlcwtchr.pfa.client.dao.impl;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.dao.*;
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


public class TextFileUserDAO implements UserDAO {

    private Properties properties;
    private String fileName;
    private boolean propertiesLoaded = false;

    @Override
    public HashMap<Integer, User> getUsersList() throws DAOException {

        loadProperties("usersFileName");
        Connection connection = new TextFileConnection(fileName);
        connection.establish(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getReader()));
        HashMap<Integer, User> map = new HashMap<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                Matcher keyMatcher = Pattern.compile("\\[id: [a-z0-9]+?,").matcher(line);
                if(keyMatcher.find())
                    map.put(Integer.parseInt(line.substring(keyMatcher.start()+5,keyMatcher.end()-1), 16), User.fromString(line));
            }
        } catch (IOException ex){
            throw new DAOException(ex);
        }

        connection.close();
        return map;

    }

    @Override
    public User signUp(User user) throws DAOException {

        loadProperties("usersFileName");
        Connection connection = new TextFileConnection(fileName);
        connection.establish(true);

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getReader()));
            PrintWriter writer = new PrintWriter(connection.getWriter());

            if (userExists(reader, user))
                throw new DAOException("User exists");

            writer.write(user.toString() + "\n");
            writer.flush();

        } finally {
            connection.close();
        }

        return user;

    }

    @Override
    public User signIn(User user) throws DAOException {

        loadProperties("usersFileName");
        Connection connection = new TextFileConnection(fileName);
        connection.establish(true);

        User retUser = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getReader()));
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if(lineMatchesId(line,user.getId())&&
                        (lineMatches(line,user.getUsername(),user.getPassword())))
                    retUser = User.fromString(line);
                if(retUser!=null)
                    if(retUser.isBanned()) throw new DAOException("User banned");
            }
        } catch (IOException ex){
            throw new DAOException(ex);
        } finally {
            connection.close();
        }

        if(retUser==null)
            throw new DAOException("User not found");
        else
            return retUser;

    }

    @Override
    public void setBanned(int id, boolean isBanned) throws DAOException {

        loadProperties("usersFileName");
        Connection connection = new TextFileConnection(fileName);

        connection.establish(true);
        HashMap<Integer, User> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getReader()));
        String line;
        try {
            while ((line = reader.readLine())!= null) {
                map.put(User.fromString(line).getId(),User.fromString(line));
            }
        } catch (IOException ex){
            throw new DAOException(ex);
        }
        connection.close();

        User user = map.get(id);
        user.setBanned(isBanned);

        connection.establish(false);
        PrintWriter writer = new PrintWriter(connection.getWriter());
        map.forEach((i,u)->{
            writer.write(u.toString()+"\n");
            writer.flush();
        });
        connection.close();

    }

    private void loadProperties(String propForFileName) throws DAOAccessException{
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

    private boolean lineMatchesId(String line, int id){
        return line.contains(new Formatter().format("id: %s",Integer.toHexString(id)).toString());
    }

    private boolean lineMatches(String line, String username, String password){
        return line.contains(new Formatter().format("username: '%s'",username).toString())&&
                line.contains(new Formatter().format("password: '%s'",password).toString());
    }

    private boolean userExists(BufferedReader reader, User user) throws DAOException{
        String line;
        try {
            while ((line = reader.readLine()) != null){
                if(lineMatchesId(line,user.getId())&&
                        (lineMatches(line,user.getUsername(),user.getPassword())))
                    return true;
            }
        } catch (IOException ex){
            throw new DAOException(ex);
        }
        return false;
    }


}

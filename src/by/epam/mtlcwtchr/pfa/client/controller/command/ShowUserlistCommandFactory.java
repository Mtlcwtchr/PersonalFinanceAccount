package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;
import by.epam.mtlcwtchr.pfa.client.service.impl.Admin;

import java.util.HashMap;

public class ShowUserlistCommandFactory extends CommandFactory{

    Profile profile;

    ShowUserlistCommandFactory(Profile profile){
        this.profile = profile;
    }

    public void execute(String... args) throws CommandNotSupportedException {
        throw new CommandNotSupportedException("Command not supported");
    }

    public HashMap<Integer, User> executeWithReturn(String... args) throws ServiceException{
        try {
            return ((Admin) profile).getUsersList();
        } catch (ClassCastException ex){
            throw new ServiceException("Wrong rules");
        } catch (NumberFormatException ex){
            throw new ServiceException("Wrong arguments");
        }
    }

}

package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;
import by.epam.mtlcwtchr.pfa.client.service.impl.Admin;

import java.util.HashMap;

public class UnblockCommandFactory extends CommandFactory{

    Profile profile;

    UnblockCommandFactory(Profile profile){
        this.profile = profile;
    }

    public void execute(String... args) throws ServiceException {
        try {
            ((Admin)profile).setBlocked(Integer.parseInt(args[0], 16),Integer.parseInt(args[1], 16), false);
        } catch (ClassCastException ex){
            throw new ServiceException("Wrong rules");
        }
        catch (NumberFormatException ex){
            throw new ServiceException("Wrong arguments");
        }
    }

    public HashMap<Integer, Account> executeWithReturn(String... args) throws CommandNotSupportedException{
        throw new CommandNotSupportedException("Command not supported");
    }

}

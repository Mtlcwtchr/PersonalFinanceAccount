package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;
import by.epam.mtlcwtchr.pfa.client.service.impl.AdminService;

import java.util.HashMap;

public class BlockCommandFactory extends ProfiledCommandFactory{

    BlockCommandFactory(Profile profile){
        this.profile = profile;
    }

    public void execute(String... args) throws ServiceException {
        try {
            ((AdminService)profile).setBlocked(Integer.parseInt(args[0], 16), Integer.parseInt(args[1], 16), true);
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

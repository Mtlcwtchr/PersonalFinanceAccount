package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;
import by.epam.mtlcwtchr.pfa.client.service.impl.CustomerService;

import java.util.HashMap;

public class CreateAccountCommandFactory extends ProfiledCommandFactory{

    CreateAccountCommandFactory(Profile profile){
        this.profile = profile;
    }

    public void execute(String... args) throws ServiceException {
        try {
            ((CustomerService) profile).createAccount();
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

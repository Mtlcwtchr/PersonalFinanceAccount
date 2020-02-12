package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.controller.command.type.CommandType;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public abstract class ProfiledCommandFactory extends CommandFactory {

    protected Profile profile;

    public abstract void execute(String... args) throws ServiceException;
    public abstract HashMap<?, ?> executeWithReturn(String... args) throws ServiceException;

}

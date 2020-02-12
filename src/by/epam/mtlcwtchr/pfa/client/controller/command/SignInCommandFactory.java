package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;
import by.epam.mtlcwtchr.pfa.client.service.validation.Validator;

import java.util.HashMap;

public class SignInCommandFactory extends ProfiledCommandFactory{

    SignInCommandFactory(Profile profile){
        this.profile = profile;
    }

    public void execute(String... args) throws ServiceException {

        if(args==null||args.length!=2) throw new ServiceException("Wrong arguments");

        if(!Validator.usernameMatches(args[0])){
            throw new ServiceException("Username can contain latin letters, numbers from 0 to 9 and special symbol '_'");
        }
        if(!Validator.passwordMatches(args[1])){
            throw new ServiceException("Password must contain latin letters, at least 1 latin upper-case letter," +
                    " numbers from 0 to 9 and special symbols of type '!_@#$%^&?' ");
        }

            profile.signIn(new User(args[0], args[1]));

    }

    public HashMap<Object, Object> executeWithReturn(String... args) throws CommandNotSupportedException{
        throw new CommandNotSupportedException("Command not supported");
    }

}

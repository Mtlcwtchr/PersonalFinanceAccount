package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public abstract class CommandFactory {

    public static CommandFactory getCommandFactory(Profile profile, CommandType commandType) {
        if(commandType==CommandType.SIGN_IN){
            return new SignInCommandFactory(profile);
        }
        else if(commandType==CommandType.SIGN_UP){
            return new SignUpCommandFactory(profile);
        }
        else if(commandType==CommandType.HELP){
            return new HelpCommandFactory();
        }
        else if(commandType==CommandType.SHOW_ACCOUNTS){
            return new ShowAccountsCommandFactory(profile);
        }
        else if(commandType==CommandType.CREATE_ACCOUNT){
            return new CreateAccountCommandFactory(profile);
        }
        else if(commandType==CommandType.REPLENISH){
            return new ReplenishCommandFactory(profile);
        }
        else if(commandType==CommandType.WITHDRAW){
            return new WithdrawCommandFactory(profile);
        }
        else if(commandType==CommandType.BORROW){
            return new BorrowCommandFactory(profile);
        }
        else if(commandType==CommandType.PAYOFF){
            return new PayOffCommandFactory(profile);
        }
        else if(commandType==CommandType.BAN){
            return new BanCommandFactory(profile);
        }
        else if(commandType==CommandType.BLOCK){
            return new BlockCommandFactory(profile);
        }
        else if(commandType==CommandType.UNBAN){
            return new UnbanCommandFactory(profile);
        }
        else if(commandType==CommandType.UNBLOCK){
            return new UnblockCommandFactory(profile);
        }
        else if(commandType==CommandType.SHOW_USERLIST){
            return new ShowUserlistCommandFactory(profile);
        }
        else if(commandType==CommandType.EXIT){
            return new EXITCommandFactory();
        }
        return null;
    }

    public abstract void execute(String... args) throws ServiceException;
    public abstract HashMap<?, ?> executeWithReturn(String... args) throws ServiceException;

}

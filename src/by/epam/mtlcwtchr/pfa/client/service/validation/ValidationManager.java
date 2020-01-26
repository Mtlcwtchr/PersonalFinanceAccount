package by.epam.mtlcwtchr.pfa.client.service.validation;

public final class ValidationManager {

    private static final String usernameFormat="[A-Za-z0-9_]{4,14}";
    private static final String passwordFormat="[A-Za-z0-9!_@#$%^&?]{4,16}";

    public static boolean usernameMatches(String username){
        return username.matches(usernameFormat);
    }

    public static boolean passwordMatches(String password){
        return password.matches(passwordFormat) &&
                !password.toLowerCase().equals(password);
    }


}

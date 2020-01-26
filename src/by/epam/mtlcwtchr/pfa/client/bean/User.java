package by.epam.mtlcwtchr.pfa.client.bean;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable, Cloneable, Comparable<User> {

    private int id;
    private String username;
    private String password;
    private boolean accessModifier = false;
    private boolean isBanned = false;

    public User(){}
    public User(String username, String password){
        this.username=username;
        this.password=password;
        calculateID();
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public void calculateID() {
        id = hashCode();
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getAccessModifier() {
        return accessModifier;
    }
    public void setAccessModifier(boolean accessModifier) {
        this.accessModifier = accessModifier;
    }
    public boolean isBanned() {
        return isBanned;
    }
    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public static User fromString(String s){
        User user = new User();
        Matcher idMatcher = Pattern.compile("\\[id: [a-z0-9]+?,").matcher(s);
        Matcher usernameMatcher = Pattern.compile("username: '[A-Za-z0-9]+?',").matcher(s);
        Matcher passwordMatcher = Pattern.compile("password: '[A-Za-z0-9]+?',").matcher(s);
        Matcher accessModifierMatcher = Pattern.compile("access modifier: '[A-Za-z]+?',").matcher(s);
        Matcher statusMatcher = Pattern.compile("status: '[A-Za-z]+?']").matcher(s);
        if(idMatcher.find())
            user.setId(Integer.parseInt(s.substring(idMatcher.start()+5,idMatcher.end()-1),16));
        if(usernameMatcher.find())
            user.setUsername(s.substring(usernameMatcher.start()+11,usernameMatcher.end()-2));
        if(passwordMatcher.find())
            user.setPassword(s.substring(passwordMatcher.start()+11,passwordMatcher.end()-2));
        if(accessModifierMatcher.find())
            user.setAccessModifier(s.substring(accessModifierMatcher.start()+18, accessModifierMatcher.end()-2).startsWith("a"));
        if(statusMatcher.find())
            user.setBanned(s.substring(statusMatcher.start()+9, statusMatcher.end() - 2).startsWith("b"));
        return user;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@[" +
                "id: " + Integer.toHexString(id) +
                ", username: '" + username + '\'' +
                ", password: '" + password + '\'' +
                ", access modifier: '" + (accessModifier ? "admin" : "customer") + '\'' +
                ", status: '" + (isBanned ? "banned']" : "active']");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o.getClass()==getClass())) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode()    {
        return 3*username.hashCode();
    }

    @Override
    public int compareTo(User o) {
        return id-o.id;
    }

}

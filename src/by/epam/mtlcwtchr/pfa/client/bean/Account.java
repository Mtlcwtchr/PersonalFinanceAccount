package by.epam.mtlcwtchr.pfa.client.bean;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account implements Serializable, Cloneable, Comparable<Account> {

    private int keyNumber;
    private int amount;
    private int debt;
    private boolean banned;

    public Account(){}
    public Account(int keyNumber){this.keyNumber=keyNumber;}

    public int getKeyNumber() {
        return keyNumber;
    }
    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getDebt() {
        return debt;
    }
    public void setDebt(int debt) {
        this.debt = debt;
    }
    public boolean isBanned() {
        return banned;
    }
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public static Account fromString(String s){
        Account account = new Account();
        Matcher keyMatcher = Pattern.compile("\\[account number: [a-z0-9]+?,").matcher(s);
        Matcher amountMatcher = Pattern.compile("amount on account: [a-z0-9]+?,").matcher(s);
        Matcher debtMatcher = Pattern.compile("debt on account: [a-z0-9]+?,").matcher(s);
        Matcher statusMatcher = Pattern.compile("account status: [a-z]+?]").matcher(s);
        if(keyMatcher.find())
        account.setKeyNumber(Integer.parseInt(s.substring(keyMatcher.start()+17,keyMatcher.end()-1),16));
        if(amountMatcher.find())
        account.setAmount(Integer.parseInt(s.substring(amountMatcher.start()+19,amountMatcher.end()-1)));
        if(debtMatcher.find())
        account.setDebt(Integer.parseInt(s.substring(debtMatcher.start()+17,debtMatcher.end()-1)));
        if(statusMatcher.find())
        account.setBanned(s.substring(statusMatcher.start()+16, statusMatcher.end() - 1).startsWith("b"));
        return account;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" +
                "[account number: " + Integer.toHexString(keyNumber) +
                ", amount on account: " + amount +
                ", debt on account: " + debt +
                ", account status: " + (banned ? "blocked]" : "active]");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o.getClass()==getClass())) return false;
        Account account = (Account) o;
        return getKeyNumber() == account.getKeyNumber() &&
                getAmount() == account.getAmount() &&
                getDebt() == account.getDebt() &&
                isBanned() == account.isBanned();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKeyNumber(), getAmount(), getDebt(), isBanned());
    }

    @Override
    public int compareTo(Account o) {
        return keyNumber-o.keyNumber;
    }

}

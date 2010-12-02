package net.froly.osw.server;

public class AccountInfo {
    String user;
    String pass;
    String host;

    public AccountInfo(String user, String pass, String host) {
        this.user = user;
        this.pass = pass;
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        return user+"@"+host;
    }
}

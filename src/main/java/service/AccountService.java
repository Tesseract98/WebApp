package service;

public class AccountService {
    private String name, password;
    private static AccountService accountService;

    private AccountService(){
    }

    public static AccountService getImplement(){
        if(accountService == null){
            accountService = new AccountService();
        }
        return accountService;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}

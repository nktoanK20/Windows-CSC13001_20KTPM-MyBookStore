package BUS;

import DAO.AccountDAO;
import POJO.AccountPOJO;

import java.util.ArrayList;

public class AccountBUS {
    public static ArrayList<AccountPOJO> getAll() {
        return AccountDAO.getAll();
    }

    public Boolean update(AccountPOJO accountPOJO) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.update(accountPOJO);
    }

    public Boolean updateActive(AccountPOJO accountPOJO) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.updateActive(accountPOJO);
    }

    public Boolean insert(AccountPOJO accountPOJO) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.insert(accountPOJO);
    }
    
    public AccountPOJO getAccountByUsername(String username) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.getAccountByUsername(username);
    }
}
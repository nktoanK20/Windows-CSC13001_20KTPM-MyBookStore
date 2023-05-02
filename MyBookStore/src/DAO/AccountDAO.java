package DAO;

import POJO.AccountPOJO;
import POJO.OrdersPOJO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAO {
    public static ArrayList<AccountPOJO> getAll() {
        ArrayList<AccountPOJO> result = null;
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM account";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                boolean isActive = rs.getBoolean("is_active");
                AccountPOJO account = new AccountPOJO(id, username, password, isActive);
                result.add(account);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountPOJO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    public Boolean update(AccountPOJO account) {
        boolean result = false;
        Connection connection = Database.createConnection();
        try {
            String sql = "UPDATE account SET password=? WHERE id=?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getPassword());
            statement.setString(2, account.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountPOJO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    public Boolean updateActive(AccountPOJO account) {
        boolean result = false;
        Connection connection = Database.createConnection();
        Boolean setActive = true;
        if (account.getIsActive()) {
            setActive = false;
        }
        try {
            String sql = "UPDATE account SET is_active=? WHERE id=?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, setActive);
            statement.setString(2, account.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountPOJO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    public Boolean insert(AccountPOJO account) {
        boolean result = false;
        Connection connection = Database.createConnection();
        try {
            String sql = "INSERT INTO account(id, username, password, is_active) VALUES (?,?,?,1);";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getId());
            statement.setString(2, account.getUsername());
            statement.setString(3, account.getPassword());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountPOJO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }
    
    public AccountPOJO getAccountByUsername(String username) {
        AccountPOJO account = null;
        Connection connection = Database.createConnection();
        
        try {
            String sql = "SELECT * FROM account WHERE username=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String password = rs.getString("password");
                boolean isActive = rs.getBoolean("is_active");
      
                account = new AccountPOJO(id, username, password, isActive);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountPOJO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return account;
    }

}

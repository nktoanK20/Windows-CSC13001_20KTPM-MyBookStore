package DAO;

import POJO.UserPOJO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
  public static ArrayList<UserPOJO> getAll() {
    ArrayList<UserPOJO> result = null;
    Connection connection = Database.createConnection();
    try {
      result = new ArrayList<>();
      Statement statement;
      statement = connection.createStatement();
      String query = "SELECT * FROM user";
      ResultSet rs = statement.executeQuery(query);
      while (rs.next()) {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String idAccount = rs.getString("id_account");
        String address = rs.getString("address");
        int role = rs.getInt("role");
        UserPOJO User = new UserPOJO(id, name, idAccount, address, role);
        result.add(User);
      }
      rs.close();
      statement.close();
    } catch (SQLException ex) {
      Logger.getLogger(UserPOJO.class.getName()).log(Level.SEVERE, null, ex);
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

  public ArrayList<UserPOJO> getUserNotDisable() {
    ArrayList<UserPOJO> listUser = new ArrayList<>();
    Connection conn = null;
    try {
      conn = Database.createConnection();
      String query = "select user.id, user.name, user.id_account, user.address, user.role from user, account where user.id_account = account.id and account.is_active = 1";
      PreparedStatement prst = conn.prepareStatement(query);
      ResultSet res = prst.executeQuery();
      while (res.next()) {
        String id = res.getString("id");
        String name = res.getString("name");
        String id_account = res.getString("id_account");
        String address = res.getString("address");
        int role = res.getInt("role");
        UserPOJO user = new UserPOJO(id, name, id_account, address, role);
        listUser.add(user);
      }
      res.close();
      prst.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return listUser;
  }

  public Boolean update(UserPOJO user) {
    boolean result = false;
    Connection connection = Database.createConnection();
    try {
      String sql = "UPDATE user SET name=?, id_account=?, address=?, role=? WHERE id=?;";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, user.getName());
      statement.setString(2, user.getIdAccount());
      statement.setString(3, user.getAddress());
      statement.setInt(4, user.getRole());
      statement.setString(5, user.getId());

      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) {
        result = true;
      }
      statement.close();
    } catch (SQLException ex) {
      Logger.getLogger(UserPOJO.class.getName()).log(Level.SEVERE, null, ex);
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

  public Boolean insert(UserPOJO user) {
    boolean result = false;
    Connection connection = Database.createConnection();
    try {
      String sql = "INSERT INTO user(id, name, id_account, address, role) VALUES (?,?,?,?,?);";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, user.getId());
      statement.setString(2, user.getName());
      statement.setString(3, user.getIdAccount());
      statement.setString(4, user.getAddress());
      statement.setInt(5, user.getRole());

      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) {
        result = true;
      }
      statement.close();
    } catch (SQLException ex) {
      Logger.getLogger(UserPOJO.class.getName()).log(Level.SEVERE, null, ex);
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
}

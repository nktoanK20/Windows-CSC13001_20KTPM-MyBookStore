package BUS;

import DAO.UserDAO;
import POJO.UserPOJO;

import java.util.ArrayList;

public class UserBUS {
  public ArrayList<UserPOJO> getUserNotDisable() {
    UserDAO userDao = new UserDAO();
    ArrayList<UserPOJO> res = userDao.getUserNotDisable();
    return res;
  }

  public static ArrayList<UserPOJO> getAll() {
    return UserDAO.getAll();
  }

  public boolean update(UserPOJO userPOJO) {
    UserDAO userDao = new UserDAO();

    return userDao.update(userPOJO);
  }

  public Boolean insert(UserPOJO userPOJO) {
    UserDAO userDao = new UserDAO();

    return userDao.insert(userPOJO);
  }
}
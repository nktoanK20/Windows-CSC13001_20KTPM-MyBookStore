/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.OrderDetailPOJO;
import POJO.OrdersPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bachl
 */
public class OrdersDAO {
    public String getMaxIDCategory() {
        String maxID = null;
        try {
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT max(id) from orders";
            ResultSet rs = statement.executeQuery(query);
            
            while(rs.next()){
                maxID = rs.getString("max(id)");
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
            maxID = "";
        }
        return maxID;
    }
    
    public boolean insertOrder(String id, String createAt, String createBy, String boughtBy, int sumCost) {
        try {
                Connection connection = Database.createConnection();

                //Prepared statement
                String query = "INSERT INTO orders " + "VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, id);
                pstmt.setString(2, createAt);
                pstmt.setString(3, createBy);
                pstmt.setString(4, boughtBy);
                pstmt.setInt(5, sumCost);
                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    }
    
    public boolean isEmployee(String id) {
        try {
                Connection connection = Database.createConnection();

                //Prepared statement
                String query = "SELECT name FROM user WHERE id = ?";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, id);
                boolean isHave = pstmt.execute();

                pstmt.close();
                connection.close();
                
                if(!isHave)
                    return false;
            } catch (SQLException ex) {
                Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    }
    
    public List<OrdersPOJO> getAllOrders() {
        List<OrdersPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT id, create_at, (SELECT name FROM user WHERE create_by = user.id) AS name_employee, (SELECT name FROM customer WHERE bought_by = customer.id) AS name_customer, sum_cost FROM orders";
            ResultSet rs = statement.executeQuery(query);
            
            while(rs.next()){
                String id = rs.getString("id");
                String createAt = rs.getString("create_at");
                String createBy = rs.getString("name_employee");
                String boughtBy = rs.getString("name_customer");
                int sumCost = rs.getInt("sum_cost");
      
                OrdersPOJO st = new OrdersPOJO(id, createAt, createBy, boughtBy, sumCost);
                ans.add(st);
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    public OrdersPOJO getOrdersById(String idOrder){
        OrdersPOJO ans = null;
        try {
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query = "SELECT id, create_at, bought_by, (SELECT name FROM user WHERE create_by = user.id) AS name_employee, (SELECT name FROM customer WHERE bought_by = customer.id) AS name_customer, sum_cost FROM orders WHERE id=?";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setString(1, idOrder);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String createAt = rs.getString("create_at");
                String boughtById = rs.getString("bought_by");
                String createBy = rs.getString("name_employee");
                String nameBoughtBy = rs.getString("name_customer");
                int sumCost = rs.getInt("sum_cost");
      
                ans = new OrdersPOJO(idOrder, createAt, createBy, nameBoughtBy, boughtById, sumCost);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
      
    public List<OrdersPOJO> getOrdersByFromToDate(String fromDate, String toDate){
        List<OrdersPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query = "SELECT id, create_at, (SELECT name FROM user WHERE create_by = user.id) AS name_employee, (SELECT name FROM customer WHERE bought_by = customer.id) AS name_customer, sum_cost FROM orders where create_at >= ? and create_at <= ?";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setString(1, fromDate);
            pstmt.setString(2, toDate);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String createAt = rs.getString("create_at");
                String createBy = rs.getString("name_employee");
                String boughtBy = rs.getString("name_customer");
                int sumCost = rs.getInt("sum_cost");
      
                OrdersPOJO st = new OrdersPOJO(id, createAt, createBy, boughtBy, sumCost);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<OrdersPOJO> getOrdersByMonth(int month, int year){
        List<OrdersPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query = "SELECT id, create_at, (SELECT name FROM user WHERE create_by = user.id) AS name_employee, (SELECT name FROM customer WHERE bought_by = customer.id) AS name_customer, sum_cost FROM orders where MONTH(create_at) = ? AND YEAR(create_at) = ?";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String createAt = rs.getString("create_at");
                String createBy = rs.getString("name_employee");
                String boughtBy = rs.getString("name_customer");
                int sumCost = rs.getInt("sum_cost");
      
                OrdersPOJO st = new OrdersPOJO(id, createAt, createBy, boughtBy, sumCost);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public boolean deleteOrder(String idOrder) {
        try {
                Connection connection =Database.createConnection();

                //Prepared statement
                String query = "DELETE FROM orders WHERE id=?";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, idOrder);
                
                pstmt.executeUpdate();

                pstmt.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    }
    
    public boolean insertPromotionOrder(String idPromotion, String idOrder) {
        try {
                Connection connection = Database.createConnection();

                //Prepared statement
                String query = "INSERT INTO promotion_order VALUES(?, ?)";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, idPromotion);
                pstmt.setString(2, idOrder);
                
                pstmt.executeUpdate();

                pstmt.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    }
    
    public int countOrdersInPromotion(String idPromotion) {
        int totalOrdersInPromotion = 0;
        
        try {
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query = "SELECT COUNT(?) AS total_orders FROM promotion_order";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            //Set parameters
            pstmt.setString(1, idPromotion);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                totalOrdersInPromotion = resultSet.getInt("total_orders");
            }

            pstmt.close();
            resultSet.close();
            connection.close();
        }catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
        return totalOrdersInPromotion;
    }

    public boolean deleteOrderPromotion(String idPromotion, String idOrder) {
        try {
            Connection connection = Database.createConnection();

            //Prepared statement
            String query = "DELETE FROM promotion_order WHERE id_promotion=? AND id_order=?";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            //Set parameters
            pstmt.setString(1, idPromotion);
            pstmt.setString(2, idOrder);
            
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    return true;
    }
}
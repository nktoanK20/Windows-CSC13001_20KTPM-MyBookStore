/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.StatisticsPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bachl
 */
public class StatisticsDAO {
    public List<StatisticsPOJO> getGroupedByBookFromDateToDate(String fromDate, String toDate){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
           
            String query =  "SELECT DISTINCT book.id, book.name," + 
                                            " (SELECT SUM(order_detail.quantity)" + 
                                            " FROM order_detail, orders" + 
                                            " WHERE order_detail.id_book = book.id and orders.id = order_detail.id_order and (orders.create_at >= ? and orders.create_at <= ?)) as quantity," +
                                            " (SELECT sum(order_detail.price)" + 
                                            " FROM order_detail, orders" + 
                                            " WHERE order_detail.id_book = book.id and orders.id = order_detail.id_order and (orders.create_at >= ? and orders.create_at <= ?)) as revenue " + 
                            "FROM book, orders, order_detail " + 
                            "WHERE book.id = order_detail.id_book and orders.id = order_detail.id_order and (orders.create_at >= ? and orders.create_at <= ?)";
            
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setString(1, fromDate);
            pstmt.setString(2, toDate);
            pstmt.setString(3, fromDate);
            pstmt.setString(4, toDate);
            pstmt.setString(5, fromDate);
            pstmt.setString(6, toDate);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<StatisticsPOJO> getGroupedByBookForMonth(int month, int year){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
           
            String query =  "SELECT DISTINCT book.id, book.name," + 
                                            " (SELECT SUM(order_detail.quantity)" + 
                                            " FROM order_detail, orders" + 
                                            " WHERE order_detail.id_book = book.id and orders.id = order_detail.id_order and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as quantity," +
                                            " (SELECT sum(order_detail.price)" + 
                                            " FROM order_detail, orders" + 
                                            " WHERE order_detail.id_book = book.id and orders.id = order_detail.id_order and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as revenue " + 
                            "FROM book, orders, order_detail " + 
                            "WHERE book.id = order_detail.id_book and orders.id = order_detail.id_order and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)";
            
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setInt(4, year);
            pstmt.setInt(5, month);
            pstmt.setInt(6, year);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<StatisticsPOJO> getGroupedByCategoryFromDateToDate(String fromDate, String toDate){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            String query =  "SELECT DISTINCT category.id, category.name," + 
                                            " (SELECT SUM(order_detail.quantity)" + 
                                            " FROM order_detail, orders, book_category" + 
                                            " WHERE orders.id = order_detail.id_order and order_detail.id_book = book_category.id_book "
                    + "                             and book_category.id_category = category.id and (orders.create_at >= ? and orders.create_at <= ?)) as quantity," +
                                            " (SELECT sum(order_detail.price)" + 
                                            " FROM order_detail, book_category, orders" + 
                                            " WHERE orders.id = order_detail.id_order and order_detail.id_book = book_category.id_book "
                    + "                             and book_category.id_category = category.id and (orders.create_at >= ? and orders.create_at <= ?)) as revenue " + 
                            "FROM category, orders, order_detail, book_category " + 
                            "WHERE book_category.id_book = order_detail.id_book and orders.id = order_detail.id_order "
                    + "            and book_category.id_category = category.id and (orders.create_at >= ? and orders.create_at <= ?)";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setString(1, fromDate);
            pstmt.setString(2, toDate);
            pstmt.setString(3, fromDate);
            pstmt.setString(4, toDate);
            pstmt.setString(5, fromDate);
            pstmt.setString(6, toDate);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<StatisticsPOJO> getGroupedByCategoryForMonth(int month, int year){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            String query =  "SELECT DISTINCT category.id, category.name," + 
                                            " (SELECT SUM(order_detail.quantity)" + 
                                            " FROM order_detail, orders, book_category" + 
                                            " WHERE orders.id = order_detail.id_order and order_detail.id_book = book_category.id_book "
                    + "                             and book_category.id_category = category.id and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as quantity," +
                                            " (SELECT sum(order_detail.price)" + 
                                            " FROM order_detail, book_category, orders" + 
                                            " WHERE orders.id = order_detail.id_order and order_detail.id_book = book_category.id_book "
                    + "                             and book_category.id_category = category.id and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as revenue " + 
                            "FROM category, orders, order_detail, book_category " + 
                            "WHERE book_category.id_book = order_detail.id_book and orders.id = order_detail.id_order "
                    + "            and book_category.id_category = category.id and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setInt(4, year);
            pstmt.setInt(5, month);
            pstmt.setInt(6, year);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<StatisticsPOJO> getGroupedByCustomerFromDateToDate(String fromDate, String toDate){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            String query =  "SELECT DISTINCT customer.id, customer.name," + 
                                            " (SELECT COUNT(orders.id)" + 
                                            " FROM orders" + 
                                            " WHERE orders.bought_by = customer.id and (orders.create_at >= ? and orders.create_at <= ?)) as quantity," +
                                            " (SELECT SUM(orders.sum_cost)" + 
                                            " FROM orders" + 
                                            " WHERE orders.bought_by = customer.id and (orders.create_at >= ? and orders.create_at <= ?)) as revenue " + 
                            "FROM customer, orders " + 
                            "WHERE customer.id = orders.bought_by and (orders.create_at >= ? and orders.create_at <= ?)";
            
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setString(1, fromDate);
            pstmt.setString(2, toDate);
            pstmt.setString(3, fromDate);
            pstmt.setString(4, toDate);
            pstmt.setString(5, fromDate);
            pstmt.setString(6, toDate);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<StatisticsPOJO> getGroupedByCustomerForMonth(int month, int year){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            String query =  "SELECT DISTINCT customer.id, customer.name," + 
                                            " (SELECT COUNT(orders.id)" + 
                                            " FROM orders" + 
                                            " WHERE orders.bought_by = customer.id and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as quantity," +
                                            " (SELECT SUM(orders.sum_cost)" + 
                                            " FROM orders" + 
                                            " WHERE orders.bought_by = customer.id and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as revenue " + 
                            "FROM customer, orders " + 
                            "WHERE customer.id = orders.bought_by and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)";
            
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setInt(4, year);
            pstmt.setInt(5, month);
            pstmt.setInt(6, year);
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<StatisticsPOJO> getGroupedByEmployeeFromDateToDate(String fromDate, String toDate){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            String query =  "SELECT DISTINCT user.id, user.name," + 
                                            " (SELECT COUNT(orders.id)" + 
                                            " FROM orders" + 
                                            " WHERE orders.create_by = user.id and (orders.create_at >= ? and orders.create_at <= ?)) as quantity," +
                                            " (SELECT SUM(orders.sum_cost)" + 
                                            " FROM orders" + 
                                            " WHERE orders.create_by = user.id and (orders.create_at >= ? and orders.create_at <= ?)) as revenue " + 
                            "FROM user, orders " + 
                            "WHERE user.id = orders.create_by and (orders.create_at >= ? and orders.create_at <= ?)";
            
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setString(1, fromDate);
            pstmt.setString(2, toDate);
            pstmt.setString(3, fromDate);
            pstmt.setString(4, toDate);
            pstmt.setString(5, fromDate);
            pstmt.setString(6, toDate);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<StatisticsPOJO> getGroupedByEmployeeForMonth(int month, int year){
        List<StatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            String query =  "SELECT DISTINCT user.id, user.name," + 
                                            " (SELECT COUNT(orders.id)" + 
                                            " FROM orders" + 
                                            " WHERE orders.create_by = user.id and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as quantity," +
                                            " (SELECT SUM(orders.sum_cost)" + 
                                            " FROM orders" + 
                                            " WHERE orders.create_by = user.id and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)) as revenue " + 
                            "FROM user, orders " + 
                            "WHERE user.id = orders.create_by and (MONTH(orders.create_at) = ? and YEAR(orders.create_at) = ?)";
            
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setInt(4, year);
            pstmt.setInt(5, month);
            pstmt.setInt(6, year);
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int revenue = rs.getInt("revenue");
      
                StatisticsPOJO st = new StatisticsPOJO(id, name, quantity, revenue);
                ans.add(st);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
}

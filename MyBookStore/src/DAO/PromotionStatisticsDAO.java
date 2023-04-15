/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.PromotionStatisticsPOJO;
import POJO.PromotionTopsPOJO;
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
public class PromotionStatisticsDAO {
    public List<PromotionStatisticsPOJO> getPromotion() {
        List<PromotionStatisticsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            String query =  "SELECT DISTINCT promotion.id, promotion.name, " + 
                                             " (SELECT COUNT(promotion_order.id_order)" + 
                                             " FROM promotion_order" + 
                                             " WHERE promotion.id = promotion_order.id_promotion) as total_orders," + 
                                             " (SELECT COUNT(orders.bought_by)" + 
                                             " FROM promotion_order, orders" + 
                                             " WHERE promotion_order.id_order = orders.id and promotion_order.id_promotion = promotion.id) as total_customers," + 
                                             " (SELECT SUM(orders.sum_cost)" + 
                                             " FROM promotion_order, orders" + 
                                             " WHERE promotion_order.id_order = orders.id and promotion.id = promotion_order.id_promotion) as total_revenue " + 
                            "FROM promotion, promotion_order " + 
                            "WHERE promotion.id = promotion_order.id_promotion";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                int totalOrders = rs.getInt("total_orders");
                int totalCustomers = rs.getInt("total_customers");
                int totalRevenue = rs.getInt("total_revenue");
      
                PromotionStatisticsPOJO st = new PromotionStatisticsPOJO(id, name, totalOrders, totalCustomers, totalRevenue);
                ans.add(st);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionStatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<PromotionTopsPOJO> getTopKOrders(int kTops){
        List<PromotionTopsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query =  "SELECT DISTINCT promotion.id AS id_promotion, orders.id AS id_order, orders.sum_cost " + 
                            "FROM promotion, orders, promotion_order " +
                            "WHERE promotion_order.id_order = orders.id AND promotion.id = promotion_order.id_promotion " + 
                            "ORDER BY orders.sum_cost desc " + 
                            "LIMIT ?";
                    
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            //Set parameters
            pstmt.setInt(1, kTops);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String idPromotion = rs.getString("id_promotion");
                String idOrder = rs.getString("id_order");
                int sumCost = rs.getInt("sum_cost");
                
                PromotionTopsPOJO pt = new PromotionTopsPOJO(idPromotion, idOrder, "", sumCost);
                ans.add(pt);
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
    
    public List<PromotionTopsPOJO> getTopKBooks(int kTops){
        List<PromotionTopsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query =  "SELECT DISTINCT promotion.id AS id_promotion, book.id AS id_book, book.name, " +
                            "(SELECT SUM(order_detail.price) " +
                            "FROM order_detail " +
                            "WHERE promotion.id = promotion_book.id_promotion AND promotion_book.id_book = book.id " +
                            "AND order_detail.id_book = book.id) AS sum_cost " +
                            "FROM promotion, promotion_book, book " +
                            "WHERE promotion_book.id_book = book.id AND promotion.id = promotion_book.id_promotion " +
                            "ORDER BY sum_cost desc " + 
                            "LIMIT ?";
                    
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            //Set parameters
            pstmt.setInt(1, kTops);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String idPromotion = rs.getString("id_promotion");
                String idBook = rs.getString("id_book");
                String name = rs.getString("name");
                int sumCost = rs.getInt("sum_cost");
                
                PromotionTopsPOJO pt = new PromotionTopsPOJO(idPromotion, idBook, name, sumCost);
                ans.add(pt);
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
    
    public List<PromotionTopsPOJO> getTopKCustomers(int kTops){
        List<PromotionTopsPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query =  "SELECT DISTINCT promotion.id AS id_promotion, orders.bought_by AS id_customer, " +
                            "(SELECT customer.name FROM customer WHERE customer.id = orders.bought_by) AS name_customer, " +
                            "(SELECT SUM(orders.sum_cost) " +
                            "FROM orders " +
                            "WHERE promotion.id = promotion_order.id_promotion AND promotion_order.id_order = orders.id) AS sum_cost " +
                            "FROM promotion, promotion_order, orders " +
                            "WHERE promotion_order.id_order = orders.id AND promotion.id = promotion_order.id_promotion " +
                            "ORDER BY sum_cost desc " +
                            "LIMIT ?";
                    
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            //Set parameters
            pstmt.setInt(1, kTops);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String idPromotion = rs.getString("id_promotion");
                String idCustomer = rs.getString("id_customer");
                String name = rs.getString("name_customer");
                int sumCost = rs.getInt("sum_cost");
                
                PromotionTopsPOJO pt = new PromotionTopsPOJO(idPromotion, idCustomer, name, sumCost);
                ans.add(pt);
            }
            
            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionStatisticsDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
}

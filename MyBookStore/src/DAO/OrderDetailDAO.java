/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.OrderDetailPOJO;
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
public class OrderDetailDAO {
    public boolean addOrderDetail(List<OrderDetailPOJO> orderDetailList) {
        try {
            Connection connection = Database.createConnection();
            PreparedStatement pstmt = null;

            //Prepared statement
            for(int i = 0; i < orderDetailList.size(); i++) {
                String query = "INSERT INTO order_detail VALUES(?, ?, ?, ?)";
                pstmt = connection.prepareStatement(query);

                //Set parameters
                String idOrder = orderDetailList.get(i).getIdOrder();
                String idBook = orderDetailList.get(i).getIdBook();
                int quantity = orderDetailList.get(i).getQuantity();
                int price = orderDetailList.get(i).getPrice();

                pstmt.setString(1, idOrder);
                pstmt.setString(2, idBook);
                pstmt.setInt(3, quantity);
                pstmt.setInt(4, price);

                pstmt.executeUpdate();
            }

            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public ArrayList<OrderDetailPOJO> getListBooksInOrder(String id) {
        ArrayList<OrderDetailPOJO> listBooksInOrder = null;
        
        try {
            listBooksInOrder = new ArrayList();
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query = "SELECT order_detail.id_book, (SELECT name FROM book WHERE book.id = order_detail.id_book) AS name_book, (SELECT percent FROM promotion, promotion_book, orders WHERE order_detail.id_book = promotion_book.id_book AND promotion_book.id_promotion = promotion.id AND (order_detail.id_order = orders.id AND orders.create_at >= promotion.start_date AND orders.create_at <= promotion.end_date)) AS percent_sale, quantity, price FROM order_detail WHERE id_order=?";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            
            //Set parameters
            pstmt.setString(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String idBook = rs.getString("id_book");
                String nameBook = rs.getString("name_book");
                double percentSale = rs.getDouble("percent_sale");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
      
               OrderDetailPOJO orderDetail = new OrderDetailPOJO(id, idBook, nameBook, percentSale, quantity, price);
               listBooksInOrder.add(orderDetail);
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch(SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listBooksInOrder;
    }
    
    public boolean deleteOrder(String idOrder) {
       try {
            Connection connection = Database.createConnection();

            //Prepared statement
            String query = "DELETE FROM order_detail WHERE id_order=?";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            //Set parameters
            pstmt.setString(1, idOrder);

            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
}

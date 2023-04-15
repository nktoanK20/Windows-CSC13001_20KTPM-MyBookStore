/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.OrderDetailPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                    String query = "INSERT INTO order_detail " + "VALUES(?, ?, ?, ?)";
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
}

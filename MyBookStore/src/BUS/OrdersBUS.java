/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.OrdersDAO;
import POJO.OrdersPOJO;
import java.util.List;

/**
 *
 * @author bachl
 */
public class OrdersBUS {
    public String getMaxIDOrder() {
        OrdersDAO da = new OrdersDAO();
        
        return da.getMaxIDCategory();
    }
    
    public boolean insertOrder(String id, String createAt, String createBy, String boughtBy, int sumCost) {
        OrdersDAO da = new OrdersDAO();
        return da.insertOrder(id, createAt, createBy, boughtBy, sumCost);
    }
    
    public boolean isEmployee(String id) {
        OrdersDAO da = new OrdersDAO();
        return da.isEmployee(id);
    }
    
    public List<OrdersPOJO> getAllOrders() {
        OrdersDAO da = new OrdersDAO();
        return da.getAllOrders();
    }
    
    public List<OrdersPOJO> getOrdersByFromToDate(String fromDate, String toDate) {
        OrdersDAO da = new OrdersDAO();
        return da.getOrdersByFromToDate(fromDate, toDate);
    }
    
    public List<OrdersPOJO> getOrdersByMonth(int month, int year) {
        OrdersDAO da = new OrdersDAO();
        return da.getOrdersByMonth(month, year);
    }
}

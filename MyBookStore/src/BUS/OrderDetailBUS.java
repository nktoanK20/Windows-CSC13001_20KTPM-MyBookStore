/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.OrderDetailDAO;
import POJO.OrderDetailPOJO;
import java.util.List;

/**
 *
 * @author bachl
 */
public class OrderDetailBUS {
    public boolean addOrderDetail(List<OrderDetailPOJO> orderDetailList) {
        OrderDetailDAO da = new OrderDetailDAO();
        return da.addOrderDetail(orderDetailList);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CustomerDAO;
import POJO.CustomerPOJO;

public class CustomerBUS {
    public String getMaxIDCustomer() {
        CustomerDAO da = new CustomerDAO();
        return da.getMaxIDCustomer();
    }
    
    public boolean addNewCustomer(CustomerPOJO cus) {
        CustomerDAO da = new CustomerDAO();
        return da.addNewCustomer(cus);
    }
    
    public CustomerPOJO getCustomerById(String idCustomer) {
        CustomerDAO da = new CustomerDAO();
        return da.getCustomerById(idCustomer);
    }
}

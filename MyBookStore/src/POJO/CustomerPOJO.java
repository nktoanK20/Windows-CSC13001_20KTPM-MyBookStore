/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author bachl
 */
public class CustomerPOJO {
    String id;
    String name;
    boolean officialCustomer;
    double discount;
    
    public CustomerPOJO() {
    
    }

    public CustomerPOJO(String id, String name, boolean officialCustomer, double discount) {
        this.id = id;
        this.name = name;
        this.officialCustomer = officialCustomer;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOfficialCustomer() {
        return officialCustomer;
    }

    public void setOfficialCustomer(boolean officialCustomer) {
        this.officialCustomer = officialCustomer;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author bachl
 */
public class PromotionStatisticsPOJO {
    String id;
    String name;
    int totalOrders;
    int totalCustomers;
    int totalRevenue;
    
    public PromotionStatisticsPOJO() {
    
    }
    
    public PromotionStatisticsPOJO(String id, String name, int totalOrders, int totalCustomers, int totalRevenue) {
        this.id = id;
        this.name = name;
        this.totalOrders = totalOrders;
        this.totalCustomers = totalCustomers;
        this.totalRevenue = totalRevenue;
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

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}

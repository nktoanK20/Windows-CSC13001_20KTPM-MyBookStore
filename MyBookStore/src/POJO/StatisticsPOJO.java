/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nguyen Huu Loc - 20127551
 */
public class StatisticsPOJO {
    String id;
    String name;
    int quantity;
    int revenue;
    
    public StatisticsPOJO() {
    
    }
    
    public StatisticsPOJO(String id, String name, int quantity, int revenue) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.revenue = revenue;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setPrice(int revenue) {
        this.revenue = revenue;
    }
    
}

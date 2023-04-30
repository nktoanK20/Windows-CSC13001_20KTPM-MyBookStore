/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author bachl
 */
public class OrderDetailPOJO {
    String idOrder;
    String idBook;
    int quantity;
    int price;
    String nameBook;
    double percentSale;
    
    
    public OrderDetailPOJO() {
    
    }
    
    public OrderDetailPOJO(String idOrder, String idBook, int quantity, int price) {
        this.idOrder = idOrder;
        this.idBook = idBook;
        this.quantity = quantity;
        this.price = price;
    }
    
    public OrderDetailPOJO(String idOrder, String idBook, String nameBook, double percentSale, int quantity, int price) {
        this.idOrder = idOrder;
        this.idBook = idBook;
        this.nameBook = nameBook;
        this.percentSale = percentSale;
        this.quantity = quantity;
        this.price = price;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public double getPercentSale() {
        return percentSale;
    }

    public void setPercentSale(double percentSale) {
        this.percentSale = percentSale;
    }
}

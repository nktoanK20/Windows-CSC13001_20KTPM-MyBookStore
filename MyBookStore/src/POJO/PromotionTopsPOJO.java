/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author bachl
 */
public class PromotionTopsPOJO {
    String idPromotion;
    String id;
    String name;
    int sumCost;
    
    public PromotionTopsPOJO() {
    
    }
    
    public PromotionTopsPOJO(String idPromotion, String id, String name, int sumCost) {
        this.idPromotion = idPromotion;
        this.id = id;
        this.name = name;
        this.sumCost = sumCost;
    }

    public String getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(String idPromotion) {
        this.idPromotion = idPromotion;
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

    public int getSumCost() {
        return sumCost;
    }

    public void setSumCost(int sumCost) {
        this.sumCost = sumCost;
    }
}

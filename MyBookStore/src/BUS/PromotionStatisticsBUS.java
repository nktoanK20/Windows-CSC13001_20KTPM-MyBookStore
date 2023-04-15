/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PromotionStatisticsDAO;
import POJO.PromotionStatisticsPOJO;
import POJO.PromotionTopsPOJO;
import java.util.List;

/**
 *
 * @author bachl
 */
public class PromotionStatisticsBUS {
    public List<PromotionStatisticsPOJO> getPromotion(){
        PromotionStatisticsDAO da = new PromotionStatisticsDAO();
        return da.getPromotion();
    }
    
    public List<PromotionTopsPOJO> getTopKOrders(int kTops) {
        PromotionStatisticsDAO da = new PromotionStatisticsDAO();
        return da.getTopKOrders(kTops);
    }
    
    public List<PromotionTopsPOJO> getTopKBooks(int kTops) {
        PromotionStatisticsDAO da = new PromotionStatisticsDAO();
        return da.getTopKBooks(kTops);
    }
    
    public List<PromotionTopsPOJO> getTopKCustomers(int kTops) {
        PromotionStatisticsDAO da = new PromotionStatisticsDAO();
        return da.getTopKCustomers(kTops);
    }
}

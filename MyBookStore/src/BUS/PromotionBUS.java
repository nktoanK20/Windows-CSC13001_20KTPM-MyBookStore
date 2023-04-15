package BUS;

import DAO.PromotionDAO;
import POJO.PromotionPOJO;

import java.util.ArrayList;

public class PromotionBUS {
    public static ArrayList<PromotionPOJO> getAll(){
        return PromotionDAO.getAll();
    }

    public static ArrayList<PromotionPOJO> getPastPromotions(){
        return PromotionDAO.getPastPromotions();
    }

    public static ArrayList<PromotionPOJO> getCurrentPromotions(){
        return PromotionDAO.getCurrentPromotions();
    }

    public static ArrayList<PromotionPOJO> getUpcomingPromotions(){
        return PromotionDAO.getUpcomingPromotions();
    }


    public static PromotionPOJO getOne(String promotionId){
        return PromotionDAO.getOne(promotionId);
    }

    public static Boolean updateOne(String oldPromotionId, PromotionPOJO modifiedPromotion){
        return PromotionDAO.updateOne(oldPromotionId,modifiedPromotion);
    }

    public static Boolean insertOne(PromotionPOJO promotion){
        return PromotionDAO.insertOne(promotion);
    }

    public static Boolean insertAppliedBooks(String promotionId, ArrayList<String> listBookId){
        return PromotionDAO.insertAppliedBooks(promotionId, listBookId);
    }

    public static Boolean deleteNotAppliedBooks(String promotionId, ArrayList<String> listBookId){
        return PromotionDAO.deleteNotAppliedBooks(promotionId, listBookId);
    }

    public static Boolean enable(String promotionId){
        return PromotionDAO.enable(promotionId);
    }

    public static Boolean disable(String promotionId){
        return PromotionDAO.disable(promotionId);
    }
}

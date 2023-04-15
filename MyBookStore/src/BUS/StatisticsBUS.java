/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.StatisticsDAO;
import POJO.StatisticsPOJO;
import java.util.List;

/**
 *
 * @author bachl
 */
public class StatisticsBUS {
    public List<StatisticsPOJO> getGroupedByBookFromDateToDate(String fromDate, String toDate){
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByBookFromDateToDate(fromDate, toDate);
    }
    
    public List<StatisticsPOJO> getGroupedByBookForMonth(int month, int year) {
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByBookForMonth(month, year);
    }
    
    public List<StatisticsPOJO> getGroupedByCategoryFromDateToDate(String fromDate, String toDate) {
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByCategoryFromDateToDate(fromDate, toDate);
    }
    
    public List<StatisticsPOJO> getGroupedByCategoryForMonth(int month, int year) {
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByCategoryForMonth(month, year);
    }
    
    public List<StatisticsPOJO> getGroupedByCustomerFromDateToDate(String fromDate, String toDate){
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByCustomerFromDateToDate(fromDate, toDate);
    }
    
    public List<StatisticsPOJO> getGroupedByCustomerForMonth(int month, int year){
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByCustomerForMonth(month, year);
    }
    
    public List<StatisticsPOJO> getGroupedByEmployeeFromDateToDate(String fromDate, String toDate) {
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByEmployeeFromDateToDate(fromDate, toDate);
    }
    
    public List<StatisticsPOJO> getGroupedByEmployeeForMonth(int month, int year) {
        StatisticsDAO da = new StatisticsDAO();
        
        return da.getGroupedByEmployeeForMonth(month, year);
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import POJO.CategoryPOJO;
import DAO.CategoryDAO;

import java.util.ArrayList;
import java.util.List;

public class CategoryBUS {
    static public ArrayList<CategoryPOJO> getAll(){
        return CategoryDAO.getAll();
    }

    public List<CategoryPOJO> getEnabledAll(){
        CategoryDAO da = new CategoryDAO();
        
        return da.getEnabledAll();
    }
    
    public List<CategoryPOJO> getDisabledAll() {
        CategoryDAO da = new CategoryDAO();
        
        return da.getDisabledAll();
    }
    
    public CategoryPOJO getCategory(String keyWords) {
        CategoryDAO da = new CategoryDAO();
        
        return da.getCategory(keyWords);
    }
    
    public List<CategoryPOJO> getEnabledAllSorted(String typeSort, String orderSort) {
        CategoryDAO da = new CategoryDAO();
        
        return da.getEnabledAllSorted(typeSort, orderSort);
    }
    
     public List<CategoryPOJO> getDisabledAllSorted(String typeSort, String orderSort) {
        CategoryDAO da = new CategoryDAO();
        
        return da.getDisabledAllSorted(typeSort, orderSort);
    }
    
    public boolean addNewCategory(String dataCategory[]) {
        CategoryDAO da = new CategoryDAO();
        
        return da.addNewCategory(dataCategory);
    }
    
    public String getMaxIDCategory() {
        CategoryDAO da = new CategoryDAO();
        
        return da.getMaxIDCategory();
    }
    
    public boolean updateCategory(String idCategory, String nameCategory, String descriptionCategory) {
        CategoryDAO da = new CategoryDAO();
        
        return da.updateCategory(idCategory, nameCategory, descriptionCategory);
    }
    
    public boolean deleteCategory(String idCategory) {
        CategoryDAO da = new CategoryDAO();
        
        return da.deleteCategory(idCategory);
    }
    
    public boolean enabledCategory(String idCategory) {
        CategoryDAO da = new CategoryDAO();
        
        return da.enabledCategory(idCategory);
    }
    
    public boolean disabledCategory(String idCategory) {
        CategoryDAO da = new CategoryDAO();
        
        return da.disabledCategory(idCategory);
    }

    static public ArrayList<CategoryPOJO> getCategoryListOfBook(String bookId) {
        return CategoryDAO.getCategoryListOfBook(bookId);
    }
}

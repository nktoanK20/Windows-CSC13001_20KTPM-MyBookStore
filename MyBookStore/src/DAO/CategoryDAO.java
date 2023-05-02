/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.lang.reflect.AnnotatedArrayType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

import POJO.AuthorPOJO;
import POJO.BookPOJO;
import POJO.CategoryPOJO;
import POJO.PromotionPOJO;

import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class CategoryDAO {
    static public ArrayList<CategoryPOJO> getAll(){
        ArrayList<CategoryPOJO> result = null;
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM category";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Boolean isEnabled = rs.getBoolean("is_enabled");

                CategoryPOJO category = new CategoryPOJO(id, name, description, isEnabled);
                result.add(category);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookPOJO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    public List<CategoryPOJO> getEnabledAll(){
        List<CategoryPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM category WHERE is_enabled=1";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                boolean isEnabled = rs.getBoolean("is_enabled");
      
                CategoryPOJO st = new CategoryPOJO(id, name, description, isEnabled);
                ans.add(st);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<CategoryPOJO> getDisabledAll() {
        List<CategoryPOJO> ans = null;
        try {
            ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM category WHERE is_enabled=0";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                boolean isEnabled = rs.getBoolean("is_enabled");
                     
                CategoryPOJO st = new CategoryPOJO(id, name, description, isEnabled);
                ans.add(st);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<CategoryPOJO> getEnabledAllSorted(String typeSort, String orderSort){
        List<CategoryPOJO> ans = null;
        try {
              ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            
            String query;
            if(typeSort.equals("id")) {
                if(orderSort.equals("asc"))
                    query = "SELECT * FROM category WHERE is_enabled=1 order by id asc";
                else
                    query = "SELECT * FROM category WHERE is_enabled=1 order by id desc";
                    
            }
            else {
                if(orderSort.equals("asc"))
                    query = "SELECT * FROM category WHERE is_enabled=1 order by name asc";
                else
                    query = "SELeCT * FROM category WHERE is_enabled=1 order by name desc";
            }
            
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                boolean isEnabled = rs.getBoolean("is_enabled");
      
                CategoryPOJO st = new CategoryPOJO(id, name, description, isEnabled);
                ans.add(st);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public List<CategoryPOJO> getDisabledAllSorted(String typeSort, String orderSort){
        List<CategoryPOJO> ans = null;
        try {
              ans = new ArrayList<>();
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            
            String query;
            if(typeSort.equals("id")) {
                if(orderSort.equals("asc"))
                    query = "SELECT * FROM category WHERE is_enabled=0 ORDER BY id asc";
                else
                    query = "SELeCT * FROM category WHERE is_enabled=0 ORDER BY id desc";
                    
            }
            else {
                if(orderSort.equals("asc"))
                    query = "SELECT * FROM category WHERE is_enabled=0 ORDER BY name asc";
                else
                    query = "SELeCT * FROM category WHERE is_enabled=0 ORDER BY name desc";
            }
            
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                boolean isEnabled = rs.getBoolean("is_enabled");
      
                CategoryPOJO st = new CategoryPOJO(id, name, description, isEnabled);
                ans.add(st);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public CategoryPOJO getCategory(String keyWords){
        CategoryPOJO ans = null;
        try {
            Connection connection = Database.createConnection();
            
            //Prepared statement
            String query = "SELECT * FROM category where id=? or name=?";
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            //Set parameters
            pstmt.setString(1, keyWords);
            pstmt.setString(2, keyWords);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                boolean isEnabled = rs.getBoolean("is_enabled");
      
                ans = new CategoryPOJO(id, name, description, isEnabled);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            ans = null;
        }
        return ans;
    }
    
    public boolean addNewCategory(String[] dataCategory) {
            try {
                Connection connection = Database.createConnection();

                //Prepared statement
                String query = "INSERT INTO category " + "VALUES(?, ?, ?, ?)";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, dataCategory[0]);
                pstmt.setString(2, dataCategory[1]);
                pstmt.setString(3, dataCategory[2]);
                pstmt.setBoolean(4, true);
                System.out.println(dataCategory[1]);
                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    }
    
    public String getMaxIDCategory() {
        String maxID = null;
        try {
            Connection connection = Database.createConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT max(id) from category";
            ResultSet rs = statement.executeQuery(query);
            
            while(rs.next()){
                maxID = rs.getString("max(id)");
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            maxID = "";
        }
        return maxID;
    }
    
    public boolean updateCategory(String idCategory, String nameCategory, String descriptionCategory) {
        try {
                Connection connection = Database.createConnection();

                //Prepared statement
                String query = "UPDATE category " + "SET name=?, description=? "
                        + "WHERE id=?";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, nameCategory);
                pstmt.setString(2, descriptionCategory);
                pstmt.setString(3, idCategory);

                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    }
    
    public boolean deleteCategory(String idCategory) {
        try {
                Connection connection =Database.createConnection();

                //Prepared statement
                String query = "Delete from category WHERE id=?";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, idCategory);
                
                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    }
    
    public boolean enabledCategory(String idCategory) {
        try {
                Connection connection = Database.createConnection();

                //Prepared statement
                String query = "UPDATE category " + "SET is_enabled=true " + "WHERE id=?";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, idCategory);

                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    
    }
    
    public boolean disabledCategory(String idCategory) {
        try {
                Connection connection = Database.createConnection();

                //Prepared statement
                String query = "UPDATE category " + "SET is_enabled=false "
                        + "WHERE id=?";
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement(query);
                //Set parameters
                pstmt.setString(1, idCategory);

                pstmt.executeUpdate();

                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        return true;
    
    }

    static public ArrayList<CategoryPOJO> getCategoryListOfBook(String bookId){
        ArrayList<CategoryPOJO> result = new ArrayList<>();
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            String query = "SELECT * FROM book_category as bc, category as c" +
                    " WHERE bc.id_book=? and bc.id_category = c.id";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bookId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                boolean isEnabled = rs.getBoolean("is_enabled");
                CategoryPOJO category = new CategoryPOJO(id, name, description, isEnabled);
                result.add(category);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookPOJO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }
}


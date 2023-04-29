package DAO;

import POJO.BookPOJO;
import POJO.PromotionPOJO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PromotionDAO {
    public static ArrayList<PromotionPOJO> getAll(){
        ArrayList<PromotionPOJO> result = null;
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM promotion";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                Double percent = rs.getDouble("percent");
                String applyOption = rs.getString("apply_option");
                Integer limitOrders = rs.getInt("limit_orders");
                Boolean enabled = rs.getBoolean("is_enabled");

                ArrayList<BookPOJO> listBook = new ArrayList<>();
                String query1 = "SELECT * " +
                        "FROM promotion_book as pb, book as b" +
                        " WHERE pb.id_promotion=? and pb.id_book = b.id";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1, id);
                ResultSet rs1 = statement1.executeQuery();
                while(rs1.next()){
                    String id_book = rs1.getString("id_book");
                    String name_book = rs1.getString("name");
                    String id_publisher = rs1.getString("id_publisher");
                    int price = rs1.getInt("price");
                    int stock = rs1.getInt("stock");
                    int totalPurchase = rs1.getInt("total_purchase");
                    java.sql.Date releaseDate = rs1.getDate("release_date");
                    Boolean enabled_book = rs1.getBoolean("is_enabled");
                    BookPOJO book = new BookPOJO(id_book, name_book, id_publisher, price, stock, totalPurchase, releaseDate, enabled_book);
                    listBook.add(book);
                }


                PromotionPOJO promotion = new PromotionPOJO(id, name, description,
                        startDate, endDate, percent, applyOption, limitOrders, enabled, listBook);
                result.add(promotion);
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

    public static ArrayList<PromotionPOJO> getPastPromotions(){
        ArrayList<PromotionPOJO> result = null;
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM promotion WHERE end_date < curdate()";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                Double percent = rs.getDouble("percent");
                String applyOption = rs.getString("apply_option");
                Integer limitOrders = rs.getInt("limit_orders");
                Boolean enabled = rs.getBoolean("is_enabled");

                ArrayList<BookPOJO> listBook = new ArrayList<>();
                String query1 = "SELECT * " +
                        "FROM promotion_book as pb, book as b" +
                        " WHERE pb.id_promotion=? and pb.id_book = b.id";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1, id);
                ResultSet rs1 = statement1.executeQuery();
                while(rs1.next()){
                    String id_book = rs1.getString("id_book");
                    String name_book = rs1.getString("name");
                    String id_publisher = rs1.getString("id_publisher");
                    int price = rs1.getInt("price");
                    int stock = rs1.getInt("stock");
                    int totalPurchase = rs1.getInt("total_purchase");
                    java.sql.Date releaseDate = rs1.getDate("release_date");
                    Boolean enabled_book = rs1.getBoolean("is_enabled");
                    BookPOJO book = new BookPOJO(id_book, name_book, id_publisher, price, stock, totalPurchase, releaseDate, enabled_book);
                    listBook.add(book);
                }
                PromotionPOJO promotion = new PromotionPOJO(id, name, description,
                        startDate, endDate, percent, applyOption, limitOrders, enabled, listBook);
                result.add(promotion);
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

    public static ArrayList<PromotionPOJO> getCurrentPromotions(){
        ArrayList<PromotionPOJO> result = null;
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM promotion where curdate() between start_date and end_date";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                Double percent = rs.getDouble("percent");
                String applyOption = rs.getString("apply_option");
                Integer limitOrders = rs.getInt("limit_orders");
                Boolean enabled = rs.getBoolean("is_enabled");
                ArrayList<BookPOJO> listBook = new ArrayList<>();
                String query1 = "SELECT * " +
                        "FROM promotion_book as pb, book as b" +
                        " WHERE pb.id_promotion=? and pb.id_book = b.id";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1, id);
                ResultSet rs1 = statement1.executeQuery();
                while(rs1.next()){
                    String id_book = rs1.getString("id_book");
                    String name_book = rs1.getString("name");
                    String id_publisher = rs1.getString("id_publisher");
                    int price = rs1.getInt("price");
                    int stock = rs1.getInt("stock");
                    int totalPurchase = rs1.getInt("total_purchase");
                    java.sql.Date releaseDate = rs1.getDate("release_date");
                    Boolean enabled_book = rs1.getBoolean("is_enabled");
                    BookPOJO book = new BookPOJO(id_book, name_book, id_publisher, price, stock, totalPurchase, releaseDate, enabled_book);
                    listBook.add(book);
                }
                PromotionPOJO promotion = new PromotionPOJO(id, name, description,
                        startDate, endDate, percent, applyOption, limitOrders, enabled, listBook);
                result.add(promotion);
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

    public static ArrayList<PromotionPOJO> getUpcomingPromotions(){
        ArrayList<PromotionPOJO> result = null;
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            Statement statement;
            statement = connection.createStatement();
            String query = "SELECT * FROM promotion where start_date > curdate()";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                Double percent = rs.getDouble("percent");
                String applyOption = rs.getString("apply_option");
                Integer limitOrders = rs.getInt("limit_orders");
                Boolean enabled = rs.getBoolean("is_enabled");
                ArrayList<BookPOJO> listBook = new ArrayList<>();
                String query1 = "SELECT * " +
                        "FROM promotion_book as pb, book as b" +
                        " WHERE pb.id_promotion=? and pb.id_book = b.id";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1, id);
                ResultSet rs1 = statement1.executeQuery();
                while(rs1.next()){
                    String id_book = rs1.getString("id_book");
                    String name_book = rs1.getString("name");
                    String id_publisher = rs1.getString("id_publisher");
                    int price = rs1.getInt("price");
                    int stock = rs1.getInt("stock");
                    int totalPurchase = rs1.getInt("total_purchase");
                    java.sql.Date releaseDate = rs1.getDate("release_date");
                    Boolean enabled_book = rs1.getBoolean("is_enabled");
                    BookPOJO book = new BookPOJO(id_book, name_book, id_publisher, price, stock, totalPurchase, releaseDate, enabled_book);
                    listBook.add(book);
                }
                PromotionPOJO promotion = new PromotionPOJO(id, name, description,
                        startDate, endDate, percent, applyOption, limitOrders, enabled, listBook);
                result.add(promotion);
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

    public static PromotionPOJO getOne(String promotionId){
        PromotionPOJO result = null;
        Connection connection = Database.createConnection();
        try {
            String sql = "SELECT * FROM promotion WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, promotionId);

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                Double percent = rs.getDouble("percent");
                String applyOption = rs.getString("apply_option");
                Integer limitOrders = rs.getInt("limit_orders");
                Boolean enabled = rs.getBoolean("is_enabled");
                ArrayList<BookPOJO> listBook = new ArrayList<>();
                String query1 = "SELECT * " +
                        "FROM promotion_book as pb, book as b" +
                        " WHERE pb.id_promotion=? and pb.id_book = b.id";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1, id);
                ResultSet rs1 = statement1.executeQuery();
                while(rs1.next()){
                    String id_book = rs1.getString("id_book");
                    String name_book = rs1.getString("name");
                    String id_publisher = rs1.getString("id_publisher");
                    int price = rs1.getInt("price");
                    int stock = rs1.getInt("stock");
                    int totalPurchase = rs1.getInt("total_purchase");
                    java.sql.Date releaseDate = rs1.getDate("release_date");
                    Boolean enabled_book = rs1.getBoolean("is_enabled");
                    BookPOJO book = new BookPOJO(id_book, name_book, id_publisher, price, stock, totalPurchase, releaseDate, enabled_book);
                    listBook.add(book);
                }

                result = new PromotionPOJO(id, name, description,
                        startDate, endDate, percent, applyOption, limitOrders, enabled, listBook);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Boolean updateOne(String oldPromotionId, PromotionPOJO modifiedPromotion){
        boolean result = false;
        Connection connection = Database.createConnection();
        try {
            String sql = "UPDATE promotion SET id=?, name=?, description=?, start_date=?, end_date=?," +
                    " percent=?, apply_option=?, limit_orders=?, is_enabled=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            int i = 1;
            statement.setString(i++, modifiedPromotion.getId());
            statement.setString(i++, modifiedPromotion.getName());
            statement.setString(i++, modifiedPromotion.getDescription());
            statement.setDate(i++, new java.sql.Date(modifiedPromotion.getStartDate().getTime()));
            statement.setDate(i++, new java.sql.Date(modifiedPromotion.getEndDate().getTime()));
            statement.setDouble(i++, modifiedPromotion.getPercent());
            statement.setString(i++, modifiedPromotion.getApplyOption());
            statement.setInt(i++, modifiedPromotion.getLimitOrders());
            statement.setInt(i++, modifiedPromotion.isEnabled() ? 1 : 0);
            statement.setString(i, oldPromotionId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Boolean insertOne(PromotionPOJO promotion){
        boolean result = false;
        Connection connection = Database.createConnection();
        try {
            String sql = "INSERT INTO promotion (id, name, description, start_date, end_date," +
                    " percent, apply_option, limit_orders, is_enabled) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            int i = 1;
            statement.setString(i++, promotion.getId());
            statement.setString(i++, promotion.getName());
            statement.setString(i++, promotion.getDescription());
            statement.setDate(i++, new java.sql.Date(promotion.getStartDate().getTime()));
            statement.setDate(i++, new java.sql.Date(promotion.getEndDate().getTime()));
            statement.setDouble(i++, promotion.getPercent());
            statement.setString(i++, promotion.getApplyOption());
            statement.setInt(i++, promotion.getLimitOrders());
            statement.setInt(i, promotion.isEnabled() ? 1 : 0);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Boolean insertAppliedBooks(String promotionId, ArrayList<String> listBookId){
        boolean result = true;
        Connection connection = Database.createConnection();
        try {
            String sql = "INSERT INTO promotion_book (id_promotion, id_book) VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            for (String bookId : listBookId){
                int i = 1;
                statement.setString(i++, promotionId);
                statement.setString(i++, bookId);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated <= 0) {
                    result = false;
                    break;
                }
            }
            statement.close();
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Boolean deleteNotAppliedBooks(String promotionId, ArrayList<String> listBookId){
        boolean result = true;
        Connection connection = Database.createConnection();
        try {
            String sql = "DELETE FROM promotion_book where id_promotion=? and id_book=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            for (String bookId : listBookId){
                int i = 1;
                statement.setString(i++, promotionId);
                statement.setString(i++, bookId);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated <= 0) {
                    result = false;
                    break;
                }
            }
            statement.close();
        } catch (SQLException ex) {
            result = false;
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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


    public static Boolean enable(String promotionId){
        boolean result = false;
        Connection connection = Database.createConnection();
        try {
            String sql = "UPDATE promotion SET is_enabled=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "1");
            statement.setString(2, promotionId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Boolean disable(String promotionId){
        boolean result = false;
        Connection connection = Database.createConnection();
        try {
            String sql = "UPDATE promotion SET is_enabled=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "0");
            statement.setString(2, promotionId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static PromotionPOJO getPromotionByIdBook(String idBook) {
        PromotionPOJO promotion = null;
        Connection connection = Database.createConnection();
        try {
            String sql = "SELECT promotion.id, promotion.name, promotion.description, promotion.start_date, promotion.end_date, promotion.percent, promotion.apply_option, promotion.limit_orders, promotion.is_enabled FROM promotion, book, promotion_book WHERE promotion_book.id_book = ? AND promotion_book.id_promotion = promotion.id";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idBook);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                double percent = rs.getDouble("percent");
                String apply_option = rs.getString("apply_option");
                int limit_orders = rs.getInt("limit_orders");
                boolean enabled = rs.getBoolean("is_enabled");
                
                promotion = new PromotionPOJO(id, name, description, start_date, end_date, percent, apply_option, limit_orders, enabled);
            }
            
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(PromotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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
        return promotion;
    }
}

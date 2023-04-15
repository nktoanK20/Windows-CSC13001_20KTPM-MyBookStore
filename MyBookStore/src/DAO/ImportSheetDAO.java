package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.BookInImportSheetPOJO;
import POJO.ImportSheetPOJO;

public class ImportSheetDAO {
  public ArrayList<ImportSheetPOJO> getAllImportSheet(){
    ArrayList<ImportSheetPOJO> listImportSheet = new ArrayList<>();
    Connection conn = null;
    try {
      conn = Database.createConnection();
      String query = "SELECT * FROM import_sheet";
      PreparedStatement prst = conn.prepareStatement(query);
      ResultSet res = prst.executeQuery();
      while(res.next()){
        String id = res.getString("id");
        Date create_at = res.getDate("create_at");
        String id_employee = res.getString("id_employee");
        String name = res.getString("name");
        int total_cost = res.getInt("total_cost");

        ImportSheetPOJO importSheet = new ImportSheetPOJO(id, create_at, id_employee,name, total_cost);
        listImportSheet.add(importSheet);
      }

      prst.close();
      res.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally{
      if(conn!=null){
        try {
          conn.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return listImportSheet;
  }

  public ArrayList<BookInImportSheetPOJO> getBooksInImportSheet(String id_importSheet){
    ArrayList<BookInImportSheetPOJO> listBookInIPS = new ArrayList<>();
    Connection conn = null;
    try {
      conn = Database.createConnection();
      String query = "SELECT * FROM import_sheet_book WHERE import_sheet_book.id_importSheet = ?";
      PreparedStatement prst = conn.prepareStatement(query);
      prst.setString(1, id_importSheet);
      ResultSet res = prst.executeQuery();
      while(res.next()){
        String id_book = res.getString("id_book");
        String name = res.getString("name");
        String id_publisher = res.getString("id_publisher");
        int quantity = res.getInt("quantity");
        int import_price = res.getInt("import_price");

        BookInImportSheetPOJO book = new BookInImportSheetPOJO(id_importSheet, id_book, name,id_publisher, quantity, import_price);
        listBookInIPS.add(book);
      }

      res.close();
      prst.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally{
      if(conn!=null){
        try {
          conn.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return listBookInIPS;
  }
  
  public boolean addImportSheet(ImportSheetPOJO importSheet){
    boolean ans = true;
    Connection conn = null;
    try {
      conn = Database.createConnection();
      String queryAddIPS = "INSERT INTO import_sheet(id, create_at, id_employee, name, total_cost) VALUES(?,?,?,?,?)";
      PreparedStatement prst = conn.prepareStatement(queryAddIPS);
      prst.setString(1, importSheet.getId());
      prst.setDate(2, importSheet.getCreate_at());
      prst.setString(3, importSheet.getId_employee());
      prst.setString(4, importSheet.getName());
      prst.setInt(5, importSheet.getTotal_cost());
      int row = prst.executeUpdate();
      if(row<1){
        ans = false;
      }
      prst.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally{
      if(conn!=null){
        try {
          conn.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return ans;
  }

  public boolean addListImportBook(ArrayList<BookInImportSheetPOJO> listImportBook){
    boolean ans = true;
    Connection conn = null;
    try {
      conn = Database.createConnection();
      String query = "INSERT INTO import_sheet_book(id_importSheet, id_book, name, id_publisher, quantity, import_price) VALUES(?,?,?,?,?,?)";
      PreparedStatement prst = conn.prepareStatement(query);
      for (BookInImportSheetPOJO bookInImportSheetPOJO : listImportBook) {
        prst.setString(1, bookInImportSheetPOJO.getId_importSheet());
        prst.setString(2, bookInImportSheetPOJO.getId_book());
        prst.setString(3, bookInImportSheetPOJO.getName());
        prst.setString(4, bookInImportSheetPOJO.getId_publisher());
        prst.setInt(5, bookInImportSheetPOJO.getQuantity());
        prst.setInt(6, bookInImportSheetPOJO.getImport_price());
        int row = prst.executeUpdate();
        if(row<1){
          ans = false;
          break;
        }

      }

      prst.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally{
      if(conn!=null){
        try {
          conn.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return ans;
  }

  public boolean isExitInBookDB(String id_book){
    boolean ans = true;
    Connection conn = null;
    try {
      conn = Database.createConnection();
      String query = "SELECT * FROM book WHERE book.id = ?";
      PreparedStatement prst = conn.prepareStatement(query);
      prst.setString(1, id_book);
      ResultSet res = prst.executeQuery();
      if(!res.next()){
        ans = false;
      }
      prst.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally{
      if(conn!=null){
        try {
          conn.close();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return ans;
  }

  public boolean configBookTable(ArrayList<BookInImportSheetPOJO> listImportBook, Date create_at){
    boolean ans = true;
    Connection conn = null;
    conn = Database.createConnection();
    PreparedStatement prst;
    String query;
    for (BookInImportSheetPOJO bookInImportSheetPOJO : listImportBook) {
      if(isExitInBookDB(bookInImportSheetPOJO.getId_book())){
        // Neu ton tai trong database
        query = "update book set book.stock = book.stock + ? where book.id = ?";
        try {
          prst = conn.prepareStatement(query);
          prst.setInt(1, bookInImportSheetPOJO.getQuantity());
          prst.setString(2, bookInImportSheetPOJO.getId_book());
          int row = prst.executeUpdate();
          if(row<1){
            ans = false;
          }
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }else{
        query = "insert into book(id, name, id_publisher, price, stock, total_purchase, enabled, release_date) values(?,?,?,?,?,0,1,?)";
        try {
          prst = conn.prepareStatement(query);
          prst.setString(1, bookInImportSheetPOJO.getId_book());
          prst.setString(2, bookInImportSheetPOJO.getName());
          prst.setString(3, bookInImportSheetPOJO.getId_publisher());
          prst.setInt(4, bookInImportSheetPOJO.getImport_price());
          prst.setInt(5, bookInImportSheetPOJO.getQuantity());
          prst.setDate(6, create_at);
          int row = prst.executeUpdate();
          if(row<1){
            ans = false;
          }
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    if(conn!=null){
      try {
        conn.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return ans;
  }

}

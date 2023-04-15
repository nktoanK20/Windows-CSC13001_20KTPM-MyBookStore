package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import POJO.PublisherPOJO;



public class PublisherDAO {
  public static ArrayList<PublisherPOJO> getAll(){
        ArrayList<PublisherPOJO> result = null;
        Connection connection = Database.createConnection();
        try {
            result = new ArrayList<>();
            Statement statement;
            
              statement = connection.createStatement();
            
            String query = "SELECT * FROM publisher";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Boolean is_disabled = rs.getBoolean("is_disabled");
                PublisherPOJO publisher = new PublisherPOJO(id, name, address, phone, is_disabled);
                result.add(publisher);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
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

	public ArrayList<PublisherPOJO> getAllPublisher (){
		ArrayList<PublisherPOJO> result = null ;
		try {
			result = new ArrayList<PublisherPOJO>();
			Connection conn = Database.createConnection();
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM publisher";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
        Boolean disable = rs.getBoolean("is_disabled");
				
				PublisherPOJO publisher = new PublisherPOJO(id,name,address,phone,disable);
				result.add(publisher);
			}
			rs.close();
			statement.close();
      conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

  public ArrayList<PublisherPOJO> getPublisherNotDisable (){
		ArrayList<PublisherPOJO> result = null ;
		try {
			result = new ArrayList<PublisherPOJO>();
			Connection conn = Database.createConnection();
			Statement statement = conn.createStatement();
			String query = "select * from publisher where publisher.is_disabled = false";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
        Boolean disable = rs.getBoolean("is_disabled");
				
				PublisherPOJO publisher = new PublisherPOJO(id,name,address,phone,disable);
				result.add(publisher);
			}
			rs.close();
			statement.close();
      conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

  public boolean addPublisher(PublisherPOJO publisher){
    boolean ans = true;
    Connection conn = null;
    
    try {
      conn = Database.createConnection();
      String query = "INSERT INTO publisher(id, name, address, phone) VALUES (?,?,?,?)";
      PreparedStatement prst = conn.prepareStatement(query);
      prst.setString(1, publisher.getId());
      prst.setString(2, publisher.getName());
      prst.setString(3, publisher.getAddress());
      prst.setString(4, publisher.getPhone());

      int rows = prst.executeUpdate();
      if(rows <1){
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

  public boolean updatePublisher(PublisherPOJO publisher){
    boolean res = true;
    Connection conn = null;
    try {
      conn = Database.createConnection();
      String query = "UPDATE publisher SET publisher.name = ?, publisher.address = ?, publisher.phone = ? WHERE publisher.id = ?";
      PreparedStatement prst = conn.prepareStatement(query);
      prst.setString(1, publisher.getName());
      prst.setString(2, publisher.getAddress());
      prst.setString(3, publisher.getPhone());
      prst.setString(4, publisher.getId());
      int rows = prst.executeUpdate();
      if(rows < 1){
        res = false;
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
    return res;
  }

  public ArrayList<PublisherPOJO> getPublisherBySearch(String id, String name){
    Connection conn = null;
    ArrayList<PublisherPOJO> listPublisher = null;
    try {
      listPublisher = new ArrayList<PublisherPOJO>();
      conn = Database.createConnection();
      String query;
      if(!id.equals("") && name.equals("")){
        query = "SELECT * FROM publisher WHERE publisher.id = ?";
      }else if(id.equals("") && !name.equals("")){
        query = "SELECT * FROM publisher WHERE publisher.name = ?";
      }else if(!id.equals("") && !name.equals("")){
        query = "SELECT * FROM publisher WHERE publisher.id = ? AND publisher.name = ?";
      }else{
        return null;
      }
      PreparedStatement prst = conn.prepareStatement(query);
      if(!id.equals("") && name.equals("")){
        prst.setString(1, id);
      }else if(id.equals("") && !name.equals("")){
        prst.setString(1, name);
      }else if(!id.equals("") && !name.equals("")){
        prst.setString(1, id);
        prst.setString(2, name);
      }
      ResultSet res = prst.executeQuery();
      while(res.next()){
        String _id = res.getString("id");
        String _name = res.getString("name");
        String address = res.getString("address");
        String phone = res.getString("phone");
        Boolean disable = res.getBoolean("is_disabled");
        PublisherPOJO publisher = new PublisherPOJO(_id, _name, address, phone, disable);
        listPublisher.add(publisher);
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
    return listPublisher;
  }

  public boolean enablePublisher(PublisherPOJO publisher){
    boolean ans = true;
    Connection conn = null;

    try {
      conn = Database.createConnection();
      String query = "UPDATE publisher SET publisher.name = ?, publisher.address = ?, publisher.phone = ?, publisher.is_disabled = false WHERE publisher.id = ?";
      PreparedStatement prst = conn.prepareStatement(query);
      prst.setString(1, publisher.getName());
      prst.setString(2, publisher.getAddress());
      prst.setString(3, publisher.getPhone());
      prst.setString(4, publisher.getId());

      int rows = prst.executeUpdate();
      if(rows<1){
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

  public boolean disablePublisher(PublisherPOJO publisher){
    boolean ans = true;
    Connection conn = null;

    try {
      conn = Database.createConnection();
      String query = "UPDATE publisher SET publisher.name = ?, publisher.address = ?, publisher.phone = ?, publisher.is_disabled = true WHERE publisher.id = ?";
      PreparedStatement prst = conn.prepareStatement(query);
      prst.setString(1, publisher.getName());
      prst.setString(2, publisher.getAddress());
      prst.setString(3, publisher.getPhone());
      prst.setString(4, publisher.getId());

      int rows = prst.executeUpdate();
      if(rows<1){
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
}

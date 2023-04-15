package BUS;

import java.util.ArrayList;

import DAO.AuthorDAO;
import POJO.AuthorPOJO;

public class AuthorBUS {
	public ArrayList<AuthorPOJO> getAllAuthor(){
		ArrayList<AuthorPOJO> result = null;
		AuthorDAO authorDA = new AuthorDAO();
		result = authorDA.getAllAuthor();
		return result;
	}

  public boolean addAuthor(AuthorPOJO author){
    AuthorDAO authorDA = new AuthorDAO();
    boolean res = authorDA.addAuthor(author);
    return res;
  }
  public boolean updateAuthor(AuthorPOJO author){
    AuthorDAO authorDA = new AuthorDAO();
    boolean res = authorDA.updateAuthor(author);
    return res;
  }

  public ArrayList<AuthorPOJO> getAuthorBySearch(String id, String name){
    AuthorDAO authorDA = new AuthorDAO();
    ArrayList<AuthorPOJO> listAuthor = new ArrayList<>();
    listAuthor = authorDA.getAuthorBySearch(id,name);
    return listAuthor;
  }

  public boolean enableAuthor(AuthorPOJO author){
    AuthorDAO authorDA  = new AuthorDAO();
    boolean res = authorDA.enableAuthor(author);
    return res;
  }

  public boolean disableAuthor(AuthorPOJO author){
    AuthorDAO authorDA  = new AuthorDAO();
    boolean res = authorDA.disableAuthor(author);
    return res;
  }

  static public ArrayList<AuthorPOJO> getAuthorListOfBook(String bookId){
    return AuthorDAO.getAuthorListOfBook(bookId);
  }
}

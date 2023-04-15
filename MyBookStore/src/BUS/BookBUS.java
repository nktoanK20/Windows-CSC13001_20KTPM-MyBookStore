package BUS;

import DAO.BookDAO;
import POJO.BookPOJO;

import java.util.ArrayList;

public class BookBUS {
    public static ArrayList<BookPOJO> getAll(){
        return BookDAO.getAll();
    }

    public static BookPOJO getOne(String bookId){
        return BookDAO.getOne(bookId);
    }

    public static Boolean updateOne(String oldBookId, BookPOJO modifiedBook){
        return BookDAO.updateOne(oldBookId,modifiedBook);
    }

    public static Boolean insertOne(BookPOJO book){
        return BookDAO.insertOne(book);
    }

    public static Boolean enable(String bookId){
        return BookDAO.enable(bookId);
    }

    public static Boolean disable(String bookId){
        return BookDAO.disable(bookId);
    }

    public static ArrayList<BookPOJO> getBookNotDisable(){
        return BookDAO.getBookNotDisable();
    }
    public static ArrayList<BookPOJO> getNewBooks(){
        return BookDAO.getNewBooks();
    }

    public static ArrayList<BookPOJO> getHotBooks(){
        return BookDAO.getHotBooks();
    }

    public static ArrayList<BookPOJO> getOutOfStockBooks(){
        return BookDAO.getOutOfStockBooks();
    }

    public static boolean insertBookAuthors(String bookId, ArrayList<String> listAuthorId){
        return BookDAO.insertBookAuthors(bookId, listAuthorId);
    }

    public static boolean deleteBookAuthors(String bookId, ArrayList<String> listAuthorId){
        return BookDAO.deleteBookAuthors(bookId, listAuthorId);
    }

    public static boolean insertBookCategories(String bookId, ArrayList<String> listCategoryId){
        return BookDAO.insertBookCategories(bookId, listCategoryId);
    }

    public static boolean deleteBookCategories(String bookId, ArrayList<String> listCategoryId){
        return BookDAO.deleteBookCategories(bookId, listCategoryId);
    }
}

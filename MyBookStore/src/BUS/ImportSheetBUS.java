package BUS;

import java.sql.Date;
import java.util.ArrayList;

import DAO.ImportSheetDAO;
import POJO.BookInImportSheetPOJO;
import POJO.ImportSheetPOJO;

public class ImportSheetBUS {
  public ArrayList<ImportSheetPOJO> getAllImportSheet(){
    ImportSheetDAO importSheetDao = new ImportSheetDAO();
    ArrayList<ImportSheetPOJO> res = importSheetDao.getAllImportSheet();
    return res;
  }
  public ArrayList<BookInImportSheetPOJO> getBookInImportSheet(String id_importSheet){
    ImportSheetDAO importSheetDAO = new ImportSheetDAO();
    ArrayList<BookInImportSheetPOJO> res = importSheetDAO.getBooksInImportSheet(id_importSheet);
    return res;
  }

  public boolean addImportSheet(ImportSheetPOJO importSheet){
    ImportSheetDAO importSheetDao = new ImportSheetDAO();
    return importSheetDao.addImportSheet(importSheet);
  }

  public boolean addListImportBook(ArrayList<BookInImportSheetPOJO> listImportBook){
    ImportSheetDAO importSheetDao = new ImportSheetDAO();
    return importSheetDao.addListImportBook(listImportBook);
  }

  public boolean configTableBook(ArrayList<BookInImportSheetPOJO> listImportBook, Date create_at){
    ImportSheetDAO importSheetDao = new ImportSheetDAO();
    return importSheetDao.configBookTable(listImportBook, create_at);
  }
}

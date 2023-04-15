package POJO;

public class BookInImportSheetPOJO {
  private String id_importSheet;
  private String id_book;
  private String name;
  private String id_publisher;
  private int quantity;
  private int import_price;

  public BookInImportSheetPOJO() {
  }

  public BookInImportSheetPOJO(String id_importSheet, String id_book, String name, String id_publisher, int quantity,
      int import_price) {
    this.id_importSheet = id_importSheet;
    this.id_book = id_book;
    this.name = name;
    this.id_publisher = id_publisher;
    this.quantity = quantity;
    this.import_price = import_price;
  }

  public String getId_importSheet() {
    return id_importSheet;
  }

  public void setId_importSheet(String id_importSheet) {
    this.id_importSheet = id_importSheet;
  }

  public String getId_book() {
    return id_book;
  }

  public void setId_book(String id_book) {
    this.id_book = id_book;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId_publisher() {
    return id_publisher;
  }

  public void setId_publisher(String id_publisher) {
    this.id_publisher = id_publisher;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getImport_price() {
    return import_price;
  }

  public void setImport_price(int import_price) {
    this.import_price = import_price;
  }

  @Override
  public String toString() {
    return "BookInImportSheetPOJO [id_importSheet=" + id_importSheet + ", id_book=" + id_book + ", name=" + name
        + ", id_publisher=" + id_publisher + ", quantity=" + quantity + ", import_price=" + import_price + "]";
  }

  

  
  
  
}

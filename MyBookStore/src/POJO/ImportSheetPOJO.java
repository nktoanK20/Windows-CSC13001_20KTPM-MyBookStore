package POJO;

import java.sql.Date;

public class ImportSheetPOJO {
  private String id;
  private Date create_at;
  private String id_employee;
  private String name;
  private int total_cost;

  public ImportSheetPOJO() {
  }

  public ImportSheetPOJO(String id, Date create_at, String id_employee, String name, int total_cost) {
    this.id = id;
    this.create_at = create_at;
    this.id_employee = id_employee;
    this.name = name;
    this.total_cost = total_cost;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getCreate_at() {
    return create_at;
  }

  public void setCreate_at(Date create_at) {
    this.create_at = create_at;
  }

  public String getId_employee() {
    return id_employee;
  }

  public void setId_employee(String id_employee) {
    this.id_employee = id_employee;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTotal_cost() {
    return total_cost;
  }

  public void setTotal_cost(int total_cost) {
    this.total_cost = total_cost;
  }

  @Override
  public String toString() {
    return "ImportSheetPOJO [id=" + id + ", create_at=" + create_at + ", id_employee=" + id_employee + ", name=" + name
        + ", total_cost=" + total_cost + "]";
  }
  

  
  
}

package POJO;

public class UserPOJO {
  private boolean admin;
  private String id_account;
  String id;
  String name;
  String idAccount;
  String address;
  int role;

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public String getId_account() {
    return id_account;
  }

  public void setId_account(String id_account) {
    this.id_account = id_account;
  }

  // public UserPOJO(String id, String name, String id_account, String address,
  // boolean admin) {
  // this.admin = admin;
  // this.id_account = id_account;
  // this.id = id;
  // this.name = name;
  // this.address = address;
  // }

  public UserPOJO(String id, String name, String idAccount, String address, int role) {
    this.id = id;
    this.name = name;
    this.idAccount = idAccount;
    this.address = address;
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdAccount() {
    return idAccount;
  }

  public void setIdAccount(String idAccount) {
    this.idAccount = idAccount;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return id + " - " + name;
  }
}

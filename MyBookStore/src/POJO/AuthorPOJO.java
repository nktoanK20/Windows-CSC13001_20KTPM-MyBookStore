package POJO;
public class AuthorPOJO {
	private String id;
	private String name;
	private String address;
	private String phone;
  private boolean disable;
	
	
	
	public AuthorPOJO() {
		
	}




  public AuthorPOJO(String id, String name, String address, String phone) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

  public AuthorPOJO(String id, String name, String address, String phone, boolean disable) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
    this.disable = disable;
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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}
  
  public boolean isDisable() {
    return disable;
  }




  public void setDisable(boolean disable) {
    this.disable = disable;
  }


	@Override
	public String toString() {
		return "AuthorPojo [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + "]";
	}




	
	
	
}

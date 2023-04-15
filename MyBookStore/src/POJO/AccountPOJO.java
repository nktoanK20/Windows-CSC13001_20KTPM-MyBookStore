package POJO;

public class AccountPOJO {
    String id;
    String username;
    String password;
    boolean isActive;

    public AccountPOJO(String id, String username, String password, boolean isActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}

package POJO;

import java.util.Date;

public class BookPOJO {
    String id;
    String name;
    String idPublisher;
    Integer price;
    Integer stock;
    Integer totalPurchase;
    Date releaseDate;
    Boolean enabled;

    public BookPOJO(String id, String name, String idPublisher, Integer price, Integer stock, Integer totalPurchase, Date releaseDate, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.idPublisher = idPublisher;
        this.price = price;
        this.stock = stock;
        this.totalPurchase = totalPurchase;
        this.releaseDate = releaseDate;
        this.enabled = enabled;
    }

    public BookPOJO(String id, String name, String idPublisher, Integer price, Integer stock, Integer totalPurchase, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.idPublisher = idPublisher;
        this.price = price;
        this.stock = stock;
        this.totalPurchase = totalPurchase;
        this.enabled = enabled;
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

    public String getIdPublisher() {
        return idPublisher;
    }

    public void setIdPublisher(String idPublisher) {
        this.idPublisher = idPublisher;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Integer totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
      return id+" - " + name +" - "+ price;
    }

    public BookPOJO clone(){
        return new BookPOJO(id, name, idPublisher, price, stock, totalPurchase, releaseDate, enabled);
    }
}

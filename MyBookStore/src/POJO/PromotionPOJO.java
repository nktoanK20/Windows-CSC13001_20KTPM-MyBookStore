package POJO;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;

public class PromotionPOJO {
    String id;
    String name;
    String description;
    Date startDate;
    Date endDate;
    Double percent;
    String applyOption;
    Integer limitOrders;
    Boolean enabled;
    ArrayList<BookPOJO> listBook;

    public PromotionPOJO(String id, String name, String description, Date startDate,
                         Date endDate, Double percent, String applyOption,
                         Integer limitOrders, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.applyOption = applyOption;
        this.limitOrders = limitOrders;
        this.enabled = enabled;
    }

    public PromotionPOJO(String id, String name, String description, Date startDate,
                         Date endDate, Double percent, String applyOption,
                         Integer limitOrders, Boolean enabled, ArrayList<BookPOJO> listBook) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.applyOption = applyOption;
        this.limitOrders = limitOrders;
        this.enabled = enabled;
        this.listBook = listBook;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getApplyOption() {
        return applyOption;
    }

    public void setApplyOption(String applyOption) {
        this.applyOption = applyOption;
    }

    public Integer getLimitOrders() {
        return limitOrders;
    }

    public void setLimitOrders(Integer limitOrders) {
        this.limitOrders = limitOrders;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<BookPOJO> getListBook() {
        return listBook;
    }

    public void setListBook(ArrayList<BookPOJO> listBook) {
        this.listBook = listBook;
    }
}

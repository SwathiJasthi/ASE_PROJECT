package tutorial.cs5551.com.homeapp;

import java.io.Serializable;

/**
 * Created by Ashweeza on 3/14/2017.
 */

public class ProductDetails implements Serializable {
    String name;
    String description;
    String brandname;
    String[] images;
    String[] websitelink;
    String[] merchantnames;
    Double[] prices;
    String email;
    String duedate;
    String phone;
    String personname;
    boolean iscompleted;

    public ProductDetails() {

    }
    public ProductDetails(String name, String brand, String person, String duedate, boolean iscompleted) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.brandname = brand;
        this.personname = person;
        this.duedate = duedate;
        this.iscompleted = iscompleted;
    }

    public String[] getWebsitelink() {
        return websitelink;
    }

    public void setWebsitelink(String[] websitelink) {
        this.websitelink = websitelink;
    }

    public String[] getMerchantnames() {
        return merchantnames;
    }

    public void setMerchantnames(String[] merchantnames) {
        this.merchantnames = merchantnames;
    }

    public Double[] getPrices() {
        return prices;
    }

    public void setPrices(Double[] prices) {
        this.prices = prices;
    }

    public boolean iscompleted() {
        return iscompleted;
    }

    public void setIscompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
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

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String[] getPhotos() {
        return images;
    }

    public void setPhotos(String[] photos) {
        this.images = photos;
    }


}

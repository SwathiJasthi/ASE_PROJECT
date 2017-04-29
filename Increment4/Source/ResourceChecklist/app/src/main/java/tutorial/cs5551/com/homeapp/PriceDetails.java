package tutorial.cs5551.com.homeapp;

import java.io.Serializable;

/**
 * Created by Ashweeza on 4/26/2017.
 */

public class PriceDetails implements Serializable {
    String merchantnames;
    Double prices;
    String websitelink;

    public String getWebsitelink() {
        return websitelink;
    }

    public void setWebsitelink(String websitelink) {
        this.websitelink = websitelink;
    }

    public String getMerchantnames() {
        return merchantnames;
    }

    public void setMerchantnames(String merchantnames) {
        this.merchantnames = merchantnames;
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }
}

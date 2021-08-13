package Model;

/**
 * countries model for the countries object
 * */

import java.time.LocalDateTime;

public class Countries {

    private Integer countryID;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    /**
     * Countries constructor
     *
     * @param countryID country id
     * @param country country
     * @param createDate created date
     * @param createdBy create by user
     * @param lastUpdate last update date
     * @param lastUpdateBy last update user
     * */
    public Countries(Integer countryID, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    //Populate Single parameter country
    public Countries(String country) {
        this.country=country;
    }

    /**
     *Getter for the country id
     * @return country id
     * */
    public Integer getCountryID() {
        return countryID;
    }
    /**
     * setter for the country id
     * @param countryID the countries id
     * */
    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }
    /**
     * getter for the country
     * @return country
     * */
    public String getCountry() {
        return country;
    }
    /**
     * setter for the country
     * @param country the country
     * */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * getter for the created date
     * @return createDate
     * */
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /**
     *setter for the create date
     * @param createDate the date the country was created
     * */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /**
     * getter for the created by
     * @return created by user
     * */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     *setter for the created by user
     * @param createdBy the user that created the country
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * getter for the last update date
     * @return last update date
     * */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /**
     *setter for the last update date
     * @param lastUpdate the last time (date) the country was update
     * */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * getter for the last update by
     * @return last update by user
     * */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    /**
     *set last update user by
     * @param lastUpdateBy the last user to update the country
     * */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }



    @Override
    //Convert hashmap to String for Country objects in combobox (credit to Mr. Kinkead Webinar on Combo Boxes)
    public String toString() {
        return ((country));
    }

}

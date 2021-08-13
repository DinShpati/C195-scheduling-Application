package Model;

/**
 * Contact Class mostly used for filling associated attributes and allowing the UI to show a name vice a number through combo box selections
 */
public class Contact {
    private  int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * The default constructor for the Contact Model
     *
     * @param contactID the contacts ID
     * @param contactName the contacts Name
     * @param contactEmail the contacts Email
     * */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public Contact() {

    }

    /**
     * constructor that returns only that contact id
     *
     * @param contactID contact id
     * */
    public Contact(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter for the contact id
     * @return contact id
     * */
    public  int getContactID() {
        return contactID;
    }
    /**
     * setter for contact id
     * @param contactID the contact id
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /**
     * Getter for the contact name
     * @return contact name
     * */
    public String getContactName() {
        return contactName;
    }
    /**
     * Setter for the contact name
     * @param contactName the contacts name
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**
     * get the contact email
     * @return contact email
     * */
    public String getContactEmail() {
        return contactEmail;
    }
    /**
     * setter for the contact email
     * @param contactEmail the contact email
     * */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }



    @Override
    /**Convert hashmap to String of Country objects for the combobox (credit to Mr. Kinkead Webinar on Combo Boxes)*/
    public String toString() {

        return ("Contact Name: " +(contactName )+ " Contact ID: " + contactID);
    }
}
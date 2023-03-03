package model;
/**
 * @author Lynn Weidman
 */

/**
 * A class to set Outsourced parts.
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor for Outsourced.
     * @param id- Id of the part.
     * @param name- name of the part.
     * @param price- price of the part.
     * @param stock- inventory level of the part.
     * @param min- the minimum set to be allowed in inventory.
     * @param max- the maximum set to be allowed in inventory.
     * @param companyName- company name the part came from.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * companyName getter.
     * @return companyName.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * companyName setter.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}



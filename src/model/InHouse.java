package model;
/**
 * @author Lynn Weidman
 */

/**
 * a class to set InHouse parts.
 */
public class InHouse extends Part{
    private int machineId;


    /**
     * constructor for InHouse.
     * @param id- id of the part.
     * @param name- name of the part.
     * @param price- price of the part.
     * @param stock- inventory level of the part.
     * @param min- the minimum set to be allowed in inventory.
     * @param max- the maximum set to be allowed in inventory.
     * @param machineId- the machineId for the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * machineId getter.
     * @return machineId.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId setter
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

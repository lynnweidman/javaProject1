package model;

/**
 * @author Lynn Weidman
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * A class that contains an allParts list and an allProducts list.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static int nextPartId = 100;
    public static int nextProductId = 200;


    /**
     * Increments the nextPartId each time a new part is created.
     * @return nextPartId
     */
    public static int getNextPartId() {
        return ++nextPartId;
    }

    /**
     * Increments the nextProductId each time a new product is created.
     * @return nextProductId
     */
    public static int getNextProductId() {
        return ++nextProductId;
    }


    /**
     * adds a part to allParts.
     * @param part adds part to allParts.
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * @return allParts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * add a product to allProducts.
     * @param product the product to add.
     */

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * allProducts getter.
     * @return allProducts.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * updates allParts when it has been modified.
     * @param index- of the part to be updated.
     * @param selectedPart- the updated part.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product that was modified.
     * @param index of the selected product.
     * @param selectedProduct- the updated product.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }


    /**
     * Searches the list of allParts for a full or partial name. It is converted to lower case.
     * @param partialPartName the name to search.
     * @return partFound.
     */
    public static ObservableList<Part> lookUpPart(String partialPartName) {
        ObservableList<Part> partFound = FXCollections.observableArrayList();

        for (Part inventory : allParts) {

            if (inventory.getName().toLowerCase().contains(partialPartName.toLowerCase())) {
                partFound.add(inventory);
            }
        }
        return partFound;
    }


    /**
     * Searches allParts by Id.
     * @param id the id to search.
     * @return the part with the matching Id.
     */
    public static Part lookUpPartId(int id) {
        ObservableList<Part> inventoryParts = Inventory.getAllParts();
        for (int i = 0; i < inventoryParts.size(); i++) {
            Part part = inventoryParts.get(i);

            if (part.getId() == id) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches allProducts by full or partial name. It is converted to lower case.
     * @param partialPartName- the name to search.
     * @return productFound.
     */
    public static ObservableList<Product> lookUpProduct(String partialPartName) {
        ObservableList<Product> productFound = FXCollections.observableArrayList();

        for (Product inventory : allProducts) {
            if (inventory.getName().toLowerCase().contains(partialPartName.toLowerCase())) {
                productFound.add(inventory);

            }
        }
        return productFound;

    }

    /**
     * Searches product by Id.
     * @param id- the id to search.
     * @return the product with the matching id.
     */
    public static Product lookUpProductId(int id) {
        ObservableList<Product> inventoryProducts = Inventory.getAllProducts();
        for (int i = 0; i < inventoryProducts.size(); i++) {
            Product product = inventoryProducts.get(i);

            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    /**
     * Allows you to delete a part from allParts.
     * @param selectedPart- the part to delete.
     * @return boolean.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Allows you to delete a product from allProducts.
     * @param selectedProduct- the product to delete.
     * @return boolean.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
}














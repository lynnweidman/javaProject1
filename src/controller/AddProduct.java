package controller;

/**
 * @author Lynn Weidman
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class that allows UI to add new products. The Product Id is automatically generated and not editable.
 *
 * FUTURE ENHANCEMENT- Have the product Price/Cost equal to the sum of the associated parts. The price changes if the
 * associated parts change.
 */
public class AddProduct implements Initializable {

    public TextField partSearchFieldProductPg;
    public Button addProductSaveButton;
    public TextField addProductId;
    public TextField addProductName;
    public TextField addProductInv;
    public TextField addProductPrice;
    public TextField addProductMin;
    public TextField addProductMax;
    public Button addProductCancelButton;
    public TableView<Part> topTable;
    public TableColumn topTableID;
    public TableColumn topTableName;
    public TableColumn topTableInv;
    public TableColumn topTablePrice;
    public TableView<Part> bottomTable;
    public TableColumn bottomTableId;
    public TableColumn bottomTableName;
    public TableColumn bottomTableInv;
    public TableColumn bottomTablePrice;
    public Button addButton;
    public Button removeAssociatedPart;

    /**
     * A list containing associated parts for products.
     */
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();


    /**
     * Initializes the controller and allows the user to create new products. The Product Id is autogenerated and not editable.
     * It populates the top parts table from all parts in inventory, and allows you to add them to the bottom table to
     * become associated with the product.
     * @param url- The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle- The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductId.setText(String.valueOf(Inventory.getNextProductId()));

        topTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        topTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        topTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        topTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));


        bottomTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomTableInv.setCellValueFactory(new PropertyValueFactory<>("price"));
        bottomTablePrice.setCellValueFactory(new PropertyValueFactory<>("stock"));

        topTable.setItems(Inventory.getAllParts());


    }

    /**
     * Saves the UI and adds a new product to Inventory. Displays an ERROR if a field is entered incorrectly.
     * Returns to the Main Screen on save.
     * Takes user back to the Main Screen on save.
     * @param actionEvent- event by pressing the button.
     */
        public void onAddProductSaveButton (ActionEvent actionEvent) throws IOException {

            try {
                int id = Integer.parseInt(addProductId.getText());
                String name = addProductName.getText();
                double price = Double.parseDouble(addProductPrice.getText());
                int stock = Integer.parseInt(addProductInv.getText());
                int min = Integer.parseInt(addProductMin.getText());
                int max = Integer.parseInt(addProductMax.getText());

                if (addProductName == null || addProductPrice == null || addProductInv == null || addProductMin == null || addProductMax == null) {
                    Alert emptyField = new Alert(Alert.AlertType.ERROR);
                    emptyField.setTitle("ERROR");
                    emptyField.setContentText("All fields must be completed to add a product");
                    emptyField.showAndWait();
                }

                if (name.isEmpty()) {
                    Alert nameField = new Alert(Alert.AlertType.ERROR);
                    nameField.setTitle("ERROR");
                    nameField.setContentText("Name cannot be empty");
                    nameField.showAndWait();
                    return;
                }

                if ((max < min) && (stock < min) || (stock > max)){
                    Alert minimum = new Alert(Alert.AlertType.ERROR);
                    minimum.setTitle("ERROR");
                    minimum.setContentText("Maximum cannot be less than minimum");
                    minimum.showAndWait();
                    return;
                }

                if (stock < min || stock > max) {
                    Alert inventory = new Alert(Alert.AlertType.ERROR);
                    inventory.setTitle("ERROR");
                    inventory.setContentText("Inventory level cannot be below the minimum or above the maximum allowed.");
                    inventory.showAndWait();
                    return;
                }

                else {
                    Product newProduct = (new Product(id, name, price, stock, min, max));

                    for (Part part : associatedPart) {
                        newProduct.addAssociatedPart(part);
                    }

                    Inventory.addProduct(newProduct);

                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 900, 600);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                }

            } catch (Exception e) {
                Alert invalidEntry = new Alert(Alert.AlertType.ERROR);
                invalidEntry.setTitle("ERROR");
                invalidEntry.setContentText("There is an invalid entry");
                invalidEntry.showAndWait();
            }
        }


    /**
     * Search for Parts by Id or name, including a partial name. Text case is irrelevant as it converted to lowercase.
     * Sets the top table with the part found (foundPart).
     * Provides an INFORMATION alert if the Part was not found.
     * @param actionEvent- an event by pressing enter.
     */
        public void onPartSearchFieldProductPg (ActionEvent actionEvent) {

            String search = partSearchFieldProductPg.getText();
            ObservableList<Part> foundPart = Inventory.lookUpPart(search);
            topTable.setItems(foundPart);

            try {
                if (foundPart.size() == 0) {

                    int searchId = Integer.parseInt(search);

                    Part foundPartId = Inventory.lookUpPartId(searchId);
                    if (foundPartId != null)
                        foundPart.add(foundPartId);
                }
            }
            catch (Exception e) {
                Alert partNotFound = new Alert(Alert.AlertType.INFORMATION);
                partNotFound.setTitle("INFORMATION");
                partNotFound.setContentText("No matches found");
                partNotFound.showAndWait();
            }
        }


    /**
     * Returns to the Main Screen.
     * @param actionEvent- event by pressing the button.
     */
    public void onAddProductCancelButton (ActionEvent actionEvent) throws IOException{

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Allows user to select a part from the top table and add it to the bottom table.
     * An ERROR displays if a part has not been selected and the user presses the Add button.
     * @param actionEvent- event by pressing the button.
     */
    public void onAddButton(ActionEvent actionEvent) {
        Part selectedPart = topTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert noSelectedPart = new Alert(Alert.AlertType.ERROR);
            noSelectedPart.setTitle("ERROR");
            noSelectedPart.setContentText("You have not selected a part to add.");
            noSelectedPart.showAndWait();
        }
         else {
            associatedPart.add(selectedPart);
            bottomTable.setItems(associatedPart);
        }

    }

    /**
     * Allows a user to remove a part from the bottom table.
     * * An ERROR displays if a part has not been selected and the user presses the Remove Associated Part button.
     * @param actionEvent- event by pressing the button.
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part partToRemove = bottomTable.getSelectionModel().getSelectedItem();

        if (partToRemove == null) {
            Alert noRemovablePart = new Alert(Alert.AlertType.ERROR);
            noRemovablePart.setTitle("ERROR");
            noRemovablePart.setContentText("You have not selected a part to add.");
            noRemovablePart.showAndWait();
        }

        associatedPart.remove(partToRemove);
        bottomTable.setItems(associatedPart);

    }
}





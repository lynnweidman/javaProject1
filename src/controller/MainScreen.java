package controller;

/**
 * @author Lynn Weidman
 */

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A class displaying the Parts Table and Products Table. Providing a UI with the ability to navigate to Add Parts, Modify Parts, Add Products,
 * and Modify Products. You can also delete a part or product from this page.
 */

public class MainScreen implements Initializable {

    public Button partsAddButton;
    public Button partsModifyButton;
    public Button addProdForm;
    public Button prodModifyButton;
    public Button exitMainButton;
    public Button partsDeleteButton;
    public Button prodDeleteButton;
    public TextField partSearchField;
    public TextField productSearchField;

    public TableView<Part> partsTable;
    public TableColumn partsTableIDCol;
    public TableColumn partsTableNameCol;
    public TableColumn partsTablePriceCol;
    public TableColumn partsTableStockCol;
    public TableColumn partsTableMinCol;
    public TableColumn partsTableMaxCol;
    public TableColumn partsTableMachineIdCol;

    public TableView<Product> productsTable;
    public TableColumn prodTableIDCol;
    public TableColumn prodTableNameCol;
    public TableColumn prodTablePriceCol;
    public TableColumn prodTableStockCol;
    public TableColumn prodTableMinCol;
    public TableColumn prodTableMaxCol;
    public TableColumn prodTableMachineIdCol;
    public TableColumn prodTableInventoryCol;


/**
 * Initializes the controller and populates the Parts and Products tables.
 * @param url- The location used to resolve relative paths for the root object, or null if the location is not known.
 * @param resourceBundle- The resources used to localize the root object, or null if the root object was not localized.
 */
    @Override
        public void initialize (URL url, ResourceBundle resourceBundle) {


            partsTableIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partsTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            partsTableStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


            prodTableIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            prodTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            prodTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            prodTableStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

            partsTable.setItems(Inventory.getAllParts());
            productsTable.setItems(Inventory.getAllProducts());


    }

    /**
     * Allows user to search for Parts by Id or name, including a partial name.
     * Provides an alert if the Part was not found.
     * @param actionEvent- an event by pressing enter.
     */
    public void onPartSearchField(ActionEvent actionEvent) {

        String search = partSearchField.getText();
        ObservableList<Part> foundPart = Inventory.lookUpPart(search);

        try {
            if (foundPart.size() == 0) {

                int searchId = Integer.parseInt(search);
                Part foundPartId = Inventory.lookUpPartId(searchId);
                if (foundPartId != null)
                    foundPart.add(foundPartId);
            }

            partsTable.setItems(foundPart);
        }

        catch (Exception e) {
            Alert partNotFound = new Alert(Alert.AlertType.INFORMATION);
            partNotFound.setTitle("INFORMATION");
            partNotFound.setContentText("No matches found");
            partNotFound.showAndWait();
        }
    }


    /**
     * Allows user to search for Products by Id or name, including a partial name.
     * Provides an alert if the Product was not found.
     * @param actionEvent- an event by pressing enter.
     */
    public void onProductSearchField(ActionEvent actionEvent) {

        String search = productSearchField.getText();
        ObservableList<Product> foundProduct = Inventory.lookUpProduct(search);


        try {
            if (foundProduct.size() == 0) {

                int searchId = Integer.parseInt(search);
                Product foundProductId = Inventory.lookUpProductId(searchId);
                if (foundProductId != null)
                    foundProduct.add(foundProductId);
            }

            productsTable.setItems(foundProduct);
        }

         catch (Exception e){
                Alert productNotFound = new Alert(Alert.AlertType.INFORMATION);
                productNotFound.setTitle("INFORMATION");
                productNotFound.setContentText("No matches found");
                productNotFound.showAndWait();

        }
    }

        /**
         * Loads the Add Part screen.
         * @param actionEvent- an event by pressing the button.
         */
        public void onAddPart (ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setTitle("Add Part Form");
            stage.setScene(scene);
            stage.show();
        }

        /**
         * Loads the Add Product screen.
         * @param actionEvent- an event by pressing the button.
         */
        public void onAddProdForm (ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setTitle("Add Product Form");
            stage.setScene(scene);
            stage.show();
        }

        /**
         * Loads the Modify Part screen. Provides an alert if a part is not selected.
         * @param actionEvent- an event by pressing the button.
         */
        public void onModifyPart (ActionEvent actionEvent) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            Parent root = loader.load();

            Part part = partsTable.getSelectionModel().getSelectedItem();

            if (part == null) {
                Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
                noPartSelected.setTitle("ERROR");
                noPartSelected.setContentText("Please select a part");
                noPartSelected.showAndWait();
                return;
            }
            ModifyPart mp = loader.getController();
            mp.setPart(partsTable.getSelectionModel().getSelectedIndex(),partsTable.getSelectionModel().getSelectedItem());


            Stage stage = new Stage();
            stage.setTitle("Modify Part Form");
            Scene scene = new Scene(root, 900, 600);
            stage.show();
            stage.setScene(scene);

        }

    /**
     * Loads the Modify Product screen. Provides an alert if a product is not selected.
     * @param actionEvent- an event by pressing the button.
     */
        public void onModifyProduct (ActionEvent actionEvent) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent root = loader.load();

            Product product = productsTable.getSelectionModel().getSelectedItem();

            if (product == null) {
                Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
                noPartSelected.setTitle("ERROR");
                noPartSelected.setContentText("Please select a product");
                noPartSelected.showAndWait();
                return;
            }
            ModifyProduct modProduct = loader.getController();
            modProduct.setProduct(productsTable.getSelectionModel().getSelectedIndex(),productsTable.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            Scene scene = new Scene(root, 900, 600);
            stage.setTitle("Modify Product Form");
            stage.setScene(scene);
            stage.show();

        }

        /**
         * Allows user to delete a part.
         * Provides a CONFIRMATION Alert- Are you sure you want to delete this part?
         * Provides a CONFIRMATION Alert- if part was successfully removed.
         * Provides a CONFIRMATION Alert- if part was not removed.
         * @param actionEvent- event by pressing the button.
         */
        public void onPartsDeleteButton (ActionEvent actionEvent) {

            Part partToRemove = partsTable.getSelectionModel().getSelectedItem();

            if (partToRemove == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("You have not selected a part to delete");
                alert.showAndWait();
            }

            if (partToRemove != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION");
                alert.setContentText("Are you sure you want to delete this part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deletePart(partToRemove);

                    Alert partRemoved = new Alert(Alert.AlertType.CONFIRMATION);
                    partRemoved.setTitle("CONFIRMATION");
                    partRemoved.setContentText("Part was successfully removed.");
                    partRemoved.showAndWait();
                }
            }


                else {
                    Alert notRemoved = new Alert(Alert.AlertType.CONFIRMATION);
                    notRemoved.setTitle("CONFIRMATION");
                    notRemoved.setContentText("Part was not removed");
                    notRemoved.showAndWait();

                }

            }




    /**
     * Allows user to delete a product.
     * Provides a CONFIRMATION Alert- Are you sure you want to delete this product?
     * Provides a CONFIRMATION Alert- if product was successfully removed.
     * Provides a CONFIRMATION Alert- if product was not removed.
     * @param actionEvent- event by pressing the button.
     */
        public void onProdDeleteButton (ActionEvent actionEvent){

            Product productToRemove = productsTable.getSelectionModel().getSelectedItem();

            if (productToRemove == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("You have not selected a product to delete");
                alert.showAndWait();
            }

            else if (productToRemove.getAllAssociatedParts().size() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("You cannot remove a product that has parts");
                alert.showAndWait();

                    Alert notRemoved = new Alert(Alert.AlertType.CONFIRMATION);
                    notRemoved.setTitle("CONFIRMATION");
                    notRemoved.setContentText("Product was not removed");
                    notRemoved.showAndWait();
                }


             else if (productToRemove != null) {
                 if (productToRemove.getAllAssociatedParts().size() < 1) {
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                     alert.setTitle("CONFIRMATION");
                     alert.setContentText("Are you sure you want to delete this product?");
                     Optional<ButtonType> result = alert.showAndWait();

                     if (result.isPresent() && result.get() == ButtonType.OK) {
                         Inventory.deleteProduct(productToRemove);

                         Alert productRemoved = new Alert(Alert.AlertType.CONFIRMATION);
                         productRemoved.setTitle("CONFIRMATION");
                         productRemoved.setContentText("Product was successfully removed.");
                         productRemoved.showAndWait();
                     }
                 }

            }

        }

        /**
         * Exits the program.
         * @param actionEvent- event by pressing the button. */
        public void onExitMain (ActionEvent actionEvent){
            Alert exiting = new Alert(Alert.AlertType.CONFIRMATION);
            exiting.setTitle("CONFIRMATION");
            exiting.setContentText("Are you sure you want to exit? The program will close.");
            exiting.showAndWait();

            System.exit(0);
        }

}














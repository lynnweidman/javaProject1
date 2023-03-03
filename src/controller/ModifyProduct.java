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
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class that allows the user to modify products.
 */
public class ModifyProduct implements Initializable {
    public TableView<Part> topTable;
    public TableColumn topTableId;
    public TableColumn topTableName;
    public TableColumn topTableInv;
    public TableColumn topTablePrice;
    public TableView<Part> bottomTable;
    public TableColumn bottomTableId;
    public TableColumn bottomTableName;
    public TableColumn bottomTableInv;
    public TableColumn bottomTablePrice;
    public TextField modifyProductId;
    public TextField modifyProductSearch;
    public TextField modifyProductName;
    public TextField modifyProductInv;
    public TextField modifyProductPrice;
    public TextField modifyProductMax;
    public TextField modifyProductMin;
    public Button modifyProductSave;
    public Button modifyProductCancel;
    public Button removeAssociatedPart;
    public Button modifyProductAddPart;

    private int productIndex = 0;
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();


    /**
     * Takes the user back to the Main Screen.
     *
     * @param actionEvent- event by pressing the button.
     */
    public void onModifyProductCancelButton(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Populates the top table with all of the parts in Inventory, and the
     * bottom table with parts associated with the product selected from the Main Screen.
     *
     * @param url- The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle- The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        topTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        topTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        topTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        bottomTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bottomTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        topTable.setItems(Inventory.getAllParts());
        bottomTable.setItems(associatedPart);

    }

    /**
     * Populates the product text fields with selected Product information from the Main Screen.
     *
     * @param index- index of the product
     * @param selectedProduct- the selected product from Main Screen.
     */
    public void setProduct(int index, Product selectedProduct) {

        productIndex = index;

        modifyProductId.setText(String.valueOf(selectedProduct.getId()));
        modifyProductName.setText(selectedProduct.getName());
        modifyProductInv.setText(Integer.toString(selectedProduct.getStock()));
        modifyProductPrice.setText(Double.toString(selectedProduct.getPrice()));
        modifyProductMax.setText(Integer.toString(selectedProduct.getMax()));
        modifyProductMin.setText(Integer.toString(selectedProduct.getMin()));

        associatedPart.addAll(selectedProduct.getAllAssociatedParts());

    }


    /**
     * Search for Parts by Id or name, including a partial name.
     * Repopulates the top table with just the part found.
     * Provides an INFORMATION alert if the Part was not found.
     *
     * @param actionEvent- an event by pressing enter.
     */
    public void onModifyProductSearch(ActionEvent actionEvent) {

        String search = modifyProductSearch.getText();
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
     * User can select a part from the top table and add it to the bottom table.
     * There's an ERROR alert if a part was not selected and they pressed the Add button.
     *
     * @param actionEvent- event by pressing the button.
     */
    public void onModifyProductAddPartButton(ActionEvent actionEvent) {
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
     * * Saves and replaces the original product with the modified product.
     * There are ERROR alerts if a field is entered incorrectly..
     *
     * @param actionEvent- event by pressing the button.
     *
     * LOGICAL ERROR- For products that had parts associated with it, if I modified the product more than once,
     *                     the associated parts would double everytime I tried to modify the product. I needed to CLEAR the list,
     *                     then add the associated parts for it to work properly.
     */
    public void onModifyProductSaveButton(ActionEvent actionEvent) throws IOException {
        Part bottomSelectedPart = bottomTable.getSelectionModel().getSelectedItem();
        try {
            int id = Integer.parseInt(modifyProductId.getText());
            String name = modifyProductName.getText();
            double price = Double.parseDouble(modifyProductPrice.getText());
            int stock = Integer.parseInt(modifyProductInv.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            int max = Integer.parseInt(modifyProductMax.getText());

            if (max < min) {
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

            Product newModifyProduct = new Product(id, name, price, stock, min, max);

            newModifyProduct.getAllAssociatedParts().clear();

            for (Part part : associatedPart) {
                newModifyProduct.addAssociatedPart(part);

                Inventory.updateProduct(productIndex, newModifyProduct);
            }

            associatedPart.remove(bottomSelectedPart);
            bottomTable.setItems(associatedPart);
            Inventory.updateProduct(productIndex, newModifyProduct);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert invalidEntry = new Alert(Alert.AlertType.ERROR);
            invalidEntry.setTitle("ERROR");
            invalidEntry.setContentText("There is an invalid entry");
            invalidEntry.showAndWait();

        }
    }

    /**
     * Removes the associated part from the bottom table (associatedPart).
     * There's an ERROR alert if a part was not selected and they pressed the Remove Associated Part button.
     *
     * @param actionEvent-event by pressing the button.
     */
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {

        Part bottomSelectedPart = bottomTable.getSelectionModel().getSelectedItem();

        if (bottomSelectedPart == null) {
            Alert noPartToRemove = new Alert(Alert.AlertType.ERROR);
            noPartToRemove.setTitle("ERROR");
            noPartToRemove.setContentText("There are no associated parts to remove.");
            noPartToRemove.showAndWait();
        }

            associatedPart.remove(bottomSelectedPart);
            bottomTable.setItems(associatedPart);

    }

}












package controller;

/**
 * @author Lynn Weidman
 */

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class that allow the user to modify parts.
 */
public class ModifyPart {

    public TextField modifyPartId;
    public TextField modifyPartName;
    public TextField modifyPartInv;
    public TextField modifyPartPriceCost;
    public TextField modifyPartMax;
    public TextField modifyPartMin;
    public TextField modifyPartMachineId;
    public RadioButton modifyPartInHouseRadio;
    public RadioButton modifyPartOutsourcedRadio;
    public Label machIdCompanyName;
    public Button modifyPartSaveButton;

    public Part selectedPart;
    public ToggleGroup modifyRadioToggle;

    public int partIndex = 0;


    /**
     * Takes the user back to the Main Screen.
     *
     * @param actionEvent- event by pressing the button.
     */
    public void modifyPartCancelButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    /**
     * Sets the text to "MachineId".
     *
     * @param actionEvent- event by pressing the button.
     */
    public void onModifyPartInHouseRadio(ActionEvent actionEvent) {
        machIdCompanyName.setText("MachineId");
    }

    /**
     * *sets the text to "Company Name"
     *
     * @param actionEvent- event by pressing the button.
     */
    public void onModifyPartOutsourcedRadio(ActionEvent actionEvent) {
        machIdCompanyName.setText("Company Name");
    }


    /**
     * Populates the part fields with the selected part from the Main Screen.
     * If the part belongs to InHouse the field is labeled MachineId.
     * If the part belongs to Outsourced the field is labeled Company Name.
     *
     * @param index-        index of the part
     * @param selectedPart- the selected part from Main Screen.
     */


    public void setPart(int index, Part selectedPart) {
        partIndex = index;

        if (selectedPart instanceof InHouse) {

            InHouse iHNewPart = (InHouse) selectedPart;
            modifyPartInHouseRadio.setSelected(true);
            machIdCompanyName.setText("Machine ID");
            modifyPartId.setText(String.valueOf(iHNewPart.getId()));
            modifyPartName.setText(iHNewPart.getName());
            modifyPartInv.setText((Integer.toString(iHNewPart.getStock())));
            modifyPartPriceCost.setText((Double.toString(iHNewPart.getPrice())));
            modifyPartMin.setText((Integer.toString(iHNewPart.getMin())));
            modifyPartMax.setText((Integer.toString(iHNewPart.getMax())));
            modifyPartMachineId.setText((Integer.toString(iHNewPart.getMachineId())));

        }

        if (selectedPart instanceof Outsourced) {

            Outsourced osNewPart = (Outsourced) selectedPart;
            modifyPartOutsourcedRadio.setSelected(true);
            machIdCompanyName.setText("Company Name");
            modifyPartId.setText(String.valueOf(osNewPart.getId()));
            modifyPartName.setText(osNewPart.getName());
            modifyPartInv.setText((Integer.toString(osNewPart.getStock())));
            modifyPartPriceCost.setText((Double.toString(osNewPart.getPrice())));
            modifyPartMin.setText((Integer.toString(osNewPart.getMin())));
            modifyPartMax.setText((Integer.toString(osNewPart.getMax())));
            modifyPartMachineId.setText(osNewPart.getCompanyName());

        }
    }

    /**
     * Saves and replaces original part with the modified part.
     * There are ERROR alerts if a field is entered incorrectly..
     *
     * @param actionEvent- event by pressing the button.
     */
    public void onModifyPartSaveButton(ActionEvent actionEvent) throws IOException {

        try {
            int id = Integer.parseInt(modifyPartId.getText());
            String name = modifyPartName.getText();
            double price = Double.parseDouble(modifyPartPriceCost.getText());
            int stock = Integer.parseInt(modifyPartInv.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());

            if (name.isEmpty()) {
                Alert nameField = new Alert(Alert.AlertType.ERROR);
                nameField.setTitle("ERROR");
                nameField.setContentText("Name cannot be empty");
                nameField.showAndWait();
                return;
            }

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


            if (modifyPartInHouseRadio.isSelected()) {

                int machineId = Integer.parseInt(modifyPartMachineId.getText());
                InHouse inHousePartToUpdate = (new InHouse(id, name, price, stock, min, max, machineId));
                Inventory.updatePart(partIndex, inHousePartToUpdate);


            }

            if (modifyPartOutsourcedRadio.isSelected()) {
                String companyName = modifyPartMachineId.getText();
                Outsourced outsourcedPartToUpdate = (new Outsourced(id, name, price, stock, min, max, companyName));
                Inventory.updatePart(partIndex, outsourcedPartToUpdate);

            }
            Inventory.deletePart(selectedPart);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert invalidEntry = new Alert(Alert.AlertType.ERROR);
            invalidEntry.setTitle("ERROR");
            invalidEntry.setContentText("There is an invalid entry");
            invalidEntry.showAndWait();

        }
    }
}







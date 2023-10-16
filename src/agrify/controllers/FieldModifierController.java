package agrify.controllers;

import agrify.entities.Field;
import agrify.services.ServiceField;
import agrify.utils.DataSource;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FieldModifierController {

    @FXML
    private TextField EditFieldNomTextField;

    @FXML
    private TextField EditFieldQuantitéTextField;

    @FXML
    private TextField EditFieldSuperficieTextField;

    @FXML
    private TextField EditFieldTypeTextField;

    @FXML
    private Label EditUserMessage1;

    @FXML
    private Label EditUserMessage111;

    @FXML
    private Button ModifyFieldBackBtn;

    @FXML
    private Button ModifyFieldBtn;

    @FXML
    private Button ModifySearchFieldBtn;

    @FXML
    private TextField SearchModifyFieldTextFieldBtn;

    
            private Field currentField; 
            
    @FXML
    void ModifyField(ActionEvent event) {
    if (currentField != null) {
    try {
        // Retrieve the values from the input fields
        String nom = EditFieldNomTextField.getText();
        String type = EditFieldTypeTextField.getText();
        double superficie = Double.parseDouble(EditFieldSuperficieTextField.getText());
        int quantity = Integer.parseInt(EditFieldQuantitéTextField.getText());

        // Update the currently selected field
        currentField.setField_Nom(nom);
        currentField.setField_type(type);
        currentField.setField_Superficie(superficie);
        currentField.setField_quantity(quantity);

        // Create a ServiceField instance
        ServiceField serviceField = new ServiceField(DataSource.getInstance().getConnection());

        // Call the modification method
        serviceField.modifier(currentField);

        EditUserMessage111.setText("Champ mis à jour avec succès.");
    } catch (NumberFormatException e) {
        EditUserMessage111.setText("Veuillez saisir un nombre valide pour la superficie ou la quantité.");
    }
} else {
    EditUserMessage111.setText("Aucun champ à mettre à jour. Veuillez effectuer une recherche d'abord.");
}

    }

    @FXML
    void ModifyFieldBack(ActionEvent event)  throws IOException{
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/agrify/views/FiledHome.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) ModifyFieldBackBtn.getScene().getWindow();
   splashSignInStage.close();
    }

    
    
    
    
    
    
    @FXML
    void ModifySearchField(ActionEvent event) {

        String searchQuery = SearchModifyFieldTextFieldBtn.getText();

    if (searchQuery.isEmpty()) {
        EditUserMessage1.setText("Veuillez entrer un ID valide.");
    } else {
        // Create a ServiceField instance
        ServiceField serviceField = new ServiceField(DataSource.getInstance().getConnection());

        Field field = serviceField.getOne(Integer.parseInt(searchQuery));

        if (field != null) {
            currentField = field;
            EditFieldNomTextField.setText(field.getField_Nom());
            EditFieldTypeTextField.setText(field.getField_type());
            EditFieldSuperficieTextField.setText(String.valueOf(field.getField_Superficie()));
            EditFieldQuantitéTextField.setText(String.valueOf(field.getField_quantity()));

            EditUserMessage1.setText("Champ trouvé.");
        } else {
            EditUserMessage1.setText("Champ non trouvé.");
        }
    }
}


}

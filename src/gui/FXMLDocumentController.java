/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import unifiedshoppingexperience.UnifiedShoppingExperience;

/**
 *
 * @author Lucas
 */
public class FXMLDocumentController implements Initializable
{

    @FXML
    private TextField findProductSearchField;
    @FXML
    private Button findProductButton;
    @FXML
    private CheckBox musKeyboardsCheckBox;
    @FXML
    private CheckBox grafikkortCheckBox;
    @FXML
    private CheckBox skærmeCheckBox;
    @FXML
    private CheckBox kabinetterCheckBox;
    @FXML
    private CheckBox harddiskeCheckBox;
    @FXML
    private ImageView productImage1;
    @FXML
    private ImageView productImage2;
    @FXML
    private ImageView productImage3;
    @FXML
    private TextArea productDesctiptionArea1;
    @FXML
    private TextArea productDesctiptionArea2;
    @FXML
    private TextArea productDesctiptionArea3;

    private CheckBox[] allCheckBoxes;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        allCheckBoxes = new CheckBox[]
        {
            musKeyboardsCheckBox, grafikkortCheckBox, skærmeCheckBox, kabinetterCheckBox, harddiskeCheckBox
        };
    }

    @FXML
    private void findProductEnter(ActionEvent event)
    {
    }

    @FXML
    private void findProduct(ActionEvent event)
    {
        String[] descriptionTags = findProductSearchField.getText().split(" ");
        ArrayList<String> temp = new ArrayList();

        for (CheckBox cb : allCheckBoxes)
        {
            if (cb.isSelected())
            {
                temp.add(cb.getText());
            }
        }

        String[] typeTags = (String[]) temp.toArray();

        UnifiedShoppingExperience.getInstance().findProduct(descriptionTags, typeTags);
    }

}

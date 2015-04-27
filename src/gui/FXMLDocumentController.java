/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import unifiedshoppingexperience.Product;
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
    private CheckBox musKeyboardsCheckBox, grafikkortCheckBox, skærmeCheckBox, kabinetterCheckBox, harddiskeCheckBox;

    private CheckBox[] allCheckBoxes;
    @FXML
    private VBox productView;
    @FXML
    private VBox productDescriptionView;

    private final int PICTURE_HEIGHT = 164;

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
        findProduct(event);
    }

    @FXML
    private void findProduct(ActionEvent event)
    {

        productDescriptionView.getChildren().clear();
        productView.getChildren().clear();

        String[] descriptionTags = findProductSearchField.getText().split(" ");
        ArrayList<String> temp = new ArrayList();

        for (CheckBox cb : allCheckBoxes)
        {
            if (cb.isSelected())
            {
                temp.add(cb.getText());
            }
        }

        // just java being java.
        String[] typeTags = temp.toArray(new String[temp.size()]);

        List<Product> products = UnifiedShoppingExperience.getInstance().findProducts(descriptionTags, typeTags);
        for (Product p : products)
        {
            Image i = new Image("/pictures/" + p.getType() + ".jpg"); // TODO: properly pass an actual image to this object...
            ImageView productImage = new ImageView(i);
            productView.getChildren().add(productImage);

            TextArea productDescription = new TextArea();
            productDescription.setPrefHeight(PICTURE_HEIGHT);
            productDescription.setEditable(false);
            productDescription.setText(p.toString());
            
            productDescriptionView.getChildren().add(productDescription);
        }

    }

}

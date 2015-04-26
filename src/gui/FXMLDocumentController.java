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

        int y = 0;

        for (Product p : products)
        {
            Image i = new Image(""); // TODO: properly pass an actual image to this object...
            ImageView productImage = new ImageView(i);
            productImage.setX(280);
            productImage.setY(240 + y);

            TextArea productDescription = new TextArea();
            productDescription.setLayoutX(500);
            productDescription.setLayoutY(240 + y);
            productDescription.setText(p.toString());

            y += 160;
        }
    }

}

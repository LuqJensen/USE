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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import unifiedshoppingexperience.Product;
import unifiedshoppingexperience.UnifiedShoppingExperience;

/**
 *
 * @author Gruppe12
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

    private final int PICTURE_HEIGHT = 164;
    @FXML
    private ImageView logoView;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            logoView.setImage(new Image("/pictures/ElectroshoppenLogo.png"));
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }

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
            Image i;
            try
            {
                i = new Image("/pictures/" + p.getType() + ".jpg"); // Should probably get this from some Image Manager Interface
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                continue;
            }

            productView.getChildren().add(new ProductView(i, p.getName(), p.toString(), p.getPrice()));
        }
    }
}

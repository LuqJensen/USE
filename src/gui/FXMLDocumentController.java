package gui;

import interfaces.IProduct;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import unifiedshoppingexperience.UnifiedShoppingExperience;

/**
 *
 * @author Gruppe12
 */
public class FXMLDocumentController implements Initializable
{
    private static final int PICTURE_HEIGHT = 164; // UNUSED
    @FXML
    private TextField findProductSearchField;
    @FXML
    private Button findProductButton;
    @FXML
    private CheckBox musKeyboardsCheckBox, grafikkortCheckBox, skærmeCheckBox, kabinetterCheckBox, harddiskeCheckBox;

    private CheckBox[] allCheckBoxes;
    @FXML
    private ImageView logoView;
    @FXML
    private ScrollPane contentContainer;

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
        VBox productView = new VBox();
        // Temporary solution to prevent Text Control of ProductView to exceed parents limits...
        productView.setPrefWidth(contentContainer.getPrefWidth() - 15.0);
        setContent(productView);

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

        List<IProduct> products = UnifiedShoppingExperience.getInstance().findProducts(descriptionTags, typeTags);
        for (IProduct p : products)
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

            CallBack cb = () ->
            {
                addToCart(p.getModel());
            };

            productView.getChildren().add(new ProductView(cb, i, p.getName(), p.toString()/*TODO fix description param*/, p.getPrice()));
        }
    }

    private void addToCart(String productModel)
    {
        System.out.println(productModel + "IMPLEMENT ME PLS");
        // add to cart use case.
    }

    @FXML
    private void seeCart()
    {
        VBox view = new VBox();
        // Add content to view, eg. something that resembles viewing a cart.
        setContent(view);
        // Add content to content, logic blabla...
    }

    private void setContent(Node content)
    {
        contentContainer.setContent(content);
    }
}

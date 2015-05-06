package gui;

import interfaces.IProduct;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import unifiedshoppingexperience.Cart;
import unifiedshoppingexperience.ProductLine;
import unifiedshoppingexperience.UnifiedShoppingExperience;

/**
 *
 * @author Gruppe12
 */

public class FXMLDocumentController implements Initializable
{
    private String costumerID = "C12345";
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
        UnifiedShoppingExperience.getInstance().addProduct(costumerID, productModel);
        seeCart();      
    }
    
    private void seeCart()
    {
        Cart c = UnifiedShoppingExperience.getInstance().getShoppingCart(costumerID);
        double sum = 0;
        
        for(ProductLine pl : c.getProducts())
        {
            sum += pl.getTotalPrice();
        }
        
        VBox productLineView = new VBox();
        VBox bottomVBox = new VBox();
        HBox cartOverView = new HBox();
        HBox totalPriceView = new HBox();
        
        Button proceed = new Button("Gå til kassen");
        proceed.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        Label sumLabel = new Label(sum + ",-");
        sumLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        Label quantity = new Label("Antal");
        quantity.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        Label price = new Label("Stk.");
        price.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        Label totalPrice = new Label("Total");
        totalPrice.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        cartOverView.setMaxWidth(contentContainer.getPrefWidth() - 37.0);
        
        cartOverView.getChildren().addAll(quantity, price, totalPrice);
        
        cartOverView.setAlignment(Pos.CENTER_RIGHT);
        cartOverView.setSpacing(120);
        
        totalPriceView.setMaxWidth(contentContainer.getPrefWidth() - 15.0);
        totalPriceView.getChildren().add(sumLabel);
        totalPriceView.setAlignment(Pos.CENTER_RIGHT);
        
        bottomVBox.getChildren().addAll(totalPriceView, proceed);
        bottomVBox.setAlignment(Pos.CENTER_RIGHT);
        bottomVBox.setSpacing(20);
        
        BorderPane bp = new BorderPane();
        bp.setTop(cartOverView);
        bp.setCenter(productLineView);
        bp.setBottom(bottomVBox);
        
        
        productLineView.setPrefWidth(contentContainer.getPrefWidth() - 15.0);
        setContent(bp);
        
        
        for(ProductLine p : c.getProducts())
        {
            Image i;
            try
            {
                i = new Image("/pictures/" + p.getProduct().getType() + ".jpg"); // Should probably get this from some Image Manager Interface
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                continue;
            }

            CallBack cb = () ->
            {
                addToCart(p.getProduct().getModel());
            };

            productLineView.getChildren().add(new ProductLineView(cb, i, p.getProduct().getName(), p.getProduct().toString()/*TODO fix description param*/, p.getProduct().getPrice(), p.getQuantity(), p.getTotalPrice()));
        }
        
        
        // Add content to content, logic blabla...
    }

    private void setContent(Node content)
    {
        contentContainer.setContent(content);
    }
}

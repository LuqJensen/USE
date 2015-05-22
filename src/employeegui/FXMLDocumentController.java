package employeegui;

import gui.ProductLineView;
import interfaces.CallBack;
import interfaces.CartDTO;
import interfaces.CustomerDTO;
import interfaces.ProductDTO;
import interfaces.ProductLineDTO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import unifiedshoppingexperience.UnifiedShoppingExperience;
import utility.PriceFormatter;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class FXMLDocumentController implements Initializable
{

    private CustomerDTO currentCustomer;
    @FXML
    private ScrollPane findProductsScrollPane;
    @FXML
    private TextField findProductSearchField;
    @FXML
    private Button findProductButton;
    @FXML
    private CheckBox harddiskeCheckBox, kabinetterCheckBox, musKeyboardsCheckBox, skærmeCheckBox, grafikkortCheckBox;
    private CheckBox[] allCheckBoxes;
    @FXML
    private Tab findProductTab;
    @FXML
    private Tab saleTab;
    @FXML
    private ScrollPane saleContentScrollPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button findCustomerButton;
    @FXML
    private TextField findCustomerTextField;
    @FXML
    private ScrollPane customerScrollPane;
    @FXML
    private Tab customerpageTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TESTcode

        UnifiedShoppingExperience.getInstance().getCustomer("TestCustomer"); //creates customer with ID.
        UnifiedShoppingExperience.getInstance().setEmail("TestCustomer", "holla@me.bro");
        // end of test code
        allCheckBoxes = new CheckBox[]
        {
            musKeyboardsCheckBox, grafikkortCheckBox, skærmeCheckBox, kabinetterCheckBox, harddiskeCheckBox
        };
    }

    /**
     * Sets content on the main scroll pane of the UI.
     *
     * @param content The content to be put on the scroll pane.
     */
    private void setContent(Node content)
    {
        findProductsScrollPane.setContent(content);
    }

    /**
     * Adds a product to the customers cart by product model.
     *
     * @param productModel The product model of the product.
     */
    private void addToCart(String productModel)
    {
        UnifiedShoppingExperience.getInstance().addProduct(currentCustomer.getID(), productModel);
        seeCart();
    }

    @FXML
    private void findProduct(ActionEvent event)
    {
        findProduct();
    }

    @FXML
    private void findProductEnter(ActionEvent event)
    {
        findProduct();
    }

    /**
     * Search for a product, based on the text in the search field.
     */
    private void findProduct()
    {
        VBox productView = new VBox();
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

        List<ProductDTO> products = UnifiedShoppingExperience.getInstance().findProducts(descriptionTags, typeTags);
        for (ProductDTO p : products)
        {
            Image i;
            try
            {
                i = new Image("/pictures/" + p.getType() + ".jpg", 150, 0, true, false); // Should probably get this from some Image Manager Interface

            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                continue;
            }

            //Ændre logikken her
            CallBack cb = () ->
            {
                String email = currentCustomer == null ? "" : currentCustomer.getEmail();

                boolean beenInLoop = false;
                while (email != null && UnifiedShoppingExperience.getInstance().getRegisteredCustomer(email) == null)
                {
                    beenInLoop = true;
                    email = (String) JOptionPane.showInputDialog(null, "Kunde er ikke fundet, skriv email på en kunde.");
                }

                if (email != null)
                {
                    if (beenInLoop)
                    {
                        currentCustomer = UnifiedShoppingExperience.getInstance().getRegisteredCustomer(email);
                        updateCustomerPage();
                    }
                    addToCart(p.getModel());
                }

            };

            productView.getChildren().add(new ProductView(cb, i, p));
        }
    }

    /**
     * Shows a cart to the UI.
     */
    private void seeCart()
    {
        tabPane.getSelectionModel().select(saleTab);
        final int COLUMN_SPACING = 80;
        CartDTO c = UnifiedShoppingExperience.getInstance().getShoppingCart(currentCustomer.getID());

        // GridPane
        Label quantity = new Label("Antal");
        quantity.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Label price = new Label("Pris");
        price.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Label totalPrice = new Label("Totalt");
        totalPrice.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        GridPane productLineDescription = new GridPane();
        productLineDescription.setPadding(new Insets(5, 5, 5, 5));
        //productLineDescription.setGridLinesVisible(true); use for visual debugging of grids.
        productLineDescription.setAlignment(Pos.CENTER_RIGHT);
        // Adds 3 columns: amount, price, totalprice, to the gridpane with spacing = COLUMN_SPACING.
        for (int i = 0; i < 3; ++i)
        {
            productLineDescription.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
        }

        productLineDescription.add(quantity, 0, 0);
        GridPane.setHalignment(quantity, HPos.RIGHT);

        productLineDescription.add(price, 1, 0);
        GridPane.setHalignment(price, HPos.RIGHT);

        productLineDescription.add(totalPrice, 2, 0);
        GridPane.setHalignment(totalPrice, HPos.RIGHT);

        // VBox
        VBox productLineView = new VBox();
        for (ProductLineDTO pl : c.getProducts())
        {
            ProductDTO p = pl.getProduct();
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

            productLineView.getChildren().add(new ProductLineView(i, pl, COLUMN_SPACING)); // pass IProductLine as param instead.
        }

        // VBox
        Label sum = new Label(PriceFormatter.format(c.getPrice()));
        sum.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // BorderPane
        BorderPane bp = new BorderPane();
        // Offset bottomVBox 5 pixels from right side to match GridPane productLineDescription.
        bp.setTop(productLineDescription);
        bp.setCenter(productLineView);
        saleContentScrollPane.setContent(bp);
    }

    /**
     * Updates the customer page when a new customers page needs to be shown.
     */
    private void updateCustomerPage()
    {
        GridPane gp = new GridPane();

        Button seeCart = new Button("Se indkøbskurv");
        Button changeAccountInfo = new Button("Ændre kontoinformation");
        Button claim = new Button("Reklamation");
        Button seeWishlists = new Button("Se ønskelister");
        Button seeOrder = new Button("Se ordre");
        seeCart.setOnAction((ActionEvent event2) ->
        {
            seeCart();
        });

        Button seePD = new Button("Se personaliseret data");

        //Disabled because it isnt implemented.
        changeAccountInfo.setDisable(true);
        claim.setDisable(true);
        seeWishlists.setDisable(true);
        seeOrder.setDisable(true);
        seePD.setDisable(true);

        Label header = new Label("Kundeside");

        if (currentCustomer == null)
        {
            header = new Label("Kunden findes ikke");
        }
        else
        {
            GridPane gpInfo = new GridPane();

            Label deliveryAddress = new Label("                   ");
            Label name = new Label("");
            Label email = new Label(currentCustomer.getEmail());
            Label phoneNumber = new Label(currentCustomer.getPhoneNumber());

            Label fatEmail = new Label("Email: ");
            Label fatName = new Label("Navn: ");
            Label fatAddress = new Label("Adresse: ");
            Label fatPhoneNumber = new Label("Telefonnummer: ");

            if (currentCustomer.getDefaultDeliveryAddress() != null)
            {
                deliveryAddress = new Label(currentCustomer.getDefaultDeliveryAddress().toString());
            }

            if (currentCustomer.getFirstName() != null || currentCustomer.getSurname() != null)
            {
                name = new Label(currentCustomer.getFirstName() + currentCustomer.getSurname());
            }

            fatAddress.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            fatEmail.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            fatName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            fatPhoneNumber.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            /*
             Spacing
             */
            gp.add(seeCart, 0, 2);
            gp.add(changeAccountInfo, 0, 3);
            gp.add(claim, 0, 4);
            gp.add(seeWishlists, 0, 5);
            gp.add(seeOrder, 0, 6);
            gp.add(seePD, 0, 7);
            GridPane.setValignment(fatAddress, VPos.TOP);

            fatAddress.setPadding(new Insets(2));
            fatName.setPadding(new Insets(2));
            fatEmail.setPadding(new Insets(2));
            fatPhoneNumber.setPadding(new Insets(2));
            deliveryAddress.setPadding(new Insets(2));
            name.setPadding(new Insets(2));
            email.setPadding(new Insets(2));
            phoneNumber.setPadding(new Insets(2));
            gpInfo.add(fatAddress, 0, 0);
            gpInfo.add(fatName, 0, 1);
            gpInfo.add(fatEmail, 0, 2);
            gpInfo.add(fatPhoneNumber, 0, 3);
            gpInfo.add(deliveryAddress, 1, 0);
            gpInfo.add(name, 1, 1);
            gpInfo.add(email, 1, 2);
            gpInfo.add(phoneNumber, 1, 3);
            gp.add(gpInfo, 0, 1);
            gpInfo.setGridLinesVisible(true);
            gp.setVgap(15);
        }
        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        gp.add(header, 0, 0);
        customerScrollPane.setContent(gp);
    }

    @FXML
    private void findCustomer(ActionEvent event)
    {

        currentCustomer = UnifiedShoppingExperience.getInstance().getRegisteredCustomer(findCustomerTextField.getText());
        updateCustomerPage();
    }

    @FXML
    private void findCustomerEnter(ActionEvent event)
    {

        currentCustomer = UnifiedShoppingExperience.getInstance().getRegisteredCustomer(findCustomerTextField.getText());
        updateCustomerPage();
    }

    @FXML
    private void checkboxAction(ActionEvent event)
    {
        findProduct();
    }

}

package employeegui;

import interfaces.CallBack;
import interfaces.CartDTO;
import interfaces.ProductDTO;
import interfaces.ProductLineDTO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import unifiedshoppingexperience.UnifiedShoppingExperience;
import utility.PriceFormatter;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class FXMLDocumentController implements Initializable
{
    private String customerID = "C12345";
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
    private Tab findCustomerTab;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        allCheckBoxes = new CheckBox[]
        {
            musKeyboardsCheckBox, grafikkortCheckBox, skærmeCheckBox, kabinetterCheckBox, harddiskeCheckBox
        };
    }

    private void setContent(Node content)
    {
        findProductsScrollPane.setContent(content);
    }

    private void addToCart(String productModel)
    {
        UnifiedShoppingExperience.getInstance().addProduct(customerID, productModel);
        goToSale();
    }

    @FXML
    private void findProduct(ActionEvent event)
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
                i = new Image("/pictures/" + p.getType() + ".jpg"); // Should probably get this from some Image Manager Interface
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                continue;
            }

            //Ændre logikken her
            CallBack cb = () ->
            {
                addToCart(p.getModel());
            };

            productView.getChildren().add(new ProductView(cb, i, p));
        }
    }

    @FXML
    private void findProductEnter(ActionEvent event)
    {
        findProduct(event);
    }

    private void goToSale()
    {
        tabPane.getSelectionModel().select(saleTab);
        final int COLUMN_SPACING = 140;
        CartDTO c = UnifiedShoppingExperience.getInstance().getShoppingCart(customerID);

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
        Button proceed = new Button("Til Betaling");
        proceed.setOnAction((ActionEvent event) ->
        {
            toCheckout();
        });
        proceed.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label sum = new Label(PriceFormatter.format(c.getPrice()));
        sum.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox bottomVBox = new VBox();
        bottomVBox.getChildren().addAll(sum, proceed);
        bottomVBox.setAlignment(Pos.CENTER_RIGHT);
        bottomVBox.setSpacing(20);

        // BorderPane
        BorderPane bp = new BorderPane();
        // Offset bottomVBox 5 pixels from right side to match GridPane productLineDescription.
        BorderPane.setMargin(bottomVBox, new Insets(0, 5, 0, 0));
        bp.setTop(productLineDescription);
        bp.setCenter(productLineView);
        bp.setBottom(bottomVBox);
        saleContentScrollPane.setContent(bp);
    }

    private void toCheckout()
    {
    }

    @FXML
    private void findCustomer(ActionEvent event)
    {
    }

    @FXML
    private void findCustomerEnter(ActionEvent event)
    {
    }

}

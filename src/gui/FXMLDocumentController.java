package gui;

import utility.PriceFormatter;
import interfaces.CallBack;
import interfaces.CartDTO;
import interfaces.CustomerDTO;
import interfaces.OrderDTO;
import interfaces.ProductDTO;
import interfaces.ProductLineDTO;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import shared.CreateOrderErrors;
import unifiedshoppingexperience.UnifiedShoppingExperience;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import shared.CreateOrderResult;
import shared.OrderStatus;
import thirdpartypaymentprocessor.PaypalDummy;
import shared.Address;
import utility.TryParse;

/**
 *
 * @author Gruppe12
 */
public class FXMLDocumentController implements Initializable
{
    private final String CUSTOMER_ID = "c123456";

    @FXML
    private TextField findProductSearchField;
    @FXML
    private CheckBox musKeyboardsCheckBox, grafikkortCheckBox, skærmeCheckBox, kabinetterCheckBox, harddiskeCheckBox;

    private CheckBox[] allCheckBoxes;
    @FXML
    private ImageView logoView;
    @FXML
    private ScrollPane contentContainer;
    @FXML
    private Button pcTabletsButton;
    @FXML
    private Button hardwareButton;
    @FXML
    private Button forsideButton;
    @FXML
    private Button mobilGpsButton;
    @FXML
    private Button hvidevarerButton;
    @FXML
    private Button husholdningButton;
    @FXML
    private Button fotoVideoButton;
    @FXML
    private Button konsolSpilButton;
    @FXML
    private Button filmButton;
    @FXML
    private Button tvRadioButton;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // tab buttons not implemented, so they are disabled.
        pcTabletsButton.setDisable(true);
        hardwareButton.setDisable(true);
        mobilGpsButton.setDisable(true);
        hvidevarerButton.setDisable(true);
        husholdningButton.setDisable(true);
        fotoVideoButton.setDisable(true);
        tvRadioButton.setDisable(true);
        forsideButton.setDisable(true);
        konsolSpilButton.setDisable(true);
        filmButton.setDisable(true);

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
        findProduct();
    }

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
                i = new Image("/pictures/" + p.getType() + ".jpg"); // Should probably get this from some Image Manager Interface
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                continue;
            }

            //Lambda used to replace a anonymous class
            CallBack cb = () ->
            {
                addToCart(p.getModel());
            };

            productView.getChildren().add(new ProductView(cb, i, p));
        }
    }

    @FXML
    private void findProduct(ActionEvent event)
    {
        findProduct();
    }

    private void proceedToCheckout()
    {
        final int COLUMN_SPACING = 140;

        CreateOrderResult orderResult = UnifiedShoppingExperience.getInstance().createOrder(CUSTOMER_ID);

        if (orderResult.getError() == CreateOrderErrors.UNPAID)
        {
            CustomerDTO customer = UnifiedShoppingExperience.getInstance().getCustomer(CUSTOMER_ID);
            Address address = customer.getDefaultDeliveryAddress();

            Label currentInhabitantName = new Label();

            Label currentStreetName = new Label();

            Label currentZipcode = new Label();

            Label currentCity = new Label();

            Label currentCountry = new Label();

            if (address != null)
            {
                currentInhabitantName.setText(address.getInhabitantName());
                currentStreetName.setText(address.getStreetName());
                currentZipcode.setText(Integer.toString(address.getZipCode()));
                currentCity.setText(address.getCity());
                currentCountry.setText(address.getCountry());
            }

            HBox zipAndCity = new HBox();
            zipAndCity.setSpacing(5.0);
            zipAndCity.getChildren().addAll(currentZipcode, currentCity);

            // GridPane
            Label addressHeader = new Label("Vælg leveringsaddresse:");
            addressHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            // BorderPane
            BorderPane addressCreation = new BorderPane();

            Button setNewDeliveryAddress = new Button();
            setNewDeliveryAddress.setText("Ny LeveringsAdresse");
            setNewDeliveryAddress.setMinWidth(100.0);
            setNewDeliveryAddress.setOnAction((ActionEvent event) ->
            {
                GridPane newAddressGrid = new GridPane();
                addressCreation.setBottom(newAddressGrid);

                for (int i = 0; i < 2; ++i)
                {
                    newAddressGrid.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
                }

                Label newAddressHeader = new Label("Ny adresse");
                newAddressHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

                Label inhabitantHeader = new Label("Navn:");
                inhabitantHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                TextField inhabitant = new TextField();

                Label streetNameHeader = new Label("Gadenavn:");
                streetNameHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                TextField streetName = new TextField();

                Label zipcodeHeader = new Label("Postnr:");
                zipcodeHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                TextField zipcode = new TextField();

                Label cityHeader = new Label("By:");
                cityHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                TextField city = new TextField();

                Label countryHeader = new Label("Land:");
                countryHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));

                TextField country = new TextField();

                Button save = new Button();
                save.setText("Gem");
                save.setOnAction((ActionEvent event2) ->
                {
                    currentInhabitantName.setText(inhabitant.getText());
                    currentStreetName.setText(streetName.getText());
                    currentZipcode.setText(zipcode.getText());
                    currentCity.setText(city.getText());
                    currentCountry.setText(country.getText());
                    addressCreation.setBottom(null);
                });

                Button cancel = new Button();
                cancel.setText("Afbryd");
                cancel.setOnAction((ActionEvent event2) ->
                {
                    addressCreation.setBottom(null);
                });

                newAddressGrid.add(newAddressHeader, 0, 0, 2, 1);
                newAddressGrid.add(inhabitantHeader, 0, 1);
                newAddressGrid.add(inhabitant, 1, 1);
                newAddressGrid.add(streetNameHeader, 0, 2);
                newAddressGrid.add(streetName, 1, 2);
                newAddressGrid.add(zipcodeHeader, 0, 3);
                newAddressGrid.add(zipcode, 1, 3);
                newAddressGrid.add(cityHeader, 0, 4);
                newAddressGrid.add(city, 1, 4);
                newAddressGrid.add(countryHeader, 0, 5);
                newAddressGrid.add(country, 1, 5);
                newAddressGrid.add(save, 0, 6);
                newAddressGrid.add(cancel, 1, 6);
            });

            addressCreation.setCenter(setNewDeliveryAddress);

            GridPane addressGrid = new GridPane();
            addressGrid.setPadding(new Insets(10, 10, 10, 0));
            // Adds 2 defaultDeliveryAddress, setNewDeliveryAddress to the gridpane with spacing = 200.
            for (int i = 0; i < 2; ++i)
            {
                addressGrid.getColumnConstraints().add(new ColumnConstraints(200));
            }

            addressGrid.add(addressHeader, 0, 0, 2, 1);
            addressGrid.add(currentInhabitantName, 0, 1);
            addressGrid.add(currentStreetName, 0, 2);
            addressGrid.add(zipAndCity, 0, 3);
            addressGrid.add(currentCountry, 0, 4);
            addressGrid.add(addressCreation, 1, 1, 1, 2);

            // VBox
            Label paymentMethodHeader = new Label("Vælg betalingsmetode:");
            paymentMethodHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            ToggleGroup tg = new ToggleGroup();

            RadioButton visa = new RadioButton();
            visa.setToggleGroup(tg);
            visa.setText("Visa (Dankort)\nMasterCard");

            RadioButton paypal = new RadioButton();
            paypal.setToggleGroup(tg);
            paypal.setText("Paypal");

            RadioButton pickup = new RadioButton();
            pickup.setToggleGroup(tg);
            pickup.setText("Betaling i Pick-up-Point");

            tg.selectToggle(paypal);

            VBox paymentMethodBox = new VBox();
            paymentMethodBox.setSpacing(10.0);
            paymentMethodBox.getChildren().addAll(paymentMethodHeader, visa, paypal, pickup);

            // GridPane
            Label orderDetailsHeader = new Label("Ordredetaljer:");
            orderDetailsHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Label model = new Label("Prodnr.");
            model.setFont(Font.font("Arial", FontWeight.BOLD, 12));

            Label productName = new Label("Produktnavn");
            productName.setFont(Font.font("Arial", FontWeight.BOLD, 12));

            Label quantity = new Label("Antal");
            quantity.setFont(Font.font("Arial", FontWeight.BOLD, 12));

            Label price = new Label("Pris");
            price.setFont(Font.font("Arial", FontWeight.BOLD, 12));

            Label totalPrice = new Label("Totalt");
            totalPrice.setFont(Font.font("Arial", FontWeight.BOLD, 12));

            GridPane orderLineDescription = new GridPane();
            orderLineDescription.setPadding(new Insets(5, 15, 5, 5));

            // Adds 2 columns for model and title with spacing = COLUMN_SPACING.
            for (int i = 0; i < 2; ++i)
            {
                orderLineDescription.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
            }

            // Adds a single empty column between title and quantity.
            orderLineDescription.getColumnConstraints().add(new ColumnConstraints(75.0));

            for (int i = 0; i < 3; ++i)
            {
                orderLineDescription.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
            }

            orderLineDescription.add(orderDetailsHeader, 0, 0);

            orderLineDescription.add(model, 0, 1);
            GridPane.setHalignment(quantity, HPos.LEFT);

            orderLineDescription.add(productName, 1, 1);
            GridPane.setHalignment(quantity, HPos.LEFT);

            orderLineDescription.add(quantity, 3, 1);
            GridPane.setHalignment(quantity, HPos.RIGHT);

            orderLineDescription.add(price, 4, 1);
            GridPane.setHalignment(price, HPos.RIGHT);

            orderLineDescription.add(totalPrice, 5, 1);
            GridPane.setHalignment(totalPrice, HPos.RIGHT);

            // VBox
            VBox orderLineView = new VBox();
            for (ProductLineDTO pl : customer.getOrder(orderResult.getOrderID()).getProductLines())
            {
                orderLineView.getChildren().add(new OrderLineView(pl, COLUMN_SPACING));
            }

            Button finishSale = new Button();
            finishSale.setText("Fuldfør");
            finishSale.setStyle("-fx-base: #ffd000;");
            finishSale.setOnAction((ActionEvent event) ->
            {
                String deliveryAddressInhabitant = currentInhabitantName.getText();
                String deliveryAddressStreet = currentStreetName.getText();
                String deliveryAddressZipcode = currentZipcode.getText();
                String deliveryAddressCity = currentCity.getText();
                String deliveryAddressCountry = currentCountry.getText();

                if (deliveryAddressInhabitant.isEmpty() || deliveryAddressStreet.isEmpty()
                    || deliveryAddressZipcode.isEmpty() || deliveryAddressCity.isEmpty()
                    || deliveryAddressCountry.isEmpty() || !TryParse.tryParseInteger(deliveryAddressZipcode))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Angiv venlist en gyldig adresse.");
                    return;
                }

                String paymentMethod = ((RadioButton)tg.getSelectedToggle()).getText();

                finishSale(orderResult.getOrderID(), paymentMethod, new Address(deliveryAddressInhabitant, deliveryAddressStreet,
                                                                                Integer.parseInt(deliveryAddressZipcode),
                                                                                deliveryAddressCity, deliveryAddressCountry));
            });

            // GridPane
            GridPane gp = new GridPane();
            gp.setPadding(new Insets(10, 10, 10, 10));
            gp.setVgap(20);

            gp.add(addressGrid, 0, 1);
            gp.add(paymentMethodBox, 0, 2);
            gp.add(finishSale, 1, 3);
            GridPane.setHalignment(finishSale, HPos.RIGHT);
            gp.add(orderLineDescription, 0, 4, 2, 1);
            gp.add(orderLineView, 0, 5, 2, 1);
            setContent(gp);
        }
        else if (orderResult.getError() == CreateOrderErrors.NO_EMAIL)
        {
            String email = JOptionPane.showInputDialog(new JFrame(), "Skriv email:");

            if (UnifiedShoppingExperience.getInstance().setEmail(CUSTOMER_ID, email))
            {
                proceedToCheckout();
            }
        }
    }

    private void finishSale(int orderID, String paymentMethod, Address address)
    {
        CallBack cb = () ->
        {
            // We cant execute UI methods from the business thread, so we tell the UI thread to run this when it pleases.
            Platform.runLater(() ->
            {
                showOrder(orderID);
            });
        };

        String URL = UnifiedShoppingExperience.getInstance().finishSale(CUSTOMER_ID, orderID, paymentMethod, address, cb);
        goToURL(URL);
    }

    private void goToURL(String URL)
    {
        // We now pretend that we redirect the customer to the given URL of the decided third party payment processor.
        // In reality we show a dummy window that lets the customer finish or cancel the payment.
        // Thus we simulate being connected to a third party payment processor.
        String[] input = URL.split("%%");
        StringBuilder sb = new StringBuilder();
        String[] productNames = input[2].split("%");
        String[] quantities = input[3].split("%");

        sb.append("Velkommen til PaypalDummy!\nTryk OK for at gennemføre dit køb, eller CANCEL for annullere.\n\n");
        sb.append(String.format("Produkt: %s\n", input[1].replace("%", ", ")));
        sb.append("Vare:");

        for (int i = 0; i < productNames.length; ++i)
        {
            sb.append(String.format(" %s x%s,", productNames[i], quantities[i]));
        }

        sb.append(String.format("\nTotal DKK: %s", input[4]));

        int result = JOptionPane.showConfirmDialog(new JFrame(), sb.toString(), URL, 2);
        if (result == 0)
        {
            PaypalDummy pd = new PaypalDummy();
            // In any realistic case the third party payment proessor will filter the URL and send back only what we need.
            pd.confirm(URL.substring(URL.lastIndexOf("/") + 1));
        }
    }

    private void showOrder(int orderID)
    {
        final int COLUMN_SPACING = 140;
        CustomerDTO customer = UnifiedShoppingExperience.getInstance().getCustomer(CUSTOMER_ID);
        OrderDTO order = customer.getOrder(orderID);

        // GridPane
        Label orderStatusHeader = new Label("Ordrestatus:");
        orderStatusHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label orderStatus = new Label();

        if (order.getStatus() == OrderStatus.PAID)
        {
            orderStatus = new Label("Betalt");
        }
        else if (order.getStatus() == OrderStatus.DISPATCHED)
        {
            orderStatus = new Label("Afsendt");
        }

        Label orderIDHeader = new Label("Ordrenummer:");
        orderIDHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label orderIDLabel = new Label(Integer.toString(orderID));

        Label orderDateHeader = new Label("Ordre placeret:");
        orderDateHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label orderDate = new Label(order.getPurchaseDate().toString());

        Label orderPaymentHeader = new Label("Betalingsmetode:");
        orderPaymentHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label orderPayment = new Label(order.getPaymentMethod());

        Label orderOwnerHeader = new Label("Bestiller: ");
        orderOwnerHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label orderOwner = new Label(customer.getFirstName() + " " + customer.getSurname());

        Label orderAddressHeader = new Label("Leveres til:");
        orderAddressHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Text orderAddress = new Text(order.getAddress());

        GridPane orderInfoGrid = new GridPane();
        orderInfoGrid.setPadding(new Insets(10, 10, 10, 0));

        // Adds 2 columns for header and data with spacing = COLUMN_SPACING.
        for (int i = 0; i < 2; ++i)
        {
            orderInfoGrid.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
        }

        orderInfoGrid.add(orderStatusHeader, 0, 0);
        orderInfoGrid.add(orderStatus, 1, 0);
        orderInfoGrid.add(orderIDHeader, 0, 1);
        orderInfoGrid.add(orderIDLabel, 1, 1);
        orderInfoGrid.add(orderDateHeader, 0, 2);
        orderInfoGrid.add(orderDate, 1, 2, 2, 1);
        orderInfoGrid.add(orderPaymentHeader, 0, 3);
        orderInfoGrid.add(orderPayment, 1, 3);
        orderInfoGrid.add(orderOwnerHeader, 0, 4);
        orderInfoGrid.add(orderOwner, 1, 4, 2, 1);
        orderInfoGrid.add(orderAddressHeader, 0, 5);
        orderInfoGrid.add(orderAddress, 1, 5, 2, 1);

        // GridPane
        Label orderDetailsHeader = new Label("Ordredetaljer:");
        orderDetailsHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label model = new Label("Varenr.");
        model.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label productName = new Label("Beskrivelse");
        productName.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label quantity = new Label("Antal");
        quantity.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label price = new Label("Pris");
        price.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label totalPrice = new Label("Sum");
        totalPrice.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        GridPane orderLineDescription = new GridPane();
        orderLineDescription.setPadding(new Insets(5, 15, 5, 5));

        // Adds 2 columns for model and title with spacing = COLUMN_SPACING.
        for (int i = 0; i < 2; ++i)
        {
            orderLineDescription.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
        }

        // Adds a single empty column between title and quantity.
        orderLineDescription.getColumnConstraints().add(new ColumnConstraints(75.0));

        for (int i = 0; i < 3; ++i)
        {
            orderLineDescription.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
        }

        orderLineDescription.add(orderDetailsHeader, 0, 0);

        orderLineDescription.add(model, 0, 1);
        GridPane.setHalignment(quantity, HPos.LEFT);

        orderLineDescription.add(productName, 1, 1);
        GridPane.setHalignment(quantity, HPos.LEFT);

        orderLineDescription.add(quantity, 3, 1);
        GridPane.setHalignment(quantity, HPos.RIGHT);

        orderLineDescription.add(price, 4, 1);
        GridPane.setHalignment(price, HPos.RIGHT);

        orderLineDescription.add(totalPrice, 5, 1);
        GridPane.setHalignment(totalPrice, HPos.RIGHT);

        // VBox
        VBox orderLineView = new VBox();
        for (ProductLineDTO pl : order.getProductLines())
        {
            orderLineView.getChildren().add(new OrderLineView(pl, COLUMN_SPACING));
        }

        // GridPane
        Label productsPriceHeader = new Label("Varer:");
        Label productsPrice = new Label(PriceFormatter.formatDKK(order.getPrice()));

        // Delivery price is not implemented on order.
        Label deliveryPriceHeader = new Label("Fragt:");
        Label deliveryPrice = new Label(PriceFormatter.formatDKK(new BigDecimal("0.0")));

        // Thus the total price is always the same as the price for the products.
        Label sumHeader = new Label("Totalt:");
        sumHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label sum = new Label(PriceFormatter.formatDKK(order.getPrice()));
        sum.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        // Tax amount is not implemented on order, so we do a hack of calc here.
        Label taxHeader = new Label("Heraf Moms:");
        Label tax = new Label(PriceFormatter.formatDKK(order.getPrice().multiply(new BigDecimal("0.2"))));

        GridPane priceInfo = new GridPane();
        priceInfo.setAlignment(Pos.BOTTOM_RIGHT);
        priceInfo.setVgap(10);

        for (int i = 0; i < 2; ++i)
        {
            priceInfo.getColumnConstraints().add(new ColumnConstraints(COLUMN_SPACING));
        }

        priceInfo.add(productsPriceHeader, 0, 0);
        priceInfo.add(productsPrice, 1, 0);
        priceInfo.add(deliveryPriceHeader, 0, 1);
        priceInfo.add(deliveryPrice, 1, 1);
        priceInfo.add(sumHeader, 0, 2);
        priceInfo.add(sum, 1, 2);
        priceInfo.add(taxHeader, 0, 3);
        priceInfo.add(tax, 1, 3);

        // GridPane
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setVgap(20);

        gp.add(orderInfoGrid, 0, 0);
        gp.add(orderLineDescription, 0, 1, 2, 1);
        gp.add(orderLineView, 0, 2, 2, 1);
        gp.add(priceInfo, 1, 3);
        setContent(gp);
    }

    private void addToCart(String productModel)
    {
        UnifiedShoppingExperience.getInstance().addProduct(CUSTOMER_ID, productModel);
        seeCart();
    }

    @FXML
    private void seeCartButton(ActionEvent event)
    {
        seeCart();
    }

    private void seeCart()
    {
        final int COLUMN_SPACING = 140;
        CartDTO c = UnifiedShoppingExperience.getInstance().getShoppingCart(CUSTOMER_ID);

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
        Button proceed = new Button("Gå til kassen");
        proceed.setStyle("-fx-base: #ffd000;");
        proceed.setOnAction((ActionEvent event) ->
        {
            proceedToCheckout();
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
        setContent(bp);
    }

    private void setContent(Node content)
    {
        contentContainer.setContent(content);
    }

    @FXML
    private void kundekontoAction(ActionEvent event)
    {
        GridPane gp = new GridPane();
        Button changeAccountInfo = new Button("Ændre kontoinformation");
        Button claim = new Button("Reklamation");
        Button seeWishlists = new Button("Se ønskelister");
        Button seeOrder = new Button("Se ordre");
        //Disabled because it isnt implemented.
        changeAccountInfo.setDisable(true);
        claim.setDisable(true);
        seeWishlists.setDisable(true);
        seeOrder.setDisable(true);

        CustomerDTO customer = UnifiedShoppingExperience.getInstance().getCustomer(CUSTOMER_ID);

        GridPane gpInfo = new GridPane();

        Label deliveryAddress = new Label("                   ");
        Label name = new Label("");
        Label email = new Label(customer.getEmail());
        Label phoneNumber = new Label(customer.getPhoneNumber());

        Label fatEmail = new Label("Email: ");
        Label fatName = new Label("Navn: ");
        Label fatAddress = new Label("Adresse: ");
        Label fatPhoneNumber = new Label("Telefonnummer: ");

        if (customer.getDefaultDeliveryAddress() != null)
        {
            deliveryAddress = new Label(customer.getDefaultDeliveryAddress().toString());
        }

        if (customer.getFirstName() != null || customer.getSurname() != null)
        {
            name = new Label(customer.getFirstName() + customer.getSurname());
        }

        Label header = new Label("Kundekonto");

        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        fatAddress.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        fatEmail.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        fatName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        fatPhoneNumber.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        /*
         Spacing
         */
        gp.add(header, 0, 0);
        gp.add(changeAccountInfo, 0, 2);
        gp.add(claim, 0, 3);
        gp.add(seeWishlists, 0, 4);
        gp.add(seeOrder, 0, 5);
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
        setContent(gp);

    }

    @FXML
    private void checkboxActivated(ActionEvent event)
    {
        findProduct();
    }

}

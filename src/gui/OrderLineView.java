package gui;

import utility.PriceFormatter;
import interfaces.ProductDTO;
import interfaces.ProductLineDTO;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Gruppe12
 */
public class OrderLineView extends GridPane
{
    public OrderLineView(ProductLineDTO pl, int columnSpacing)
    {
        super();

        ProductDTO product = pl.getProduct();

        Label model = new Label(product.getModel());

        Text title = new Text(product.getName());
        // Wrap text at 250 pixel width.
        title.setWrappingWidth(250);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Text description = new Text(product.getDescription());

        Label quantity = new Label(Integer.toString(pl.getQuantity()));
        quantity.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Label price = new Label(PriceFormatter.format(product.getPrice()));
        price.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Label totalPrice = new Label(PriceFormatter.format(pl.getTotalPrice()));
        totalPrice.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setAlignment(Pos.CENTER_RIGHT);

        for (int i = 0; i < 2; ++i)
        {
            this.getColumnConstraints().add(new ColumnConstraints(columnSpacing));
        }

        this.getColumnConstraints().add(new ColumnConstraints(85.0));
        // Adds 3 columns: amount, price, totalprice, to the gridpane with spacing = COLUMN_SPACING.
        for (int i = 0; i < 3; ++i)
        {
            this.getColumnConstraints().add(new ColumnConstraints(columnSpacing));
        }

        this.add(model, 0, 0);

        this.add(title, 1, 0);

        this.add(description, 1, 1);

        this.add(quantity, 3, 0);
        GridPane.setHalignment(quantity, HPos.RIGHT);

        this.add(price, 4, 0);
        GridPane.setHalignment(price, HPos.RIGHT);

        this.add(totalPrice, 5, 0);
        GridPane.setHalignment(totalPrice, HPos.RIGHT);
    }
}

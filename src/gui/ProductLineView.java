package gui;

import utility.PriceFormatter;
import interfaces.ProductDTO;
import interfaces.ProductLineDTO;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Gruppe12
 */
public class ProductLineView extends BorderPane
{
    // Keep references to GridPane and AnchorPane, allows for flexibility later on.
    GridPane gpLeft;
    GridPane gpRight;

    /**
     * Creates a product line view, for viewing product lines. A image of the
     * product in the product line and a product line is shown.
     *
     * @param productImage The image of the product in the product line.
     * @param pl The product line
     * @param columnSpacing The column spacing in between the sections of the
     * product line view
     */
    public ProductLineView(Image productImage, ProductLineDTO pl, int columnSpacing)
    {
        // BorderPane
        super();

        ProductDTO product = pl.getProduct();

        // GridPane left
        gpLeft = new GridPane();
        gpLeft.setHgap(10.0);
        gpLeft.setVgap(10.0);
        gpLeft.setPadding(new Insets(5, 5, 5, 5)); // Offsets are 5 from top, 5 from right, 5 from bottom, 5 from left.
        // Pin GridPane to right side of the BorderPane.
        this.setLeft(gpLeft);

        ImageView image = new ImageView(productImage);
        image.setFitWidth(147);
        image.setPreserveRatio(true);
        // Add image to grid[0, 0] and let it stretch to grid[1, 2]
        gpLeft.add(image, 0, 0, 1, 2);

        Text title = new Text(product.getName());
        // Wrap text at 200 pixel width.
        title.setWrappingWidth(200);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        // Pin title to top of its grid.
        GridPane.setValignment(title, VPos.TOP);
        // Add title to grid[1, 0] and let it stretch to grid[2, 1]
        gpLeft.add(title, 1, 0);

        Text description = new Text(product.getDescription());
        // Pin description to top of its grid.
        GridPane.setValignment(description, VPos.TOP);
        // Add description to grid[1, 1] and let it stretch to grid[2, 2]
        gpLeft.add(description, 1, 1);

        //GridPane right
        gpRight = new GridPane();
        gpRight.setPadding(new Insets(5, 5, 5, 5));
        gpRight.setAlignment(Pos.CENTER_RIGHT);
        //gpRight.setGridLinesVisible(true); use for visual debugging of grids.
        this.setRight(gpRight);
        // Adds 3 columns: amount, price, totalprice, to the gridpane with spacing = COLUMN_SPACING.
        for (int i = 0; i < 3; ++i)
        {
            gpRight.getColumnConstraints().add(new ColumnConstraints(columnSpacing));
        }

        Label quantity = new Label(Integer.toString(pl.getQuantity()));
        quantity.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Label price = new Label(PriceFormatter.format(product.getPrice()));
        price.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Label totalPrice = new Label(PriceFormatter.format(pl.getTotalPrice()));
        totalPrice.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        gpRight.add(quantity, 0, 0);
        GridPane.setHalignment(quantity, HPos.RIGHT);

        gpRight.add(price, 1, 0);
        GridPane.setHalignment(price, HPos.RIGHT);

        gpRight.add(totalPrice, 2, 0);
        GridPane.setHalignment(totalPrice, HPos.RIGHT);
    }
}

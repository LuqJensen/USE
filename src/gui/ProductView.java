package gui;

import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Lucas
 */
public class ProductView extends BorderPane
{
    // Keep references to GridPane and AnchorPane, allows for flexibility later on.
    GridPane gp;
    AnchorPane ap;

    public ProductView(Image productImage, String productName, String productDescription, Double productPrice)
    {
        // BorderPane
        super();

        // AnchorPane
        ap = new AnchorPane();
        // Pin AnchorPane to right side of the BorderPane.
        this.setRight(ap);

        Text price = new Text(productPrice.toString() + ",-");
        price.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        // Offset is needed for Right Anchor because javaFX.Text Control is quite odd.
        // Even with the offset it still produces an annoying bug, letting the Text Control
        // exceed the limits of its parent... The AnchorPane then reflects this and
        // exceeds its own parent (this).
        AnchorPane.setBottomAnchor(price, 10.0);
        AnchorPane.setRightAnchor(price, 20.0);
        ap.getChildren().add(price);

        // GridPane
        gp = new GridPane();
        gp.setHgap(10.0);
        gp.setVgap(10.0);
        gp.setPadding(new Insets(5, 5, 5, 5));
        // Pin GridPane to right side of the BorderPane.
        this.setLeft(gp);

        // Add image to grid[0, 0] and let it stretch to grid[1, 2]
        gp.add(new ImageView(productImage), 0, 0, 1, 2);

        Text title = new Text(productName);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        // Pin title to top of its grid.
        GridPane.setValignment(title, VPos.TOP);
        // Add title to grid[1, 0] and let it stretch to grid[2, 1]
        gp.add(title, 1, 0);

        Text description = new Text(productDescription);
        // Pin description to top of its grid.
        GridPane.setValignment(description, VPos.TOP);
        // Add description to grid[1, 1] and let it stretch to grid[2, 2]
        gp.add(description, 1, 1);
    }
}

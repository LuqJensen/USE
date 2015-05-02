package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
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
    CallBack callBack;

    public ProductView(CallBack callBack, Image productImage, String productName, String productDescription, Double productPrice)
    {
        // BorderPane
        super();

        this.callBack = callBack;

        // AnchorPane
        ap = new AnchorPane();
        // Pin AnchorPane to right side of the BorderPane.
        this.setCenter(ap);

        Text price = new Text(productPrice.toString() + ",-");
        price.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        AnchorPane.setBottomAnchor(price, 10.0);
        AnchorPane.setRightAnchor(price, 10.0);
        ap.getChildren().add(price);

        // GridPane
        gp = new GridPane();
        gp.setHgap(10.0);
        gp.setVgap(10.0);
        gp.setPadding(new Insets(5, 5, 10, 5)); // Offsets are 5 from top, 5 from right, 10 from bottom, 5 from left.
        // Pin GridPane to right side of the BorderPane.
        this.setLeft(gp);

        // Add image to grid[0, 0] and let it stretch to grid[1, 3]
        gp.add(new ImageView(productImage), 0, 0, 1, 3);

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

        Button btn = new Button();
        btn.setText("Køb");
        btn.setStyle("-fx-base: #ffd000;");
        btn.setOnAction((ActionEvent event) ->
        {
            callBack.call();
        });
        // Pin btn to top of its grid.
        GridPane.setValignment(btn, VPos.BOTTOM);
        // Add btn to grid[1, 2] and let it stretch to grid[2, 3]
        gp.add(btn, 1, 2);
    }
}

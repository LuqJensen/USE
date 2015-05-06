package gui;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Simon Flensted
 */
public class ProductLineView extends BorderPane
{
 // Keep references to GridPane and AnchorPane, allows for flexibility later on.
    GridPane gp;
    CallBack callBack;
    HBox hb;

    public ProductLineView(CallBack callBack, Image productImage, String productName, String productDescription, Double productPrice, int productQuantity, double totalPrice)
    {
        // BorderPane
        super();

        this.callBack = callBack;

        // GridPane
        gp = new GridPane();
        gp.setHgap(10.0);
        gp.setVgap(10.0);
        gp.setPadding(new Insets(5, 5, 10, 5)); // Offsets are 5 from top, 5 from right, 10 from bottom, 5 from left.
        // Pin GridPane to right side of the BorderPane.
        this.setLeft(gp);
        
        //HBox
        hb = new HBox();
        hb.setSpacing(110);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.setPadding(new Insets(5, 5, 10, 5));
        this.setRight(hb);

        // Add image to grid[0, 0] and let it stretch to grid[1, 3]
        ImageView pi = new ImageView(productImage);
        pi.setFitWidth(147);
        pi.setPreserveRatio(true);
        
        gp.add(pi, 0, 0, 1, 3);

        Text title = new Text(productName);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        // Pin title to top of its grid.
        GridPane.setValignment(title, VPos.TOP);
        // Add title to grid[1, 0] and let it stretch to grid[2, 1]
        gp.add(title, 1, 0, 3, 1);

        Text description = new Text(productDescription);
        // Pin description to top of its grid.
        GridPane.setValignment(description, VPos.TOP);
        // Add description to grid[1, 1] and let it stretch to grid[2, 2]
        gp.add(description, 1, 1);
        // Pin btn to top of its grid.
        
        Text quantity = new Text(Integer.toString(productQuantity));
        hb.getChildren().add(quantity);
        
        Text price = new Text(productPrice.toString() + ",-");
        price.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hb.getChildren().add(price);
        
        Text sum = new Text(totalPrice + ",-");
        sum.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hb.getChildren().add(sum);
    }
}
